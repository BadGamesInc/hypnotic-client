package badgamesinc.hypnotic.gui;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.gui.Logo;
import badgamesinc.hypnotic.module.render.TargetHUD;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.TimerUtils;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;

public class HUD {

	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr = mc.fontRendererObj;
	public ScaledResolution sr = new ScaledResolution(mc);
	public static UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 20, 0, 1, 1);
    public static UnicodeFontRenderer ufr2 = UnicodeFontRenderer.getFontFromAssets("Magneto-Bold", 20, 0, 1, 1);
    public static UnicodeFontRenderer ufr3 = UnicodeFontRenderer.getFontFromAssets("Roboto-Light", 15, 0, 1, 1);
    public static UnicodeFontRenderer ufr4 = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 21, 0, 2, 1);
    public static UnicodeFontRenderer ufr5 = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 15, 0, 2, 1);
	private static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	private static GlyphPageFontRenderer fontRenderer2 = GlyphPageFontRenderer.create("Roboto-Light", 13, false, false, false);
	private static GlyphPageFontRenderer fontRenderer3 = GlyphPageFontRenderer.create("Magneto", 18, false, false, false);
	private static GlyphPageFontRenderer fontRenderer4 = GlyphPageFontRenderer.create("Consolas", 50, false, false, false);
	
	public int height;
	public int width;
	
	public TimerUtils timer = new TimerUtils();
	public TimeHelper timeHelper = new TimeHelper();
	
	public int count = 0;
	
	private float lastHealth = 0;
	
	DecimalFormat df = new DecimalFormat("###.#");
	
	public void draw() {

		double serverTPS = Timer.ticksPerSecond;
		
		if (mc.gameSettings.showDebugInfo) {
			return;
		}
		
		renderLogo();
		
		renderTargetHUD();
		renderArrayList(new ScaledResolution(mc));    
        
		if(Hypnotic.instance.moduleManager.getModuleByName("PC Pinger").isEnabled()) 
		{
			fontRenderer4.drawCenteredString("PINGING PC", sr.getScaledWidth(), sr.getScaledHeight() - 150, ColorUtils.rainbow(2, 0.5f, 0.5f), true);
        }
		
		if(Hypnotic.instance.moduleManager.getModuleByName("Info").isEnabled()) 
		{
			ScaledResolution scaled = new ScaledResolution(mc);
			// overworld coords
			ufr3.drawStringWithShadow(df.format(mc.thePlayer.posX) + ", " + df.format(mc.thePlayer.posY) + ", " + df.format(mc.thePlayer.posZ), 2, scaled.getScaledHeight() - 10, -1);
			// nether coords
			ufr3.drawStringWithShadow(df.format(mc.thePlayer.posX/8) + ", " + df.format(mc.thePlayer.posY) + ", " + df.format(mc.thePlayer.posZ/8), 2, scaled.getScaledHeight() - 20, 11141120);
			// fps
			ufr3.drawStringWithShadow("FPS: " + mc.getDebugFPS(), 2, scaled.getScaledHeight() - 30, -1);
			// tps
			ufr3.drawStringWithShadow("TPS: ", 2, scaled.getScaledHeight() - 40, -1);
			if(serverTPS < 5.0) {
				ufr3.drawStringWithShadow(serverTPS + "", 22, scaled.getScaledHeight() - 40, 11141120);
			}
			else if(serverTPS < 10.0) {
				ufr3.drawStringWithShadow(serverTPS + "", 22, scaled.getScaledHeight() - 40, 16733525);
			}
			else if(serverTPS < 15.0) {
				ufr3.drawStringWithShadow(serverTPS + "", 22, scaled.getScaledHeight() - 40, 16777045);
			}
			else if(serverTPS < 20.0) {
				ufr3.drawStringWithShadow(serverTPS + "", 22, scaled.getScaledHeight() - 40, 5635925);
			}
			else {
				ufr3.drawStringWithShadow(serverTPS + "", 22, scaled.getScaledHeight() - 40, 5592575);
			}
			// ping
			if(!mc.isSingleplayer()) {
				ufr3.drawStringWithShadow("Ping: " + getPing(mc.thePlayer), 2, scaled.getScaledHeight() - 50, -1);
			}
			else {
				ufr3.drawStringWithShadow("Ping: 0", 2, scaled.getScaledHeight() - 50, -1);
			}
        }
		
	}
	
	public void renderLogo() {
		if (!Hypnotic.instance.moduleManager.getModule(Logo.class).isEnabled()) {
			return;
		}
		if (Hypnotic.instance.setmgr.getSettingByName("Logo Mode").getValString().equalsIgnoreCase("Text")) {
			fontRenderer3.drawString("H", 2, 4, ColorUtils.rainbow(2, 0.5f, 0.5f), true);
			fontRenderer3.drawString("ypnotic", 9.5, 4, -1, true);
		} else if (Hypnotic.instance.setmgr.getSettingByName("Logo Mode").getValString().equalsIgnoreCase("Image")) {
			mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/purple.png"));
			Gui.drawModalRectWithCustomSizedTexture(4, 4, 80, 20,  80, 20, 80, 20);
		}
	}
	
	public void renderArrayList(ScaledResolution sr) {
		Color temp = ColorUtil.getClickGUIColor().darker();
		String theme = Hypnotic.instance.setmgr.getSettingByName("Style").getValString();
		final int height = sr.getScaledHeight();    
		final int width = sr.getScaledWidth();	
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		int count = 0;
		
		Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> fontRenderer.getStringWidth(((Mod)m).getDisplayName())).reversed());
		
			for(Mod m : Hypnotic.instance.moduleManager.getEnabledModules()) {	
				if(Hypnotic.instance.moduleManager != null) {
					if(!Hypnotic.instance.moduleManager.getModuleByName("Array List").isEnabled()) {
						return;
					}
				}
				String mods = m.getDisplayName();
				Boolean background = Hypnotic.instance.setmgr.getSettingByName("Background").getValBoolean();
				if(Hypnotic.instance.setmgr != null && Hypnotic.instance.setmgr.getSettingByName("Rainbow") != null) {
					if(Hypnotic.instance.setmgr.getSettingByName("Rainbow").getValBoolean()) {
						color = ColorUtils.rainbow(2.0f, 0.5f, 1f, count * 120);
					} else {
						color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
					}
				}
				
				double offset = count * (fontRenderer.getFontHeight() + 1);      
				
				if(!mods.equalsIgnoreCase("Array List") || !mods.equalsIgnoreCase("Logo")) {
					//Chill
					if(theme.equalsIgnoreCase("Chill") && !background) {
						Gui.drawRect(width , offset, width - 3, 1 + fontRenderer.getFontHeight() + offset, color);               
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 8) , (offset), color, false);
					}
					if(theme.equalsIgnoreCase("Chill") && background) {
						Gui.drawRect(width , offset, width - 3, 1 + fontRenderer.getFontHeight() + offset, color);        
						Gui.drawRect(width - fontRenderer.getStringWidth(m.getDisplayName()) - 11 , offset, sr.getScaledWidth() - 3, 1 + fontRenderer.getFontHeight() + offset, new Color(0, 0, 0, 190).getRGB());	        
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 8) , (offset), color, false);
					}
					
					//Accent
					if(theme.equalsIgnoreCase("Accent") && !background) {
						Gui.drawRect(width - fontRenderer.getStringWidth(m.getDisplayName()) - 9 , offset, width - fontRenderer.getStringWidth(m.getDisplayName()) - 10 , 1 + fontRenderer.getFontHeight() + offset, color);                
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 6) , (offset), color, false);
					}
					if(theme.equalsIgnoreCase("Accent") && background) {
						Gui.drawRect(width - fontRenderer.getStringWidth(m.getDisplayName()) - 9 , offset, width - fontRenderer.getStringWidth(m.getDisplayName()) - 10 , 1 + fontRenderer.getFontHeight() + offset, color);        
						Gui.drawRect(width - fontRenderer.getStringWidth(m.getDisplayName()) - 9 , offset, sr.getScaledWidth(), 1 + fontRenderer.getFontHeight() + offset, new Color(0, 0, 0, 190).getRGB());	        
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 6) , (offset), color, false);
					}
					
					//Normal
					if(theme.equalsIgnoreCase("Normal") && !background) {
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 6) , (offset), color, false);
					}
					if(theme.equalsIgnoreCase("Normal") && background) {
						Gui.drawRect(width - fontRenderer.getStringWidth(m.getDisplayName()) - 9 , offset, sr.getScaledWidth(), 1 + fontRenderer.getFontHeight() + offset, new Color(0, 0, 0, 190).getRGB());
						fontRenderer.drawString(m.getDisplayName(), (float)(width - fontRenderer.getStringWidth(m.displayName) - 6) , (offset), color, false);
					}
				count++;
			}
		}
	}
	
	public void renderTargetHUD() {
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		TargetHUD targetHud = new TargetHUD();
		
		
		if (KillAura.target != null && Hypnotic.instance.moduleManager.getModule(KillAura.class).isEnabled()) {		
			if (Hypnotic.instance.setmgr.getSettingByName("TargetHUD Design").getValString().equalsIgnoreCase("New")) {
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
			} else if (Hypnotic.instance.setmgr.getSettingByName("TargetHUD Design").getValString().equalsIgnoreCase("Astolfo")) {
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
						fr.drawString(MathUtils.round(target.getHealth(), 2) + " \u2764", sr.getScaledWidth() / 3f, sr.getScaledHeight() / 2f - fr.FONT_HEIGHT / 2f + 10, ColorUtils.rainbow(2f, 0.5f, 0.5f));
						GlStateManager.popMatrix();
						
					} else {
						
					}
				
			}
		}
	}
	
	public int getPing(EntityPlayer entityPlayer) {
	    if (entityPlayer == null || mc.isSingleplayer())
	      return 0; 
	    NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
	    return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.getResponseTime();
	}

}
