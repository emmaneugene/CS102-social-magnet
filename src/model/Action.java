package model;

public abstract class Action {
    private String label;

    private char trigger;

    public Action(String label, char trigger) {
        this.label = label;
        this.trigger = trigger;
    }

    public String getLabel() {
        return label;
    }

    public char getTrigger() {
        return trigger;
    }
}
