package badgamesinc.hypnotic.module.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class NameTags extends Mod {

	public NumberSetting scale = new NumberSetting("Scale", 15, 3, 15, 0.1);
	public BooleanSetting armor = new BooleanSetting("Armor", true);
	public BooleanSetting healthbar = new BooleanSetting("Healthbar", true);
	public BooleanSetting background = new BooleanSetting("Background", true);
	
    public NameTags(){
        super("NameTags", Keyboard.KEY_NONE, Category.RENDER, "Renders a custom nametag above players");
        addSettings(scale, armor, healthbar, background);
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
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    @EventTarget
    public void event3d(Event3D event) {
    	for (EntityPlayer entity1 : mc.theWorld.playerEntities) {

            if (entity1.isInvisible() || entity1 == mc.thePlayer)
                continue;

            GL11.glPushMatrix();


            double x = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
            double y = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
            double z = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;


            GL11.glTranslated(x, y + entity1.getEyeHeight() + 1.7, z);
            GL11.glNormal3f(0, 1, 0);
            if (mc.gameSettings.thirdPersonView == 2) {
                GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0, 1, 0);
                GlStateManager.rotate(-mc.getRenderManager().playerViewX, 1, 0, 0);
            } else {
                GlStateManager.rotate(-mc.thePlayer.rotationYaw, 0, 1, 0);
                GlStateManager.rotate(mc.thePlayer.rotationPitch, 1, 0, 0);
            }
            float distance = mc.thePlayer.getDistanceToEntity(entity1),
                    scaleConst_1 = 0.02672f, scaleConst_2 = 0.10f;
            double maxDist = 7.0;


            float scaleFactor = (float) (distance <= maxDist ? maxDist * scaleConst_2 : (double) (distance * scaleConst_2));
            scaleConst_1 *= scaleFactor;

            float scaleBet = (float) (scale.getValue() * 15E-3);
            scaleConst_1 = Math.min(scaleBet, scaleConst_1);


            GL11.glScalef(-scaleConst_1, -scaleConst_1, .2f);

            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            boolean isDeveloper = entity1.getName().equalsIgnoreCase("BadGamesInc") || 
            		entity1.getName().equalsIgnoreCase("KawaiiZenbo") || 
            		entity1.getName().equalsIgnoreCase("PowerMacG5") || 
            		entity1.getName().equalsIgnoreCase("PCPinger");
            

            String colorCode = entity1.getHealth() > 15 ? "\247a" : entity1.getHealth() > 10 ? "\247e" : entity1.getHealth() > 7 ? "\2476" : "\247c";
            int colorrectCode = entity1.getHealth() > 15 ? 0xff4DF75B : entity1.getHealth() > 10 ? 0xffF1F74D : entity1.getHealth() > 7 ? 0xffF7854D : 0xffF7524D;
            String thing = entity1.getName() + " " + colorCode + (int) entity1.getHealth();
            float namewidth = (float) fontRenderer.getStringWidth(thing) + fontRenderer.getStringWidth((isDeveloper ? ColorUtils.purple + " DEV" : "")) + 0;


            Gui.drawRect(-namewidth / 2 - 2, 42, namewidth / 2 + 2, 40, 0x90080808);


            if (healthbar.isEnabled())
                Gui.drawRect(-namewidth / 2 - 15, 42, namewidth / 2 + 15 - (1 - (entity1.getHealth() / entity1.getMaxHealth())) * (namewidth + 4), 40, colorrectCode);

            if (background.isEnabled())
                Gui.drawRect(-namewidth / 2 - 15, 20, namewidth / 2 + 15, 40, 0x90202020);
            
            fontRenderer.drawString(entity1.getName() + (isDeveloper ? ColorUtils.purple + "  DEV" : ""), -namewidth / 2 - 15 + 4, 23, -1, true);
            fontRenderer.drawString(colorCode + (int) entity1.getHealth(), namewidth / 2, 23, -1, true);

            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);


            double movingArmor = 1.2;

            if (namewidth <= 65) {
                movingArmor = 2;
            }
            if (namewidth <= 85) {
                movingArmor = 1.2;
            }

            if (namewidth <= 100) {
                movingArmor = 1.1;
            }

            if (armor.isEnabled()) {
                for (int index = 0; index < 5; index++) {

                    if (entity1.getEquipmentInSlot(index) == null)
                        continue;


                    renderItem(entity1.getEquipmentInSlot(index), (int) (index * 19 / movingArmor) - 40, -10);


                }
            }

            GL11.glPopMatrix();

        }
    }
}
