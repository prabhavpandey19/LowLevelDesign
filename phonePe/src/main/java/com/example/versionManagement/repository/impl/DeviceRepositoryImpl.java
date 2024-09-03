package com.example.versionManagement.repository.impl;

import com.example.versionManagement.model.Devices;
import com.example.versionManagement.repository.DeviceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
@Component
public class DeviceRepositoryImpl implements DeviceRepository {
    @Override
    public boolean installApp(Devices device, File file) {
        return true;
    }

    @Override
    public boolean updateApp(Devices device, File file) {
        return true;
    }
}
