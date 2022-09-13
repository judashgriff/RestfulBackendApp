package com.andraskrausz.restfulapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author András Krausz
 */

public class TaskDTO {
    private String name;
    private String description;
    @JsonAlias("date_time")
    private String dateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    /**
     * Formats the received dateTime string to the required format and sets the dateTime of the current object with it.
     *
     * @param dateTime a string containing a date-time
     */
    public void setDateTime(String dateTime) {
        String[] dateAndTime = dateTime.split(" ");
        String[] dateParts = dateAndTime[0].split("-");
        String[] timeParts = dateAndTime[1].split(":");
        dateParts[1] = ("0" + dateParts[1]).substring(Math.max((dateParts[1].length() + 1) - 2, 0));
        dateParts[2] = ("0" + dateParts[2]).substring(Math.max((dateParts[2].length() + 1) - 2, 0));

        timeParts[0] = ("0" + timeParts[0]).substring(Math.max((timeParts[0].length() + 1) - 2, 0));
        timeParts[1] = ("0" + timeParts[1]).substring(Math.max((timeParts[1].length() + 1) - 2, 0));
        timeParts[2] = ("0" + timeParts[2]).substring(Math.max((timeParts[2].length() + 1) - 2, 0));

        dateTime = String.join("-", dateParts) + " " + String.join(":", timeParts);
        this.dateTime = dateTime;
    }
}
