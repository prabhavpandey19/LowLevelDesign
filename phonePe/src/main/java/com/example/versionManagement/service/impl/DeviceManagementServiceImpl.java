package com.example.versionManagement.service.impl;

import com.example.versionManagement.exception.NotSupportedVersionException;
import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;
import com.example.versionManagement.repository.DeviceRepository;
import com.example.versionManagement.service.DeviceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class DeviceManagementServiceImpl implements DeviceManagementService {
    List<Devices> devicesList;
    private static DeviceManagementServiceImpl deviceManagementService;
    @Autowired
    private DeviceRepository deviceRepository;
    public DeviceManagementServiceImpl() {
        this.devicesList = new ArrayList<>();
    }

    public static DeviceManagementServiceImpl getInstance() {
        if (deviceManagementService == null) {
            synchronized (DeviceManagementServiceImpl.class) {
                if (deviceManagementService == null) {
                    deviceManagementService = new DeviceManagementServiceImpl();
                }
            }
        }
        return deviceManagementService;
    }


    @Override
    public Devices addVehicle(OSType os, String makeName, String modelName, Double osVersion, AppVersion appInstalled) {
        Devices devices = new Devices();
        devices.setOs(os);
        devices.setMakeName(makeName);
        devices.setModelName(modelName);
        devices.setOsVersion(osVersion);
        devices.setAppInstalled(appInstalled);
        devicesList.add(devices);
        return devices;
    }

    @Override
    public List<Devices> getAllDevicesList() {
        return devicesList;
    }

    @Override
    public List<Devices> getDevicesByOsType(OSType osType) {
        return devicesList.stream()
                .filter(device -> device.getOs() == osType)
                .collect(Collectors.toList());
    }

    @Override
    public void installVersion(Devices devices, AppVersion appVersion, File file) {
        if (isAppVersionSupported(devices,appVersion)) {
            deviceRepository.installApp(devices,file);
        } else {
            throw new NotSupportedVersionException();
        }
    }

    @Override
    public void updateVersion(Devices devices, AppVersion appVersion, File file) {
        if (isAppVersionSupported(devices,appVersion)) {
            deviceRepository.updateApp(devices,file);
        } else {
            throw new NotSupportedVersionException();
        }
    }

    @Override
    public boolean isAppVersionSupported(Devices devices, AppVersion appVersion) {
        if (devices.getAppInstalled() == null) {
            return devices.getOs() == appVersion.getOsType() && devices.getOsVersion() >= appVersion.getSupportedOSVersion();
        } else if (devices.getAppInstalled().getOsType() == appVersion.getOsType() &&
                devices.getAppInstalled().getAppVersionId() < appVersion.getAppVersionId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkForInstall(Devices devices, AppVersion appVersion) {
        return devices.getOs() == appVersion.getOsType() && devices.getOsVersion() >= appVersion.getSupportedOSVersion();
    }

    @Override
    public boolean checkForUpdates(Devices devices, AppVersion appVersion) {
        if (devices.getAppInstalled() == null) {
            return true;
        } else if (devices.getAppInstalled().getAppVersionId() < appVersion.getAppVersionId()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Devices> getDevicesByOsTypeAndVersionBelow(OSType osType, Double osVersion) {
        return devicesList.stream()
                .filter(device -> device.getOs() == osType && device.getOsVersion() >= osVersion)
                .collect(Collectors.toList());
    }
}
