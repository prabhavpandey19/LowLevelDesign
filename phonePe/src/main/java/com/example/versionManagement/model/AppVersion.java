package com.example.versionManagement.model;

import com.example.versionManagement.model.enums.OSType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AppVersion {
    private Long appVersionId;
    private OSType osType;
    private String appName;
    private String fileUrl;
    private Double supportedOSVersion;
}
