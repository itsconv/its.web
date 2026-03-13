package com.itsconv.web.image.service.dto.view;

import com.itsconv.web.menu.domain.Menu;

import java.util.List;

public record ImageManagementView(
        Menu selectedMainMenu,
        List<Menu> subMenus,
        Menu selectedSubMenu,
        List<Menu> tabMenus,
        Menu selectedTabMenu,
        List<ImageSlotCardView> slots
) {
    public static ImageManagementView of(
            Menu selectedMainMenu,
            List<Menu> subMenus,
            Menu selectedSubMenu,
            List<Menu> tabMenus,
            Menu selectedTabMenu,
            List<ImageSlotCardView> slots
    ) {
        return new ImageManagementView(selectedMainMenu, subMenus, selectedSubMenu, tabMenus, selectedTabMenu, slots);
    }

    public String getFrontPreviewBaseUrl() {
        if (selectedMainMenu == null || selectedMainMenu.getCode() == null) {
            return "/front/trading-center";
        }

        return switch (selectedMainMenu.getCode()) {
            case "TRADING_ROOM" -> "/front/trading-center";
            case "AI_MONITORING_CENTER" -> "/front/operation-center";
            case "AI_CONTACT_CENTER" -> "/front/contact-center";
            case "UC_SOLUTION" -> "/front/uc-solution";
            default -> "/front/trading-center";
        };
    }
}
