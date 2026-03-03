package com.itsconv.web.board.domain;

import java.util.ArrayList;
import java.util.List;

import com.itsconv.web.common.domain.BaseTimeEntity;
import com.itsconv.web.file.domain.FileDetail;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "its_board")
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false, length = 10)
    private BoardType type;

    @Column(name = "title", length = 100)
    private String title;

    @Lob
    @Column(name = "contents", columnDefinition = "LONGTEXT")
    private String contents;

    @Column(name = "create_id", length = 50)
    private String createId;

    @Column(name = "create_name", length = 20)
    private String createName;

    @Column(name = "last_update_id", length = 50)
    private String lastUpdateId;

    @Column(name = "last_update_name", length = 20)
    private String lastUpdateName;

    @Column(name = "view_count")
    private Integer viewCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileDetail> files = new ArrayList<>();
}
