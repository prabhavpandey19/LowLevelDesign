import models.*;
import models.enums.SlotTiming;
import models.enums.WorkoutType;
import service.BookingManager;
import service.CenterManager;
import service.UserManager;
import service.impl.BookingManagerImpl;
import service.impl.CenterManagerImpl;
import service.impl.UserManagerImpl;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserManager userManager = UserManagerImpl.getInstance();
        CenterManager centerManager = CenterManagerImpl.getInstance();
        BookingManager bookingManager = BookingManagerImpl.getInstance();
        setupCenter(centerManager, userManager);
        
        registerTestUsers(userManager);

        bookSlotsForUserOne(centerManager, userManager, bookingManager);
        bookSlotsForUser2(centerManager, userManager, bookingManager);
        System.out.println("Completed");
    }

    private static void bookSlotsForUser2(CenterManager centerManager, UserManager userManager, BookingManager bookingManager) {
        User user = userManager.login("Ravi@xyz", "user2", null, null);
        bookSlots(centerManager, bookingManager, user);
    }

    private static void bookSlots(CenterManager centerManager, BookingManager bookingManager, User user) {
        List<Slots> slots = centerManager.viewWorkoutsForDay(LocalDate.now().plusDays(1));
        System.out.println("Slots available on  " + LocalDate.now().plusDays(1)  + " :-" + slots);

        Booking booking1 = bookingManager.createBooking(user.getUserId(), slots.get(0).getSlotId(), LocalDate.now().plusDays(1));
        Booking booking2 = bookingManager.createBooking(user.getUserId(), slots.get(1).getSlotId(), LocalDate.now().plusDays(1));

        System.out.println("Booking done for " +  user.getName() + " on " + LocalDate.now().plusDays(1) + " :-" + booking1);
        System.out.println("Booking done for " +  user.getName() + " on " + LocalDate.now().plusDays(1) + " :-" + booking2);
    }

    private static void registerTestUsers(UserManager userManager) {
        userManager.createUser("Aashi", "Aashi@xyz", "user1", "1234567891", false);
        userManager.createUser("Ravi", "Ravi@xyz", "user2", "1234567880", false);
    }

    private static void bookSlotsForUserOne(CenterManager centerManager, UserManager userManager, BookingManager bookingManager) {
        User user = userManager.login(null, "user1", "1234567891", null);
        bookSlots(centerManager, bookingManager, user);
    }

    private static void setupCenter(CenterManager centerManager, UserManager userManager) {
        User admin = userManager.createUser("admin", "admin@xyz.com", "admin123", "1234567890", true);
        Center kormangala = centerManager.addCenter("Bellendur", new Address("Bellendur","Bangalore", "Karnataka", "India", "560102"), admin);
        Center bellendur = centerManager.addCenter("Kormangala", new Address("Kormangala","Bangalore", "Karnataka", "India", "560103"), admin);

        addSlots(centerManager, kormangala, admin);
        addSlots(centerManager, bellendur, admin);


    }

    private static void addSlots(CenterManager centerManager, Center center, User admin) {

        // adding slots for next 7 days
        for (int i = 0; i < 7; i++) {
            for (SlotTiming slotTiming: SlotTiming.values()) {
                centerManager.addSlots(admin, center.getCenterId(), 3, WorkoutType.CARDIO, slotTiming, LocalDate.now().plusDays(i));
                centerManager.addSlots(admin, center.getCenterId(), 3, WorkoutType.WEIGHTS, slotTiming, LocalDate.now().plusDays(i));
            }
        }

    }
}