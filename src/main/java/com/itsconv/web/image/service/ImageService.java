package com.itsconv.web.image.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.file.domain.File;
import com.itsconv.web.file.repository.FileRepository;
import com.itsconv.web.image.domain.PageImageMapping;
import com.itsconv.web.image.repository.ImageQueryRepository;
import com.itsconv.web.image.repository.PageImageMappingRepository;
import com.itsconv.web.image.repository.PageImageSlotRepository;
import com.itsconv.web.image.service.dto.view.ImageManagementView;
import com.itsconv.web.image.service.dto.view.ImageSlotCardView;
import com.itsconv.web.menu.domain.Menu;
import com.itsconv.web.menu.domain.MenuDepth;
import com.itsconv.web.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itsconv.web.image.service.dto.view.PageImageUrlsView;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final MenuRepository menuRepository;
    private final ImageQueryRepository imageQueryRepository;
    private final PageImageSlotRepository pageImageSlotRepository;
    private final PageImageMappingRepository pageImageMappingRepository;
    private final FileRepository fileRepository;
    private final ImageFileStorage imageFileStorage;

    @Transactional(readOnly = true)
    public ImageManagementView findImageManagementView(String mainMenuCode, String subMenuCode, String tabMenuCode) {
        Menu selectedMainMenu = menuRepository.findByCode(mainMenuCode)
                .orElseGet(() -> menuRepository.findMenuByDepth(MenuDepth.MAIN).stream().findFirst()
                        .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND)));

        List<Menu> subMenus = menuRepository.findMenuByParentMenuId(selectedMainMenu.getId());
        Menu selectedSubMenu = findMenuByCodeOrDefault(subMenuCode, subMenus);

        List<Menu> tabMenus = menuRepository.findMenuByParentMenuId(selectedSubMenu.getId());
        Menu selectedTabMenu = findTabMenu(tabMenuCode, tabMenus, selectedSubMenu);
        List<ImageSlotCardView> slotViews = imageQueryRepository.findImageSlotCardsByMenuIds(List.of(selectedTabMenu.getId()))
                .getOrDefault(selectedTabMenu.getId(), List.of());

        return ImageManagementView.of(selectedMainMenu, subMenus, selectedSubMenu, tabMenus, selectedTabMenu, slotViews);
    }

    @Transactional(readOnly = true)
    public PageImageUrlsView findImageUrlsByMainMenuCode(String mainMenuCode) {
        Menu mainMenu = menuRepository.findByCode(mainMenuCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        List<Menu> subMenus = menuRepository.findMenuByParentMenuId(mainMenu.getId());
        List<Long> targetMenuIds = extractTargetMenuIds(subMenus);

        Map<Long, List<ImageSlotCardView>> slotCardMap = imageQueryRepository.findImageSlotCardsByMenuIds(targetMenuIds);
        Map<String, String> imageUrlMap = new LinkedHashMap<>();

        for (Long menuId : targetMenuIds) {
            appendImageUrls(imageUrlMap, slotCardMap.getOrDefault(menuId, List.of()));
        }

        return PageImageUrlsView.of(imageUrlMap);
    }

    @Transactional
    public void updateImageFile(Long slotId, MultipartFile uploadFile, String altText, String linkUrl, String requestId) {
        pageImageSlotRepository.findById(slotId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));

        if (uploadFile == null || uploadFile.isEmpty()) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        ImageFileStorage.StoredImageFile storedImageFile = imageFileStorage.store(uploadFile);
        File storedFile = fileRepository.save(File.create(
                storedImageFile.path(),
                storedImageFile.uuid(),
                storedImageFile.savedFileName(),
                storedImageFile.size()
        ));

        pageImageMappingRepository.findBySlotId(slotId)
                .ifPresentOrElse(
                        mapping -> mapping.replace(storedFile.getId(), altText, linkUrl, requestId),
                        () -> pageImageMappingRepository.save(
                                PageImageMapping.create(slotId, storedFile.getId(), altText, linkUrl, requestId)
                        )
                );
    }

    private List<Long> extractTargetMenuIds(List<Menu> subMenus) {
        List<Long> subMenuIds = subMenus.stream()
                .map(Menu::getId)
                .toList();

        Map<Long, List<Menu>> tabMenuMap = menuRepository.findMenuByParentMenuIdIn(subMenuIds).stream()
                .collect(Collectors.groupingBy(Menu::getParentMenuId));

        return subMenus.stream()
                .flatMap(subMenu -> {
                    List<Menu> tabMenus = tabMenuMap.getOrDefault(subMenu.getId(), List.of());
                    return tabMenus.isEmpty() ?
                            java.util.stream.Stream.of(subMenu.getId()) :
                            tabMenus.stream().map(Menu::getId);
                })
                .toList();
    }

    private Menu findTabMenu(String tabMenuCode, List<Menu> tabMenus, Menu selectedSubMenu) {
        if (tabMenus.isEmpty()) {
            return selectedSubMenu;
        }

        Menu tabMenu = findMenuByCodeOrDefault(tabMenuCode, tabMenus);

        if (!MenuDepth.TAB.equals(tabMenu.getDepth())) {
            throw new BusinessException(ErrorCode.COMMON_BAD_REQUEST);
        }

        return tabMenu;
    }

    private Menu findMenuByCodeOrDefault(String code, List<Menu> menus) {
        return menus.stream()
                .filter(menu -> code == null || code.isBlank() || menu.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_NOT_FOUND));
    }

    private void appendImageUrls(Map<String, String> imageUrlMap, List<ImageSlotCardView> slotCards) {
        for (ImageSlotCardView slotCard : slotCards) {
            imageUrlMap.put(slotCard.slotCode(), slotCard.thumbnailUrl());
        }
    }
}
