package com.itsconv.web.history.domain;

import java.time.LocalDateTime;

import com.itsconv.web.common.domain.BaseTimeEntity;
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
public class History extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer id;

    @Column(name = "history_parent", nullable = false, length = 20)
    private String parent = "TOP";

    @Column(name = "history_name", length = 50)
    private String name;

    @Column(name = "history_sub_name", length = 50)
    private String subName;

    @Column(name = "last_update_id", length = 20)
    private String lastUpdateId;
}
