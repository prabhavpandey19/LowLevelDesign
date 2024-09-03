package com.example.versionManagement.repository;

import com.example.versionManagement.model.Devices;

import java.io.File;

public interface DeviceRepository {
    boolean installApp(Devices device, File file);
    boolean updateApp(Devices device, File file);

}
