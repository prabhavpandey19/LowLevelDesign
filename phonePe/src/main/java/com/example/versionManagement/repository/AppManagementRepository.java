package com.example.versionManagement.repository;

import com.example.versionManagement.model.AppVersion;

public interface AppManagementRepository {
    Long getLatestVersionId();

    AppVersion saveNewAppVersion(AppVersion appVersion);
}
