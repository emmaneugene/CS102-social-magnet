package model;

public abstract class Action {
    private String label;

    private String trigger;

    public Action(String label, String trigger) {
        this.label = label;
        this.trigger = trigger;
    }

    public String getLabel() {
        return label;
    }

    public String getTrigger() {
        return trigger;
    }
}
