package com.itsconv.web.file.domain;

import com.itsconv.web.common.domain.BaseTimeEntity;

import com.itsconv.web.file.service.dto.command.FileUploadCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "its_file")
@Getter
@NoArgsConstructor
public class File extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "file_path", length = 200)
    private String path;

    @Column(name = "file_uuid", length = 200)
    private String uuid;

    @Column(name = "file_origin_name", length = 200)
    private String originName;

    @Column(name = "file_size")
    private Long size;

    public void saveFile(FileUploadCommand command) {
        this.path = command.path();
        this.uuid = command.uuid();
        this.originName = command.originName();
        this.size = command.size();
    }

    @Builder
    public File(String path, String uuid, String originName, Long size) {
        this.path = path;
        this.uuid = uuid;
        this.originName = originName;
        this.size = size;
    }

    public static File create(String path, String uuid, String originName, Long size) {
        return File.builder()
                .path(path)
                .uuid(uuid)
                .originName(originName)
                .size(size)
                .build();
    }
}
