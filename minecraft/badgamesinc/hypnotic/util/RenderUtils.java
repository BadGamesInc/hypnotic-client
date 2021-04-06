package badgamesinc.hypnotic.util;

import net.minecraft.client.Minecraft;

public class RenderUtils {

// Made by lavaflowglow 11/19/2020 3:39 AM
    
    public static boolean SetCustomYaw = false;
    public static float CustomYaw = 0;
    
    public static void setCustomYaw(float customYaw) {
        CustomYaw = customYaw;
        SetCustomYaw = true;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = customYaw;
    }
    
    public static void resetPlayerYaw() {
        SetCustomYaw = false;
    }
    
    public static float getCustomYaw() {
        
        return CustomYaw;
        
    }
    public static boolean SetCustomPitch = false;
    public static float CustomPitch = 0;
    
    public static void setCustomPitch(float customPitch) {
        CustomPitch = customPitch;
        SetCustomPitch = true;
    }
    
    public static void resetPlayerPitch() {
        SetCustomPitch = false;
    }
    
    public static float getCustomPitch() {
        
        return CustomPitch;
        
    }
    
    // Made by lavaflowglow 11/19/2020 3:39 AM
    
}
