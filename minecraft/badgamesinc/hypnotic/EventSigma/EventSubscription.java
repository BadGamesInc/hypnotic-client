package badgamesinc.hypnotic.EventSigma;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.util.MinecraftUtil;


/**
 * Keeps track of subscribed listeners for a given assigned event.
 */
public class EventSubscription<T extends Event> implements MinecraftUtil{

    private final T event;
    private final List<EventListener> subscribed = new CopyOnWriteArrayList<>();

    public EventSubscription(T event) {
        this.event = event;
    }

    public void fire(Event event) {
        if (mc.thePlayer == null) {
            return;
        }
        subscribed.forEach(listener -> listener.onEvent(event));
    }

    public void add(EventListener listener) {
        subscribed.add(listener);
    }

    public void remove(EventListener listener) {
        if (subscribed.contains(listener)) {
            subscribed.remove(listener);
        }
    }

    public List<EventListener> getSubscribed() {
        return subscribed;
    }

    public Event getEvent() {
        return event;
    }

}
