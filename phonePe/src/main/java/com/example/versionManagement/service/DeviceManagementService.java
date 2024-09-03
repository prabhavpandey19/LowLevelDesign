package com.example.versionManagement.service;

import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;

import java.io.File;
import java.util.List;

public interface DeviceManagementService {

    Devices addVehicle(OSType os, String makeName, String modelName, Double osVersion, AppVersion appInstalled);
    List<Devices> getAllDevicesList();
    List<Devices> getDevicesByOsType(OSType osType);
    void installVersion(Devices devices, AppVersion appVersion, File file);
    void updateVersion(Devices devices, AppVersion appVersion, File file);

    boolean isAppVersionSupported(Devices devices, AppVersion appVersion);
    boolean checkForInstall(Devices devices, AppVersion appVersion);
    boolean checkForUpdates(Devices devices,AppVersion appVersion);
    List<Devices> getDevicesByOsTypeAndVersionBelow(OSType osType,Double osVersion);

}
