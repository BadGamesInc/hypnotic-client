package badgamesinc.hypnotic.module.render;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event2D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TargetHUD extends Mod {
	
	public ModeSetting targetHudLook = new ModeSetting("Design", "New", "New", "Astolfo", "Compact");
	private static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("dfgdgdg", 18, false, false, false);
	private static GlyphPageFontRenderer fontRenderer2 = GlyphPageFontRenderer.create("Comfortaa-Light", 13, false, false, false);
	private float lastHealth = 0;
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER, "Displays information about KillAura targets");
		addSettings(targetHudLook);
	}
	
	@Override
	public void onUpdate() {
		this.setDisplayName("TargetHUD " + ColorUtils.white + "[" + targetHudLook.getSelected() + "]");
		super.onUpdate();
	}
	
	@EventTarget
	public void renderTargetHUD(Event2D e) {
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		TargetHUD targetHud = new TargetHUD();
		
		
		if (KillAura.target != null && Hypnotic.instance.moduleManager.getModule(KillAura.class).isEnabled()) {		
			if (Hypnotic.instance.moduleManager.targetHud.targetHudLook.is("New")) {
				if (Hypnotic.instance.moduleManager.getModule(TargetHUD.class).isEnabled()) {
		            if (KillAura.target instanceof EntityPlayer || KillAura.target instanceof EntityOtherPlayerMP) {
		                float width = (float) ((scaledResolution.getScaledWidth() / 2) + 100);
		                float height = (float) (scaledResolution.getScaledHeight() / 2);
		
		                EntityPlayer player = (EntityPlayer) KillAura.target;
		                Gui.drawRect(width - 70, height + 30, width + 80, height + 105, new Color(0, 0, 0, 180).getRGB());
		                fontRenderer.drawString(player.getName(), width - 65, height + 35, 0xFFFFFF, true);
		                fontRenderer2.drawString(player.onGround ? "On Ground" : "Off Ground", width - 65, height + 50, 0xFFFFFF, true);
		                fontRenderer2.drawString("Health: " + MathUtils.round(player.getHealth(), 2),  width - 65, height + 70, 0xFFFFFF, true);
		                fontRenderer2.drawString("Distance: " + MathUtils.round(mc.thePlayer.getDistanceToEntity(player), 2), width - 65, height + 60, -1, true);
		                //fontRenderer.drawString(player.getHealth() > mc.thePlayer.getHealth() ? "You Might Lose" : "You Might Win", width - 65, height + 80, player.getHealth() > mc.thePlayer.getHealth() ? Color.RED.getRGB() : Color.GREEN.getRGB());
		                GL11.glPushMatrix();
		                GL11.glColor4f(1, 1, 1, 1);
		                GlStateManager.scale(1.0f, 1.0f,1.0f);
		                mc.getRenderItem().renderItemAndEffectIntoGUI(player.getCurrentEquippedItem(), (int) width + 50, (int) height + 80);
		                GL11.glPopMatrix();
		
		                float health = player.getHealth();
		                float healthPercentage = (health / player.getMaxHealth());
		                float targetHealthPercentage = 0;
		                if (healthPercentage != lastHealth) {
		                    float diff = healthPercentage - this.lastHealth;
		                    targetHealthPercentage = this.lastHealth;
		                    this.lastHealth += diff / 8;
		                }
		                Color healthcolor = Color.WHITE;
		                Color healthBarColor = Color.GREEN;
		                if (healthPercentage * 100 > 75) {
		                    healthcolor = Color.GREEN.brighter();
		                } else if (healthPercentage * 100 > 50 && healthPercentage * 100 < 75) {
		                    healthcolor = Color.YELLOW.brighter();
		                } else if (healthPercentage * 100 < 50 && healthPercentage * 100 > 25) {
		                    healthcolor = Color.ORANGE.brighter();
		                } else if (healthPercentage * 100 < 25) {
		                    healthcolor = Color.RED.brighter();
		                }
		                
		                if (healthPercentage * 100 > 75) {
		                	healthBarColor = Color.GREEN;
		                } else if (healthPercentage * 100 > 50 && healthPercentage * 100 < 75) {
		                	healthBarColor = Color.YELLOW;
		                } else if (healthPercentage * 100 < 50 && healthPercentage * 100 > 25) {
		                	healthBarColor = Color.ORANGE;
		                } else if (healthPercentage * 100 < 25) {
		                	healthBarColor = Color.RED;
		                }
		                Gui.drawRect(width - 70, height + 104, width - 70 + (149 * targetHealthPercentage), height + 106, healthcolor.getRGB());
		                Gui.drawRect(width - 70, height + 104, width - 70 + (149 * healthPercentage), height + 106, healthBarColor.getRGB()    );
		                GL11.glColor4f(1, 1, 1, 1);
		                drawEntityOnScreen((int) width + 60, (int) height + 80, 20, player.rotationYaw, player.rotationPitch, player);
		            }
		        }
			} else if (Hypnotic.instance.moduleManager.targetHud.targetHudLook.is("Astolfo")) {
				Color temp = ColorUtil.getClickGUIColor().darker();
				FontRenderer fr = mc.fontRendererObj;
				ScaledResolution sr = new ScaledResolution(mc);		
				EntityLivingBase target = KillAura.target;
				
				int healthBarColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
				
					if(target != null) {
						
						drawEntityOnScreen(sr.getScaledWidth() / 3.2f, sr.getScaledHeight() / 1.69f, 25, target.rotationYaw, target.rotationPitch, target);
						
						Gui.drawRect(sr.getScaledWidth() / 3.4f, sr.getScaledHeight() / 2.05f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 2.3f, sr.getScaledHeight() / 1.6f, new Color(0, 0, 0, 190).getRGB());
								
						Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + 20 * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, -1);
						
						Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + 20 * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, 0x55111111);
							
						Gui.drawRect(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 1.7f - fr.FONT_HEIGHT / 2f, sr.getScaledWidth() / 3f + target.getHealth() * 4, sr.getScaledHeight() / 1.78f - fr.FONT_HEIGHT / 2f, healthBarColor);				
						
						fr.drawString(target.getName(), sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f, -1);
						
						GlStateManager.pushMatrix();
						GlStateManager.translate(sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 6, 0);
						GlStateManager.scale(1.6, 1.6, 1);
						GlStateManager.translate(-(sr.getScaledWidth() / 3f), -(sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 6), 0);
						fr.drawString(MathUtils.round(target.getHealth(), 30) + " \u2764", sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 10, ColorUtils.rainbow(4f, 0.5f, 0.5f));
						GlStateManager.popMatrix();
						
					} else {
						
					}
				
			} else if (Hypnotic.instance.moduleManager.targetHud.targetHudLook.is("Compact")) {
				if (KillAura.target instanceof EntityPlayer || KillAura.target instanceof EntityOtherPlayerMP) {
					EntityLivingBase target = KillAura.target;
					float width = (float) ((scaledResolution.getScaledWidth() / 2) + 100);
	                float height = (float) (scaledResolution.getScaledHeight() / 2);
					RenderUtils.drawRoundedRect(width - 76, height + 29, width + 42.5f, height + 73, 2, new Color(50, 50, 50, 255));
					fontRenderer.drawString(target.getName(), width - 34, height + 35, -1, true);
					fontRenderer.drawString("Health: " + MathUtils.round(target.getHealth(), 2), width - 34, height + 45, -1, true);
					float health = target.getHealth();
	                float healthPercentage = (health / target.getMaxHealth() - 0.48f);
	                float targetHealthPercentage = 0;
	                if (healthPercentage != lastHealth) {
	                    float diff = healthPercentage - this.lastHealth;
	                    targetHealthPercentage = this.lastHealth;
	                    this.lastHealth += diff / 8;
	                }
	                Color healthColor = Color.white;
	                int outlineColor = Hypnotic.instance.moduleManager.arrayMod.colorMode.is("Rainbow") ? ColorUtils.rainbow(6, 0.5f, 0.5f) : ColorUtil.getClickGUIColor().getRGB();
					
	                Gui.drawRect(width - 34, height + 59, width + 36.5f, height + 65, new Color(50, 50, 50, 255).darker().getRGB());
	                Gui.drawRect(width - 34, height + 59, width + 70 * (targetHealthPercentage), height + 65, healthColor.getRGB());
					Gui.drawRect(width - 34, height + 59, width + 70 * (healthPercentage), height + 65, outlineColor);
					if (mc.thePlayer == null || KillAura.target == null || mc.getNetHandler() == null || KillAura.target.getUniqueID() == null || mc.getNetHandler().getPlayerInfo(KillAura.target.getUniqueID()) == null || mc.getNetHandler().getPlayerInfo(KillAura.target.getUniqueID()).getLocationSkin() == null) {
						
					} else {
						Gui.drawRect((int) width - 70 - 1, (int) height + 35 - 1, (int) width - 70 + 33, (int) height + 35 + 33, new Color(outlineColor).getRGB());
						this.drawHead(Objects.requireNonNull(mc.getNetHandler()).getPlayerInfo(KillAura.target.getUniqueID()).getLocationSkin(), width - 70, height + 35);
					}
				}
			}
		}
	}
	
	public void drawHead(ResourceLocation skin, float width, float height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
    }

}
