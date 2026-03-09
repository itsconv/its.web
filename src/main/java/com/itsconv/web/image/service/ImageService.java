package com.itsconv.web.image.service;

import com.itsconv.web.common.exception.BusinessException;
import com.itsconv.web.common.exception.ErrorCode;
import com.itsconv.web.image.repository.ImageQueryRepository;
import com.itsconv.web.image.service.dto.view.ImageManagementView;
import com.itsconv.web.image.service.dto.view.ImageSlotCardView;
import com.itsconv.web.menu.domain.Menu;
import com.itsconv.web.menu.domain.MenuDepth;
import com.itsconv.web.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String USE_Y = "Y";

    private final MenuRepository menuRepository;
    private final ImageQueryRepository imageQueryRepository;

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
}
