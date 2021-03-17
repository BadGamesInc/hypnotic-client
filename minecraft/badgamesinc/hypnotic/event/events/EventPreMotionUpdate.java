package badgamesinc.hypnotic.event.events;

import badgamesinc.hypnotic.event.Event;

public class EventPreMotionUpdate extends Event {

	private float yaw, pitch;
	private boolean ground;
	public double x, y, z;
	
	public EventPreMotionUpdate(float yaw, float pitch, boolean ground, double x, double y, double z) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.ground = ground;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	
}
