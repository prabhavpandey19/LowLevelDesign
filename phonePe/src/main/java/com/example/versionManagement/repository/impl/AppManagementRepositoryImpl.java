package com.example.versionManagement.repository.impl;

import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.repository.AppManagementRepository;
import com.example.versionManagement.service.AppManagementService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AppManagementRepositoryImpl implements AppManagementRepository {
    private List<AppVersion> appList;
    private static AppManagementRepositoryImpl appManagementRepository;
    private Long versionId;
    public void AppManagementRepositoryImpl() {
        appList = new ArrayList<>();
        versionId = 1L;
    }

    @Override
    public Long getLatestVersionId() {
        return versionId;
    }

    @Override
    public AppVersion saveNewAppVersion(AppVersion appVersion) {
        appList.add(appVersion);
        versionId = appVersion.getAppVersionId() + 1;
        return appVersion;
    }
}
