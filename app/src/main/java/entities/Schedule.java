package entities;

import java.io.Serializable;
import java.util.*;

public class Schedule implements Serializable {
    /**
     * The Schedule that contains all the events.
     * Instance Attributes:
     * events: The list of events corresponding to this schedule.
     *
     * Representation Invariants:
     * - 0 <= events.size()
     */
    private List<Event> events;

    public Schedule(List<Event> events){
        this.events = events;
    }

    public Schedule(){ this.events = new ArrayList<>();}

    /**
     * Gets the events in this schedule
     * @return  The events in this Schedule
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     *  Adds a list of events to this schedule.
     * @param events List of events to be added to the schedule.
     */
    public void addEvents(List<Event> events){
        this.events.addAll(events);
    }

    /**
     * Adds an event to the schedule with the event's name, description and a mapping of a day to a time.
     * @param name          Name of the event.
     * @param description   Description of the event.
     * @param timeStamp     Mapping of a day to a time. The time the event takes place.
     */
    public void addEvent(String name, String description, Map<String, Double> timeStamp){
        // First we create a new event:
        Event event = new Event(name, description, timeStamp);

        // Then add it to the list.
        this.events.add(event);
    }

    /**
     * Adds an event to the schedule with the event's name, description, the day the event takes place
     * and the hour it takes place. This hides the internal representation of how an event is made.
     *
     * @param name          Name of the event.
     * @param description   Description of the event.
     * @param day           The day the event takes place.
     * @param time          The time the event takes place.
     */
    public void addEvent(String name, String description, String day, double time){
        // Make a time stamp and call the other addEvent.
        Map<String, Double> timeStamp = Event.makeTimeStamp(day, time);

        // Just call the other addEvent method to add the event. No need to repeat code.
        this.addEvent(name, description, timeStamp);
    }

    /**
     * Removes a specific event from the schedule.
     * @param event An event to be removed from the schedule.
     */
    public void removeEvent(Event event){
        this.events.remove(event);
    }

    /**
     * Removes all the events in this schedule.
     */
    public void removeAllEvents(){
        this.events.clear();
    }

    /**
     *  A toString method that returns a string representation of the schedule. Formatted to
     *  look nice.
     *
     * @return  A string representation of the Schedule.
     */
    @Override
    public String toString() {
        Map<String, List<Event>> sortedEvents = sortEvents();
        StringBuilder scheduleRep = new StringBuilder();
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                                     "Sunday"};

        for (String day: days){
            scheduleRep.append(day).append(": \n");

            if (sortedEvents.containsKey(day)) {
                for (Event event : sortedEvents.get(day)) {
                    String eventName = "    " + event.getName() + "\n";
                    String eventDescription = event.getDescription() + " \n";
                    String eventHour = "    " + event.decimalToHourFormat();

                    // Add the strings to the string builder.
                    scheduleRep.append(eventName);
                    scheduleRep.append(eventHour);
                    scheduleRep.append(" - ").append(eventDescription);

                }
            } else {
                scheduleRep.append("Nothing today \n");
            }
        }

        return scheduleRep.toString();
    }

    /**
     *  This method organizes the events so that events occurring in the same
     *  day are grouped together. And events occurring on the same day are also
     *  organized according to what time they occur. Earliest to latest.
     *
     * @return A map mapping a day to the events occurring in that day, with each
     *         associated event list being sorted according to time.
     */
    public Map<String, List<Event>> sortEvents(){
        // Sort the events by day.
        Map<String, List<Event>> newDictOfEvents = sortEventsByDay();

        // Sort the events by hour:
        for (String day: newDictOfEvents.keySet()){
            // get the list of events for that day.
            List<Event> eventList = newDictOfEvents.get(day);

            //sort the list.
            Collections.sort(eventList);
        }

        return newDictOfEvents;
    }

    /**
     * Sorts the events in the events attribute into a dictionary mapping a
     * day to events taking place on that day.
     *
     * @return The dictionary made from organizing the events by day.
     */
    private Map<String, List<Event>> sortEventsByDay() {
        // Start and accumulator collection. This is a dictionary mapping
        // days to events occurring on that day.
        Map<String, List<Event>> newDictOfEvents = new HashMap<>();

        // iterate through the list of events:
        for(Event event : this.events){
            // First get the Event's day.
            String day = event.getDay();

            // Check if the key already exists in the mapping
            if (newDictOfEvents.containsKey(day)){
                // if it exists, add to it.
                newDictOfEvents.get(day).add(event);
            } else {
                // if it doesn't, make a new mapping.
                List<Event> eventList = new ArrayList<>();
                eventList.add(event);
                newDictOfEvents.put(day, eventList);
            }
        }

        return newDictOfEvents;
    }

    /**
     * Returns the number of events in this schedule
     * @return  The number of events in this schedule.
     */
    public int getNumberOfEvents(){
        return events.size();
    }

    /**
     * @return The times of the events in this schedule.
     */
    public String[] getEventTimes(){
        String[] times = new String[events.size()];
        for (int i = 0; i < times.length; i++){
            String day = events.get(i).getDay();
            String hour = events.get(i).decimalToHourFormat();
            times[i] = day + ": " + hour;
        }
        return times;
    }

    /**
     * Sets the times of the events, ONLY IF all the event descriptions and names are the same.
     *
     * Preconditions:
     * - All the names and descriptions of the events of this schedule are the same.
     * @param times     The new times of the events.
     */
    public void setEventTimes(List<Map<String, Double>> times){
        // Since all the event names and descriptions are the same, just get the name and
        // descriptions from the first event.
        String eventName = events.get(0).getName();
        String eventDescription = events.get(0).getDescription();

        // Delete all the old events:
        events.clear();

        for (Map<String, Double> time: times){
            addEvent(eventName, eventDescription, time);
        }
    }

    public void setEventNames(String newName){
        for (Event event : events) {
            event.setName(newName);
        }
    }

    public void setEventDescriptions(String newDescription){
        for (Event event : events) {
            event.setDescription(newDescription);
        }
    }


}
