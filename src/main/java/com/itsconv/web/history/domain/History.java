package com.itsconv.web.history.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "history")
@Getter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;

    @Column(name = "history_parent", nullable = false, length = 20)
    private String historyParent = "TOP";

    @Column(name = "history_name", length = 50)
    private String historyName;

    @Column(name = "last_update_id", length = 20)
    private String lastUpdateId;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
