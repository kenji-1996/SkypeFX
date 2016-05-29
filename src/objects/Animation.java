package objects;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kenji on 12/04/2016.
 */
public class Animation {
    private ArrayList<String> inputs;
    private long delay;
    private boolean enabled;

    public Animation() {

    }

    public Animation( ArrayList<String> inputs, long delay,boolean enabled) {
        this.inputs = inputs;
        this.delay = delay;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList inputs) {
        this.inputs = inputs;
    }

    public void addInputs(String input) {
        this.inputs.add(input);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "objects.Animation{" +
                "inputs=" + inputs +
                ", delay=" + delay +
                ", enabled=" + enabled +
                '}';
    }
}
