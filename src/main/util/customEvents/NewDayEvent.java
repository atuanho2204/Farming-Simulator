package main.util.customEvents;

public class NewDayEvent {
    private final Integer newDay;

    public Integer getNewDay() {
        return newDay;
    }

    public NewDayEvent(int newDay) {
        this.newDay = newDay;
    }
}
