package service.impl;

import exception.FlipFitException;
import models.Address;
import models.Center;
import models.Slots;
import models.User;
import models.enums.SlotTiming;
import models.enums.WorkoutType;
import service.CenterManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CenterManagerImpl implements CenterManager {
    private Map<Integer, Center> centerIdVsCenter;
    private Integer counter;
    private Integer slotCounter;
    private Map<String, Center> centerNameVsCenter;
    private Map<Integer, Map<LocalDate,List<Slots>>> centerIdVsSlots;
    private Map<Integer, Slots> slotIdVsSlots;


    private static CenterManagerImpl instance;
    private CenterManagerImpl() {
        counter = 0;
        slotCounter = 0;
        centerIdVsCenter = new HashMap<>();
        centerNameVsCenter = new HashMap<>();
        centerIdVsSlots = new HashMap<>();
        slotIdVsSlots = new HashMap<>();
    }

    public static CenterManagerImpl getInstance() {
        if (instance == null) {
            synchronized (CenterManagerImpl.class) {
                if (instance == null) {
                    instance = new CenterManagerImpl();
                }
            }
        }
        return instance;
    }

    private Integer getCounter() {
        synchronized (counter) {
            return counter++;
        }
    }

    @Override
    public Center addCenter(String centerName, Address address, User user) {
        if (!user.isAdmin()) {
            throw new FlipFitException("User is not authorized to add center");
        }
        Center center = new Center();
        center.setCenterId(getCounter());
        center.setCenterName(centerName);
        center.setAddress(address);
        centerIdVsCenter.put(center.getCenterId(), center);
        centerNameVsCenter.put(center.getCenterName(), center);
        return center;
    }

    @Override
    public Slots addSlots(User users, Integer centerId, Integer capacity, WorkoutType workoutType, SlotTiming slotTiming, LocalDate date) {
        if (!users.isAdmin()) {
            throw new FlipFitException("User is not authorized to add slots");
        }
        Center center = centerIdVsCenter.get(centerId);
        if (center == null) {
            throw new FlipFitException("Center not found with centerId: " + centerId);
        }
        Slots slots = new Slots();
        slots.setSlotId(getSlotCounter());
        slots.setCapacity(capacity);
        slots.setWorkoutType(workoutType);
        slots.setTiming(slotTiming);
        slots.setCenter(center);
        centerIdVsSlots.putIfAbsent(centerId, new HashMap<>());
        centerIdVsSlots.get(centerId).putIfAbsent(date, new ArrayList<>());
        centerIdVsSlots.get(centerId).get(date).add(slots);
        slotIdVsSlots.put(slots.getSlotId(), slots);
        return slots;
    }

    @Override
    public List<Slots> viewWorkoutsForDay(LocalDate date) {
        return centerIdVsSlots.values().stream()
                .filter(map -> map.containsKey(date))
                .flatMap(map -> map.get(date).stream())
                .filter(slot -> slot.getCapacity() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public void updateSlotCapacity(Integer slotId, Integer newCapacity) {
        Slots slots = slotIdVsSlots.get(slotId);
        if (slots == null) {
            throw new FlipFitException("Slot not found with slotId: " + slotId);
        }
        slots.setCapacity(newCapacity);
    }

    @Override
    public Slots getSlotById(Integer slotId) {
        Slots slot = slotIdVsSlots.get(slotId);
        if (slot == null) {
            throw new FlipFitException("Slot not found with slotId: " + slotId);
        }
        return slot;
    }

    private int getSlotCounter() {
        synchronized (slotCounter) {
            return slotCounter++;
        }
    }
}
