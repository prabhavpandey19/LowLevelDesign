package com.example.versionManagement.repository.impl;

import com.example.versionManagement.repository.FileRepository;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class FileRepositoryImpl implements FileRepository {
    @Override
    public File createDiffPack(File sourceFile, File targetFile) {
        return null;
    }

    @Override
    public String uploadFile(File file) {
        return null;
    }

    @Override
    public File getFile(String fileUrl) {
        return null;
    }
}
