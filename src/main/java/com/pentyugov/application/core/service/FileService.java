package com.pentyugov.application.core.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String NAME = "fileService";

    void importFromCsv(MultipartFile file);
}
