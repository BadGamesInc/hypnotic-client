package badgamesinc.hypnotic.gui.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;

public class Notification {
    String message;
    int duration;
    NotificationType type;
    long currentTime = 0;
    private static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 20, false, false, false);
    int currentLocation = GuiScreen.width;
    int currentLastLocation = GuiScreen.width;
    float lastYloc = indexOf(this) * 42;

    public Notification(String message, int duration, NotificationType type) {
        this.message = message;
        this.duration = duration;
        this.type = type;
    }

    public void draw(){
        int index = indexOf(this);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        currentTime++;

        currentLocation = currentTime > (duration * 50) - 50 ? sr.getScaledWidth() + 10 : sr.getScaledWidth() - (fontRenderer.getStringWidth(message) + 40);

        if(currentLastLocation != currentLocation){
            float diff = currentLocation - currentLastLocation;
            currentLocation = currentLastLocation;
            currentLastLocation += diff / 8;
        }

        if(currentTime > (duration * 50)){
            Hypnotic.instance.notificationManager.notifications.remove(this);
        }

        float yloc = 42 * index;
        if(lastYloc != yloc){
            float diff = yloc - lastYloc;

            lastYloc += diff / 3;
        }

        int progress = (int) (currentTime / duration * 50);
        int hue = 360 * progress;
        int color = Color.HSBtoRGB(hue, 1, 1);


        Gui.drawRect(currentLocation + 20, sr.getScaledHeight() - 42 - (lastYloc), sr.getScaledWidth(), sr.getScaledHeight() - 10- (lastYloc),new Color(30, 30, 30, 220).getRGB());
        fontRenderer.drawString(message,currentLocation + 25, sr.getScaledHeight() - 33 - (lastYloc), -1, true);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public int indexOf(Object o){
        if (o == null) {
            for (int i = 0; i < Hypnotic.instance.notificationManager.notifications.size(); i++)
                if (Hypnotic.instance.notificationManager.notifications.toArray()[i]==null)
                    return i;
        } else {
            for (int i = 0; i < Hypnotic.instance.notificationManager.notifications.size(); i++)
                if (o.equals(Hypnotic.instance.notificationManager.notifications.toArray()[i]))
                    return i;
        }
        return -1;
    }
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
