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
}
