package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;

public class Event2D extends Event {
    private float width, height;

    public Event2D(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
