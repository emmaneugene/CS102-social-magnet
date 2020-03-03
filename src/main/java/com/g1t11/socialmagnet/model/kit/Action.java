package com.g1t11.socialmagnet.model.kit;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Action)) return false;
        Action other = (Action) o;
        return Objects.equals(label, other.label)
            && Objects.equals(trigger, other.trigger);
    }
}
