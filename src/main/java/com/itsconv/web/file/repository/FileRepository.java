package com.itsconv.web.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsconv.web.file.domain.File;

public interface FileRepository extends JpaRepository<File, Long>{
    
}
