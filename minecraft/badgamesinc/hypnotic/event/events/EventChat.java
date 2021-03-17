package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;

public class EventChat extends Event {
	
	public String message;
	
	public EventChat(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
