package com.itsconv.web.popup.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "its_popup_list")
@SQLRestriction("del_yn = 'N'")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Popup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "title", length = 100)
    private String title;

    @Lob
    @Column(name = "contents", columnDefinition = "LONGTEXT")
    private String contents;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "use_yn", length = 1)
    private String useYn;

    @Column(name = "del_yn", nullable = false, length = 1)
    private String delYn = "N";

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @Column(name = "size_w")
    private Integer sizeW;

    @Column(name = "size_h")
    private Integer sizeH;

    @Column(name = "redirect_url", length = 100)
    private String redirectUrl;

    @Builder
    private Popup(
            String title,
            String contents,
            LocalDate startDate,
            LocalDate endDate,
            String useYn,
            Integer positionX,
            Integer positionY,
            Integer sizeW,
            Integer sizeH,
            String redirectUrl
    ) {
        this.title = title;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.useYn = useYn;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeW = sizeW;
        this.sizeH = sizeH;
            this.redirectUrl = redirectUrl;
    }

    public static Popup create(
            String title,
            String contents,
            LocalDate startDate,
            LocalDate endDate,
            String useYn,
            Integer positionX,
            Integer positionY,
            Integer sizeW,
            Integer sizeH,
            String redirectUrl
    ) {
        return Popup.builder()
                .title(title)
                .contents(contents)
                .startDate(startDate)
                .endDate(endDate)
                .useYn(useYn)
                .positionX(positionX)
                .positionY(positionY)
                .sizeW(sizeW)
                .sizeH(sizeH)
                .redirectUrl(redirectUrl)
                .build();
    }

    public void update(
            String title,
            String contents,
            LocalDate startDate,
            LocalDate endDate,
            String useYn,
            Integer positionX,
            Integer positionY,
            Integer sizeW,
            Integer sizeH,
            String redirectUrl
    ) {
        this.title = title;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.useYn = useYn;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeW = sizeW;
        this.sizeH = sizeH;
        this.redirectUrl = redirectUrl;
    }

    public void delete() {
        this.delYn = "Y";
    }
}
