package service;

import models.Address;
import models.Center;
import models.Slots;
import models.User;
import models.enums.SlotTiming;
import models.enums.WorkoutType;

import java.time.LocalDate;
import java.util.List;

public interface CenterManager {
    Center addCenter(String centerName, Address address, User user);
    Slots addSlots(User users, Integer centerId, Integer capacity, WorkoutType workoutType, SlotTiming slotTiming, LocalDate date);
    List<Slots> viewWorkoutsForDay(LocalDate date);
    void updateSlotCapacity(Integer slotId, Integer newCapacity);
    Slots getSlotById(Integer slotId);
}
