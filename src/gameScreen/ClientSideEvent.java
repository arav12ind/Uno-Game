package gameScreen;

import javafx.event.Event;
import javafx.event.EventType;

public class ClientSideEvent extends Event {
    public static final EventType<ClientSideEvent> RECEIVE_CARD_EVENT_TYPE  =  new EventType<>(EventType.ROOT, "RECEIVE_CARD_EVENT_TYPE");
    public static final EventType<ClientSideEvent> START_SIGNAL_RECEIVED_EVENT_TYPE  =  new EventType<>(EventType.ROOT, "START_SIGNAL_RECEIVED_EVENT_TYPE");
    public ClientSideEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
