package com.itsconv.web.history.domain;

import java.util.ArrayList;
import java.util.List;

import com.itsconv.web.common.domain.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history_period")
@Getter
@NoArgsConstructor
public class HistoryPeriod extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_period", nullable = false, length = 4)
    private String startPeriod;

    @Column(name = "end_period", nullable = false, length = 4)
    private String endPeriod;

    @Column(name = "display_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "last_update_id", length = 20)
    private String lastUpdateId;

    @Column(name = "create_id", length = 20)
    private String createId;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryYear> years = new ArrayList<>();

    public void updatePeriodName(String startPeriod, String endPeriod, String requestId) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.lastUpdateId = requestId;
    }

    public void saveTopHistory(String startPeriod, String endPeriod, String requestId, Integer sortOrder) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.sortOrder = sortOrder;
        this.createId = requestId;
        this.lastUpdateId = requestId;
    }
}
