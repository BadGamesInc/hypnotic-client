package badgamesinc.hypnotic.EventSigma;

public interface EventListener<E extends Event> {
    void onEvent(E event);
}
