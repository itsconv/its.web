package com.itsconv.web.image.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "its_page_image_slot",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_its_page_image_slot_tab_code", columnNames = {"tab_menu_id", "slot_code"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageImageSlot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private Long id;

    @Column(name = "tab_menu_id", nullable = false)
    private Long tabMenuId;

    @Column(name = "slot_code", nullable = false, length = 100)
    private String code;

    @Column(name = "slot_name", nullable = false, length = 100)
    private String name;

    @Column(name = "slot_label", length = 100)
    private String label;

    @Column(name = "guide_text", length = 255)
    private String guideText;

    @Column(name = "guide_width")
    private Integer guideWidth;

    @Column(name = "guide_height")
    private Integer guideHeight;

    @Builder
    private PageImageSlot(
            Long tabMenuId,
            String code,
            String name,
            String label,
            String guideText,
            Integer guideWidth,
            Integer guideHeight
    ) {
        this.tabMenuId = tabMenuId;
        this.code = code;
        this.name = name;
        this.label = label;
        this.guideText = guideText;
        this.guideWidth = guideWidth;
        this.guideHeight = guideHeight;
    }

    public static PageImageSlot create(
            Long tabMenuId,
            String code,
            String name,
            String label,
            String guideText,
            Integer guideWidth,
            Integer guideHeight
    ) {
        return PageImageSlot.builder()
                .tabMenuId(tabMenuId)
                .code(code)
                .name(name)
                .label(label)
                .guideText(guideText)
                .guideWidth(guideWidth)
                .guideHeight(guideHeight)
                .build();
    }

    public void update(String name, String label, String guideText, Integer guideWidth, Integer guideHeight) {
        this.name = name;
        this.label = label;
        this.guideText = guideText;
        this.guideWidth = guideWidth;
        this.guideHeight = guideHeight;
    }
}
