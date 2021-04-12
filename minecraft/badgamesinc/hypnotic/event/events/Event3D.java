package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;

public class Event3D extends Event {
    float partialTicks;
    public Event3D(float partialTicks){
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
