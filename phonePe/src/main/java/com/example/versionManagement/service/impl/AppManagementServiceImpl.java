package com.example.versionManagement.service.impl;

import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;
import com.example.versionManagement.model.enums.RollOutStrategy;
import com.example.versionManagement.repository.AppManagementRepository;
import com.example.versionManagement.repository.FileRepository;
import com.example.versionManagement.repository.impl.AppManagementRepositoryImpl;
import com.example.versionManagement.service.AppManagementService;
import com.example.versionManagement.service.DeviceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppManagementServiceImpl implements AppManagementService {
    private final AppManagementRepository appManagementRepository;
    private final FileRepository fileRepository;
    private final DeviceManagementService deviceManagementService;

    @Override
    public AppVersion uploadNewVersion(String appName, OSType osType, File file, Double supportedOsVersion) {
        String fileUrl = fileRepository.uploadFile(file);
        AppVersion appVersion = new AppVersion();
        appVersion.setAppName(appName);
        appVersion.setAppVersionId(appVersion.getAppVersionId());
        appVersion.setFileUrl(fileUrl);
        appVersion.setOsType(osType);
        appVersion.setSupportedOSVersion(supportedOsVersion);
        return appManagementRepository.saveNewAppVersion(appVersion);
    }

    @Override
    public void releaseVersion(AppVersion appVersion, RollOutStrategy rollOutStrategy) {
        List<Devices> devices = null;
        if (rollOutStrategy == rollOutStrategy.IOS) {
            devices = deviceManagementService.getDevicesByOsType(OSType.IOS);
        } else if (rollOutStrategy == rollOutStrategy.ANDROID) {
            devices = deviceManagementService.getDevicesByOsType(OSType.ANDROID);
        } else if (rollOutStrategy == rollOutStrategy.ANDROID_VERSION) {
            devices = deviceManagementService.getDevicesByOsTypeAndVersionBelow(OSType.ANDROID,appVersion.getSupportedOSVersion());
        } else if (rollOutStrategy == rollOutStrategy.IOS_ANDROID) {
            devices = deviceManagementService.getDevicesByOsTypeAndVersionBelow(OSType.IOS,appVersion.getSupportedOSVersion());
        }
        executeTask(devices,appVersion);
    }

    @Override
    public void executeTask(List<Devices> devices, AppVersion appVersion) {
        for (Devices device: devices) {
            if (deviceManagementService.checkForInstall(device,appVersion)) {
                deviceManagementService.installVersion(device,appVersion,fileRepository.getFile(appVersion.getFileUrl()));
            } else if (deviceManagementService.checkForUpdates(device,appVersion)) {
                createUpdatePatch(device,device.getAppInstalled(),appVersion);
            }

        }
    }

    @Override
    public void createUpdatePatch(Devices device, AppVersion previous, AppVersion newApp) {
        File previosFile = fileRepository.getFile(previous.getFileUrl());
        File newFile = fileRepository.getFile(newApp.getFileUrl());
        File diffFile = fileRepository.createDiffPack(previosFile,newFile);
        deviceManagementService.updateVersion(device,newApp,diffFile);
    }


}
