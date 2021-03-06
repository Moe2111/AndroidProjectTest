package interface_adapters;

import java.util.Scanner;

public abstract class ScheduleInputWindow extends Window{

    public ScheduleInputWindow(Scanner scanner) {
        super(scanner);
    }

    /**
     * Gets input from the user on the times they will be taking the medicine they are trying to add.
     * @return The times the user will be taking the medicine they are trying to add.
     */
    protected String[] getTimes(Scanner scanner) {
        String howManyTimesStr = selectHowManyTimes(scanner);

        int howManyTimesInt = Integer.parseInt(howManyTimesStr);
        String[] times = new String[howManyTimesInt];
        String time = " ";
        for (int i = 0; i < howManyTimesInt; i++){
            while(!isNumeric(time) || 0 > Double.parseDouble(time) || 24 <= Double.parseDouble(time)){
                System.out.println("For the "+ (i + 1) + "'st time, pick a time (In the form of XX.XX." +
                        "Note that  such as 10:30 becomes " +
                        "10.5, or 13:45 as 13.75");
                time = scanner.nextLine();
            }
            times[i] = time;
            time = " ";
        }
        return times;
    }
    /**
     * Gets input from the user whether the medicine they want to add will be taken weekly or daily
     * @return Whether the user inputted weekly or daily.
     */
    public String selectWD(Scanner scanner) {

        while (true) {
            System.out.println("Do you need to take it weekly or daily?");
            String input = scanner.nextLine();
            if (input.equals("weekly") || input.equals("daily")) {
                return input;
            }
        }
    }

    /**
     * Allows the user to select what day they want to start recording when they should take the medicine
     * @return A string representing the day to start on
     */
    public String selectDay(Scanner scanner){
        String input = "";
        while (!isNumeric(input) || 0 > Integer.parseInt(input) || Integer.parseInt(input)  > 7){
            System.out.println("What day would you like to start? Press 1 for Monday, 2 for Tuesday," +
                    "3 for Wednesday, 4 for Thursday, 5 for Friday, 6 for Saturday, 7 for Sunday");
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Gets input and allows the user to decide how many times they should take medicine in a day
     * @return an integer representing how many times in a day you will take the medicine
     */
    public String selectHowManyTimes(Scanner scanner) {

        while (true) {
            System.out.println("How many times do you need to take it in a day?");
            String input = scanner.nextLine();
            if (isNumeric(input)) {
                return input;
            }
        }
    }

    /**
     * Checks to see whether str can be converted into a double
     * @param str what it will check to see if it can be converted to a string
     * @return Whether the str can be converted to a double
     */
    protected boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
