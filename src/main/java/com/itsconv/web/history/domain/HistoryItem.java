package com.itsconv.web.history.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;
import com.itsconv.web.history.service.dto.command.HistoryItemCreateCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history_item")
@Getter
@NoArgsConstructor
public class HistoryItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "year_id", nullable = false)
    private HistoryYear year;

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    @Column(name = "display_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "last_update_id", length = 20)
    private String lastUpdateId;

    @Column(name = "create_id", length = 20)
    private String createId;

    public void updateContent(String content, String requestId) {
        this.content = content;
        this.lastUpdateId = requestId;
    }

    public void saveContent(HistoryItemCreateCommand command) {
        this.year = command.year();
        this.content = command.content();
        this.sortOrder = command.nextOrder();
        this.lastUpdateId = command.requestId();
        this.createId = command.requestId();
    }
}
