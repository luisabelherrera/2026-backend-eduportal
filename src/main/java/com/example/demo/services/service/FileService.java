package com.example.demo.services.service;

import com.example.demo.model.entity.FileEntity;
import com.example.demo.response.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    FileEntity store(MultipartFile file) throws IOException;
    Optional<FileEntity> getFile(Long id) throws FileNotFoundException;
    List<ResponseFile> getAllFiles();
}
