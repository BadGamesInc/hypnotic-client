package badgamesinc.hypnotic.event;



import java.lang.reflect.InvocationTargetException;

import badgamesinc.hypnotic.Hypnotic;

/**
 * Created by Hexeption on 18/12/2016.
 */
public abstract class Event {

    /**
     *
     * Main events you may need:
     *
     * Minecraft:
     * - EventKeyboard
     * - EventMiddleClick
     * - EventTick
     *
     * EntityPlayerSP:
     * - EventUpdate
     * - EventPreMotionUpdates
     * - EventPostMotionUpdates
     *
     * GuiIngame:
     * - EventRender2D
     *
     * EntityRenderer:
     * - EventRender3D
     *
     */

    private boolean cancelled;
    
    public EventType type;
    public EventDirection direction;
    
    public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	public EventDirection getDirection() {
		return direction;
	}
	public void setDirection(EventDirection direction) {
		this.direction = direction;
	}

    public enum State {
        PRE("PRE", 0),

        POST("POST", 1);

        State(final String string, final int number) {

        }
    }

    public Event call() {

        this.cancelled = false;
        call(this);
        return this;
    }

    public boolean isCancelled() {

        return cancelled;
    }

    public void setCancelled(boolean cancelled) {

        this.cancelled = cancelled;
    }

    private static final void call(final Event event) {

        final ArrayHelper<Data> dataList = Hypnotic.instance.eventManager.get(event.getClass());

        if (dataList != null) {
            for (final Data data : dataList) {

                try {
                    data.target.invoke(data.source, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    
    public boolean isBeforePre() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.BEFOREPRE;
		
	}
	
	public boolean isPre() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.PRE;
		
	}
	
	public boolean isBeforePost() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.BEFOREPOST;
		
	}
	
	public boolean isPost() {
		if (type == null) {
			return false;
		}
		
		return type == EventType.POST;
		
	}
	
	public boolean isIncoming() {
		if (direction == null) {
			return false;
		}
		
		return direction == EventDirection.INCOMING;
		
	}
	
	public boolean isOutgoing() {
		if (direction == null) {
			return false;
		}
		
		return direction == EventDirection.OUTGOING;
		
	}
}
