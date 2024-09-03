package models;

import models.enums.SlotTiming;
import models.enums.WorkoutType;

public class Slots {
    private int slotId;
    private int capacity;
    private SlotTiming slotTiming;
    private WorkoutType workoutType;
    private Center center;

    @Override
    public String toString() {
        return "Slots{" +
                "slotId=" + slotId +
                ", capacity=" + capacity +
                ", slotTiming=" + slotTiming +
                ", workoutType=" + workoutType +
                ", center=" + center +
                '}';
    }

    public Center getCenter() {
        return center;
    }
    public void setCenter(Center center) {
        this.center = center;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public SlotTiming getTiming() {
        return slotTiming;
    }

    public void setTiming(SlotTiming slotTiming) {
        this.slotTiming = slotTiming;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }
}
