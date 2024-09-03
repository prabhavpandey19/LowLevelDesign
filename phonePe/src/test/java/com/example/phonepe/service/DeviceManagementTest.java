package com.example.phonepe.service;

import com.example.versionManagement.exception.NotSupportedVersionException;
import com.example.versionManagement.model.AppVersion;
import com.example.versionManagement.model.Devices;
import com.example.versionManagement.model.enums.OSType;
import com.example.versionManagement.repository.DeviceRepository;
import com.example.versionManagement.service.DeviceManagementService;
import com.example.versionManagement.service.impl.DeviceManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceManagementTest {


    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceManagementServiceImpl deviceManagementService;


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] a = nums1;
        int[] b = nums2;
        int total = nums1.length + nums2.length;
        if (a.length > b.length) {
            int[] temp = a;
            a=b;
            b=temp;
        }
        int half = total/2;
        int l=0,r=a.length-1;
        while (l<=r) {
            int mid = l + (r-l)/2;

        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AppVersion androidApp = new AppVersion();
        androidApp.setAppVersionId(1L);
        androidApp.setAppName("TestApp");
        androidApp.setOsType(OSType.ANDROID);
        AppVersion iosApp = new AppVersion();
        iosApp.setAppVersionId(2L);
        iosApp.setAppName("TestApp@");
        iosApp.setOsType(OSType.IOS);
        deviceManagementService.addVehicle(OSType.ANDROID, "Samsung", "Galaxy", 10.0, androidApp);
        deviceManagementService.addVehicle(OSType.IOS, "Apple", "iPhone", 14.0, iosApp);
    }

    @Test
    public void testGetAllDevicesList() {
        List<Devices> result = deviceManagementService.getAllDevicesList();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetDevicesByOsType() {
        List<Devices> result = deviceManagementService.getDevicesByOsType(OSType.ANDROID);
        assertEquals(1, result.size());

        List<Devices> result1 = deviceManagementService.getDevicesByOsType(OSType.IOS);
        assertEquals(1, result1.size());
    }

    @Test
    public void testInstallVersion() {
        AppVersion androidApp = new AppVersion();
        androidApp.setAppVersionId(2L);
        androidApp.setAppName("TestApp");
        androidApp.setOsType(OSType.ANDROID);
        File file = new File("TestApp.apk");
        Devices device = deviceManagementService.getAllDevicesList().get(0);
        // mock deviceRepository.installApp(devices,file) method
        mock(DeviceRepository.class);
        when(deviceRepository.installApp(device, file)).thenReturn(true);
        deviceManagementService.installVersion(device, androidApp, file);
        verify(deviceRepository, times(1)).installApp(device, file);
    }

    @Test
    public void testInstallVersionNotSupported() {
        AppVersion iosApp = new AppVersion();
        iosApp.setAppVersionId(2L);
        iosApp.setAppName("TestApp@");
        iosApp.setOsType(OSType.IOS);
        File file = new File("TestApp.APK");
        Devices device = deviceManagementService.getDevicesByOsType(OSType.ANDROID).get(0);
        assertThrows(NotSupportedVersionException.class, () -> deviceManagementService.installVersion(device, iosApp, file));
    }

}
