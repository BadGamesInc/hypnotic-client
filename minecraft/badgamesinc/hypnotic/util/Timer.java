package badgamesinc.hypnotic.util;

public class Timer {

    private long prevMS;
    public long lastMS = System.currentTimeMillis();

    public Timer() {
        this.prevMS = 0L;
    }

    public boolean delay(float milliSec) {
        return (float) (getTime() - this.prevMS) >= milliSec;
    }

    public void reset() {
        this.prevMS = getTime();
    }

    public long getDifference() {
        return getTime() - this.prevMS;
    }

    public void setDifference(long difference) {
        this.prevMS = (getTime() - difference);
    }
    
    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public void setTime(long time) {
        lastMS = time;
    }

}
