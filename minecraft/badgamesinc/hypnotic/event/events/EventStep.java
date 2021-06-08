package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;

public class EventStep extends Event {
    float stepHeight;
    public EventStep(float stepHeight){
        this.stepHeight = stepHeight;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }
}
