package com.example.versionManagement.model;

import com.example.versionManagement.model.enums.OSType;
import lombok.Data;

import java.util.List;

@Data
public class Devices {
    private Long deviceId;
    private OSType os;
    private String makeName;
    private String modelName;
    private Double osVersion;
    private AppVersion appInstalled;
}
