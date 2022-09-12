package com.andraskrausz.restfulapp.enums;

public enum TaskStates {
    PENDING("pending"),
    DONE("done");

    private final String label;
    TaskStates(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
