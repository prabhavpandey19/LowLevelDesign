package com.example.phonepe.service;

import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;
import com.example.versionManagement.model.enums.RollOutStrategy;
import com.example.versionManagement.repository.AppManagementRepository;
import com.example.versionManagement.repository.FileRepository;
import com.example.versionManagement.service.impl.AppManagementServiceImpl;
import com.example.versionManagement.service.impl.DeviceManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppManagementTest {

    @Mock
    private AppManagementRepository appManagementRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private DeviceManagementServiceImpl deviceManagementService;

    @InjectMocks
    private AppManagementServiceImpl appManagementService;

    private List<Devices> devicesList;
    private Devices device;
    private AppVersion appVersion;
    private File file;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        devicesList = new ArrayList<>();
        // create a device object
        AppVersion androidApp = new AppVersion();
        androidApp.setAppVersionId(1L);
        androidApp.setAppName("TestApp");
        androidApp.setOsType(OSType.ANDROID);
        androidApp.setFileUrl("fileUrl");
        AppVersion iosApp = new AppVersion();
        iosApp.setAppVersionId(2L);
        iosApp.setAppName("TestApp@");
        iosApp.setOsType(OSType.IOS);

        device = new Devices();
        device.setOs(OSType.ANDROID);
        device.setMakeName("Samsung");
        device.setModelName("Galaxy");
        device.setOsVersion(10.0);
        device.setAppInstalled(androidApp);

        devicesList.add(device);
        appVersion = androidApp;
        file = new File("path/to/file");

        devicesList.add(device);
    }

    @Test
    public void testUploadNewVersion() {
        when(fileRepository.uploadFile(file)).thenReturn("fileUrl");
        when(appManagementRepository.saveNewAppVersion(any())).thenReturn(appVersion);
        AppVersion result = appManagementService.uploadNewVersion("TestApp", OSType.ANDROID, file, 1.0);
        assertEquals("TestApp", result.getAppName());
        assertEquals("fileUrl", result.getFileUrl());
    }

    @Test
    public void testReleaseVersion() {
        when(deviceManagementService.getDevicesByOsType(OSType.ANDROID)).thenReturn(devicesList);
        appManagementService.releaseVersion(appVersion, RollOutStrategy.ANDROID);
        verify(deviceManagementService, times(1)).getDevicesByOsType(OSType.ANDROID);
    }

    @Test
    public void testExecuteTask() {
        // Test for install App
        when(deviceManagementService.checkForInstall(device, appVersion)).thenReturn(true);
        when(fileRepository.getFile(any())).thenReturn(file);
        appManagementService.executeTask(devicesList, appVersion);
        verify(deviceManagementService, times(2)).installVersion(device, appVersion, file);

        // Test for update App
        when(deviceManagementService.checkForInstall(device, appVersion)).thenReturn(false);
        when(deviceManagementService.checkForUpdates(device, appVersion)).thenReturn(true);
        appManagementService.executeTask(devicesList, appVersion);
        verify(fileRepository, times(2)).createDiffPack(any(), any());
        verify(deviceManagementService, times(2)).updateVersion(any(),any(), any());
    }

    @Test
    public void testCreateUpdatePatch() {
        File previousFile = new File("path/to/previousFile");
        File newFile = new File("path/to/newFile");
        when(fileRepository.getFile(appVersion.getFileUrl())).thenReturn(previousFile, newFile);
        appManagementService.createUpdatePatch(device, appVersion, appVersion);
        verify(fileRepository, times(1)).createDiffPack(previousFile, newFile);
        verify(deviceManagementService, times(1)).updateVersion(any(),any(), any());
    }
}
