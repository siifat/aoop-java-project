package com.to_do_list;

import java.time.LocalDate;

public class ToDoItem
{
    private final String shortDescription;
    private final String details;
    private final LocalDate deadLine;
    public ToDoItem(String shortDescription, String details, LocalDate deadLine) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadLine = deadLine;
    }

    //to ensure that you get the short description not the object reference this relates to the listView

    public String shortDescription() {
        return shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public LocalDate getDeadline() {
        return deadLine;
    }
}
