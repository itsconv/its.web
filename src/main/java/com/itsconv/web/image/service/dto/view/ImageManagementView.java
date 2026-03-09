package com.itsconv.web.image.service.dto.view;

import com.itsconv.web.menu.domain.Menu;

import java.util.List;

public record ImageManagementView(
        List<Menu> mainMenus,
        Menu selectedMainMenu,
        List<Menu> subMenus,
        Menu selectedSubMenu,
        List<Menu> tabMenus,
        Menu selectedTabMenu,
        List<ImageSlotCardView> slots
) {
    public static ImageManagementView of(
            List<Menu> mainMenus,
            Menu selectedMainMenu,
            List<Menu> subMenus,
            Menu selectedSubMenu,
            List<Menu> tabMenus,
            Menu selectedTabMenu,
            List<ImageSlotCardView> slots
    ) {
        return new ImageManagementView(mainMenus, selectedMainMenu, subMenus, selectedSubMenu, tabMenus, selectedTabMenu, slots);
    }
}
