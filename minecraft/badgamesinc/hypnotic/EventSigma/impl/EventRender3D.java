package badgamesinc.hypnotic.EventSigma.impl;

import badgamesinc.hypnotic.EventSigma.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class EventRender3D extends Event {
    private boolean offset;
    public float renderPartialTicks;
    private int x, y, z, ix, iy, iz;

    public void fire(float renderPartialTicks, int x, int y, int z) {
        this.renderPartialTicks = renderPartialTicks;
        this.x = x;
        this.y = y;
        this.z = z;
        ix = x;
        iy = y;
        iz = z;
        super.fire();
    }

    public boolean isOffset() {
        return offset;
    }

    public void offset(int renderOffsets) {
        double ox = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double oy = Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double oz = Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        if (renderOffsets < 0) {
            GlStateManager.translate(-ox, -oy, -oz);
            x -= ox;
            y -= oy;
            z -= oz;
            offset = true;
        } else if (renderOffsets > 0) {
            GlStateManager.translate(ox, oy, oz);
            x += ox;
            y += oy;
            z += oz;
            offset = true;
        }
    }

    public void reset() {
        x = ix;
        y = iy;
        z = iz;
        offset = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
