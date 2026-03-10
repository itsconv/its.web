package com.itsconv.web.image.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.file.domain.File;
import com.itsconv.web.file.repository.FileRepository;
import com.itsconv.web.image.domain.PageImageMapping;
import com.itsconv.web.image.repository.PageImageMappingRepository;
import com.itsconv.web.image.repository.PageImageSlotRepository;
import com.itsconv.web.image.repository.ImageQueryRepository;
import com.itsconv.web.image.service.dto.view.ImageManagementView;
import com.itsconv.web.image.service.dto.view.ImageSlotCardView;
import com.itsconv.web.menu.domain.Menu;
import com.itsconv.web.menu.domain.MenuDepth;
import com.itsconv.web.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String USE_Y = "Y";
    private static final String PAGE_IMAGE_UPLOAD_DIR = "src/main/resources/static/upload/page-image";
    private static final String PAGE_IMAGE_URL_PATH = "/upload/page-image";

    private final MenuRepository menuRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final PageImageSlotRepository pageImageSlotRepository;
    private final PageImageMappingRepository pageImageMappingRepository;
    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public ImageManagementView findImageManagementView(String mainMenuCode, String subMenuCode, String tabMenuCode) {
        List<Menu> mainMenus = menuRepository.findByDepthAndUseYnOrderBySortOrderAsc(MenuDepth.MAIN, USE_Y);
        // 메인 메뉴 미선택 시 정렬 기준 첫 번째 메뉴를 기본값으로 사용한다.
        Menu selectedMainMenu = findMainMenu(mainMenuCode, mainMenus);

        List<Menu> subMenus = menuRepository.findByParentMenuIdAndUseYnOrderBySortOrderAsc(selectedMainMenu.getId(), USE_Y);
        Menu selectedSubMenu = findSubMenu(subMenuCode, subMenus);

        List<Menu> tabMenus = menuRepository.findByParentMenuIdAndUseYnOrderBySortOrderAsc(selectedSubMenu.getId(), USE_Y);
        // 3뎁스 탭이 없으면 2뎁스 메뉴 자체를 이미지 관리 대상으로 사용한다.
        Menu selectedTabMenu = findTabMenu(tabMenuCode, tabMenus, selectedSubMenu);
        List<ImageSlotCardView> slotViews = imageQueryRepository.findImageSlotCardsByMenuId(selectedTabMenu.getId());

        return ImageManagementView.of(mainMenus, selectedMainMenu, subMenus, selectedSubMenu, tabMenus, selectedTabMenu, slotViews);
    }

    @Transactional(readOnly = true)
    public Map<String, String> findImageUrlsByMainMenuCode(String mainMenuCode) {
        Menu mainMenu = menuRepository.findByCode(mainMenuCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        List<Menu> subMenus = menuRepository.findByParentMenuIdAndUseYnOrderBySortOrderAsc(mainMenu.getId(), USE_Y);
        Map<String, String> imageUrlMap = new LinkedHashMap<>();

        for (Menu subMenu : subMenus) {
            List<Menu> tabMenus = menuRepository.findByParentMenuIdAndUseYnOrderBySortOrderAsc(subMenu.getId(), USE_Y);

            if (tabMenus.isEmpty()) {
                appendImageUrls(imageUrlMap, subMenu.getId());
                continue;
            }

            for (Menu tabMenu : tabMenus) {
                appendImageUrls(imageUrlMap, tabMenu.getId());
            }
        }

        return imageUrlMap;
    }

    @Transactional
    public void updateImageFile(Long slotId, MultipartFile uploadFile, String altText, String linkUrl, String requestId) {
        pageImageSlotRepository.findById(slotId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        if (uploadFile == null || uploadFile.isEmpty()) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        File storedFile = storeFile(uploadFile);

        pageImageMappingRepository.findBySlotId(slotId)
                .ifPresentOrElse(
                        mapping -> mapping.replace(storedFile.getId(), altText, linkUrl, requestId),
                        () -> pageImageMappingRepository.save(
                                PageImageMapping.create(slotId, storedFile.getId(), altText, linkUrl, requestId)
                        )
                );
    }

    private Menu findSubMenu(String subMenuCode, List<Menu> subMenus) {
        if (subMenuCode == null || subMenuCode.isBlank()) {
            return subMenus.stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
        }

        return subMenus.stream()
                .filter(subMenu -> subMenu.getCode().equals(subMenuCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
    }

    private Menu findTabMenu(String tabMenuCode, List<Menu> tabMenus, Menu selectedSubMenu) {
        if (tabMenus.isEmpty()) {
            return selectedSubMenu;
        }

        if (tabMenuCode == null || tabMenuCode.isBlank()) {
            return tabMenus.stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
        }

        Menu tabMenu = tabMenus.stream()
                .filter(menu -> menu.getCode().equals(tabMenuCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        if (!MenuDepth.TAB.equals(tabMenu.getDepth())) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        return tabMenu;
    }

    private Menu findMainMenu(String mainMenuCode, List<Menu> mainMenus) {
        if (mainMenuCode == null || mainMenuCode.isBlank()) {
            return mainMenus.stream()
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
        }

        return mainMenus.stream()
                .filter(menu -> menu.getCode().equals(mainMenuCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
    }

    private File storeFile(MultipartFile uploadFile) {
        String originalFilename = uploadFile.getOriginalFilename();
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String savedFileName = uuid + extension;
        Path uploadDirectory = Path.of(PAGE_IMAGE_UPLOAD_DIR);
        Path targetPath = uploadDirectory.resolve(savedFileName);

        try {
            Files.createDirectories(uploadDirectory);
            uploadFile.transferTo(targetPath);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.COMMON_INTERNAL_SERVER_ERROR, "파일 저장 중 오류가 발생했습니다.", e);
        }

        return fileRepository.save(File.create(
                PAGE_IMAGE_URL_PATH,
                uuid,
                savedFileName,
                uploadFile.getSize()
        ));
    }

    private String extractExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private void appendImageUrls(Map<String, String> imageUrlMap, Long menuId) {
        List<ImageSlotCardView> slotCards = imageQueryRepository.findImageSlotCardsByMenuId(menuId);

        for (ImageSlotCardView slotCard : slotCards) {
            imageUrlMap.put(slotCard.slotCode(), slotCard.thumbnailUrl());
        }
    }
}
