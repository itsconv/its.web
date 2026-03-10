package com.itsconv.web.file.domain;

import com.itsconv.web.board.domain.Board;
import com.itsconv.web.file.service.dto.command.FileBoardCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "its_file_board")
@Getter
@NoArgsConstructor
public class FileBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapped_id")
    private Long mappedId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;

    @Column(name = "is_thumbnail", length = 1)
    private String isThumbnail = "N";

    @Column(name = "file_order")
    private Integer sortOrder;

    @Column(name = "status", length = 4)
    private String status;

    public void saveFileBoard(FileBoardCommand command) {
        this.file = command.file();
        this.board = command.board();
        this.isThumbnail = command.isThumbnail();
        this.sortOrder = command.sortOrder();
        this.status = command.status();
    }

    public void updateStatus(Board board, String status) {
        this.board = board;
        this.status = status;
    }
}
