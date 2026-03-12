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
        name = "its_page_image_mapping",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_its_page_image_mapping_slot", columnNames = "slot_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageImageMapping extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long id;

    @Column(name = "slot_id", nullable = false)
    private Long slotId;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "link_url", length = 255)
    private String linkUrl;

    @Column(name = "version_no", nullable = false)
    private Integer versionNo;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;

    @Column(name = "create_id", length = 50)
    private String createId;

    @Column(name = "last_update_id", length = 50)
    private String lastUpdateId;

    @Builder
    private PageImageMapping(
            Long slotId,
            Long fileId,
            String altText,
            String linkUrl,
            Integer versionNo,
            String useYn,
            String createId,
            String lastUpdateId
    ) {
        this.slotId = slotId;
        this.fileId = fileId;
        this.altText = altText;
        this.linkUrl = linkUrl;
        this.versionNo = versionNo;
        this.useYn = useYn;
        this.createId = createId;
        this.lastUpdateId = lastUpdateId;
    }

    public static PageImageMapping create(
            Long slotId,
            Long fileId,
            String altText,
            String linkUrl,
            String requestId
    ) {
        return PageImageMapping.builder()
                .slotId(slotId)
                .fileId(fileId)
                .altText(altText)
                .linkUrl(linkUrl)
                .versionNo(1)
                .useYn("Y")
                .createId(requestId)
                .lastUpdateId(requestId)
                .build();
    }

    public void replace(Long fileId, String altText, String linkUrl, String requestId) {
        this.fileId = fileId;
        this.altText = altText;
        this.linkUrl = linkUrl;
        this.versionNo += 1;
        this.lastUpdateId = requestId;
    }

    public void disable(String requestId) {
        this.useYn = "N";
        this.lastUpdateId = requestId;
    }
}
