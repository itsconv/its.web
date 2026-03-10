package com.itsconv.web.menu.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(
        name = "its_menu_list",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_its_menu_list_code", columnNames = "menu_code")
        }
)
@SQLRestriction("del_yn = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "parent_menu_id")
    private Long parentMenuId;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_depth", nullable = false, length = 10)
    private MenuDepth depth;

    @Column(name = "menu_code", nullable = false, length = 100)
    private String code;

    @Column(name = "menu_name", nullable = false, length = 100)
    private String name;

    @Column(name = "menu_path", length = 255)
    private String path;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "del_yn", nullable = false, length = 1)
    private String delYn;

    @Builder
    private Menu(
            Long parentMenuId,
            MenuDepth depth,
            String code,
            String name,
            String path,
            Integer sortOrder,
            String useYn,
            String delYn
    ) {
        this.parentMenuId = parentMenuId;
        this.depth = depth;
        this.code = code;
        this.name = name;
        this.path = path;
        this.sortOrder = sortOrder;
        this.useYn = useYn;
        this.delYn = delYn;
    }

    public static Menu create(
            Long parentMenuId,
            MenuDepth depth,
            String code,
            String name,
            String path,
            Integer sortOrder
    ) {
        return Menu.builder()
                .parentMenuId(parentMenuId)
                .depth(depth)
                .code(code)
                .name(name)
                .path(path)
                .sortOrder(sortOrder)
                .useYn("Y")
                .delYn("N")
                .build();
    }

    public void update(String name, String path, Integer sortOrder, String useYn) {
        this.name = name;
        this.path = path;
        this.sortOrder = sortOrder;
        this.useYn = useYn;
    }

    public void delete() {
        this.delYn = "Y";
    }

    public boolean isTabMenu() {
        return MenuDepth.TAB.equals(depth);
    }
}
