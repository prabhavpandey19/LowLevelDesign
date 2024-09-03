package com.example.versionManagement.service;

import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;
import com.example.versionManagement.model.enums.RollOutStrategy;

import java.io.File;
import java.util.List;

public interface AppManagementService {
    AppVersion uploadNewVersion(String appName, OSType osType, File file, Double supportedOsVersion);

    void releaseVersion(AppVersion appVersion, RollOutStrategy rollOutStrategy);

    void executeTask(List<Devices> device, AppVersion appVersion);

    void createUpdatePatch(Devices device,AppVersion previous, AppVersion newApp);
}
