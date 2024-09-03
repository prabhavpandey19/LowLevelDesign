package com.example.versionManagement.repository;

import java.io.File;

public interface FileRepository {
    File createDiffPack(File sourceFile, File targetFile);
    String uploadFile(File file);
    File getFile(String fileUrl);
}
