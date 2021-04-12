package badgamesinc.hypnotic.module.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.gui.clickgui.util.FontUtil;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.font.FontUtils;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class NameTags extends Mod {

	UnicodeFontRenderer ufr;
    public NameTags(){
        super("NameTags", Keyboard.KEY_NONE, Category.RENDER, "Renders a custom nametag above players");
    }


    public static void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, y + 8);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        //NameTags.renderEnchantText(stack, x, y);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

    @EventTarget
    public void on3D(Event3D event){
        if(ufr == null) {
            ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 20, 0, 2, 1);

        }
        for(Entity e : mc.theWorld.loadedEntityList){
            if(e instanceof EntityLivingBase && !(e instanceof EntityPlayerSP)){
                EntityLivingBase entity = (EntityLivingBase) e;
                if(entity instanceof EntityPlayer){
                    EntityPlayer player = (EntityPlayer) entity;
                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.mc.timer.renderPartialTicks
                            - this.mc.getRenderManager().renderPosX;
                    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.mc.timer.renderPartialTicks
                            - this.mc.getRenderManager().renderPosY;
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.mc.timer.renderPartialTicks
                            - this.mc.getRenderManager().renderPosZ;


                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y + 2.5D, z);
                    GL11.glScalef(-0.03f, -0.03f, -0.03f);

                    GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0d, 1.0d, 0.0d);
                    GL11.glRotated(this.mc.getRenderManager().playerViewX, 1.0d, 0.0d, 0.0d);
                    GlStateManager.disableDepth();
                    float width = ufr.getStringWidth(player.getName());
                    float progress = player.getHealth() / player.getMaxHealth();

                    Color color = Color.WHITE;
                    if(player.getHealth() > 15){
                        color = Color.GREEN;
                    }else if(player.getHealth() > 7 && player.getHealth() <= 15){
                        color = Color.YELLOW;
                    }else if(player.getHealth() <= 7){
                        color = Color.RED;
                    }
                    Gui.drawRect(-width / 2 - 5, -2, width / 2 + 5, ufr.FONT_HEIGHT + 2, new Color(0, 0, 0, 80).getRGB());
                    Gui.drawRect(-width / 2 - 5, ufr.FONT_HEIGHT + 1,  -width / 2 - 5 + (width / 2 + 5 - -width / 2 + 5) * progress, ufr.FONT_HEIGHT + 2, color.getRGB());
                    FontUtil.drawCenteredString(player.getName(), 0, 0, 0xFFFFFFFF);
                    GL11.glTranslated(-x, -(y + 2.5D), -z);
                    GL11.glScalef(1.0f,1.0f,1.0f);
                    GlStateManager.enableDepth();
                    GL11.glPopMatrix();



                }
            }
        }
    }
}
