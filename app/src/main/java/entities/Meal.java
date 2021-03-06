package entities;

import java.util.HashMap;
import java.util.Map;

public class Meal extends OtherActivities{
    /**
     * This is a Class that stores and Does everything related to Meal times.
     * Instance Attributes:
     * schedule: This Meal Class' OtherActivitiesSchedule
     * times: This Meal Class' sleep and wakeup times
     */


    public Meal() {
        super();
    }


    /**
     * Creates an OtherActivitiesSchedule for this Meal Class. The OtherActivitiesSchedule is empty if the class is
     * being instantiated for the first time and is not empty after the user sets Meal times
     */
    @Override
    public void createSchedule() {
        String[]  daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        this.schedule.removeAllEvents();

        // Checks if the meal times isEmpty() which indicates that the MealClass is being created for
        // the first time and therefore there are no set meal times so the ActivitiesSchedule is empty
        if (!(times.isEmpty())) {
            for (String i : daysOfWeek) {
                for (Double time : this.times) {
                    Map<String, Double> meal = new HashMap<>();
                    meal.put(i, time);

                    // Add the events to the schedule.
                    schedule.addEvent("Meal Time", "Time to eat", meal);
                }
            }
        }
    }

}
