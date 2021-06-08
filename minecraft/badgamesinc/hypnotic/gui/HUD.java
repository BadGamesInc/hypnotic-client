package badgamesinc.hypnotic.gui;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.command.commands.LogoName;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.Killaura;
import badgamesinc.hypnotic.module.gui.ArrayListModule;
import badgamesinc.hypnotic.module.gui.Logo;
import badgamesinc.hypnotic.module.misc.PCPinger;
import badgamesinc.hypnotic.module.render.TargetHUD;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.FontUtil;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.MovementUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.TimerUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.WorldProviderHell;

public class HUD {

	public String logoName;
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr = mc.fontRendererObj;
	public ScaledResolution sr = new ScaledResolution(mc);
	private static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("dfgdgdg", 18, false, false, false);
	private static GlyphPageFontRenderer fontRenderer2 = GlyphPageFontRenderer.create("Comfortaa-Light", 13, false, false, false);
	private static GlyphPageFontRenderer fontRenderer3 = GlyphPageFontRenderer.create("Magneto", 18, false, false, false);
	private static GlyphPageFontRenderer fontRenderer4 = GlyphPageFontRenderer.create("Consolas", 50, false, false, false);
	private static GlyphPageFontRenderer fontRenderer5 = GlyphPageFontRenderer.create("lucon", 15, false, false, false);
	
	public int height;
	public int width;
	
	public static badgamesinc.hypnotic.util.Timer animationTimer = new badgamesinc.hypnotic.util.Timer();
	public TimerUtils timer = new TimerUtils();
	public TimeHelper timeHelper = new TimeHelper();
	
	public int count = 0;
	
	private float lastHealth = 0;
	
	DecimalFormat df = new DecimalFormat("###.#");
	
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("hh.mm aa");
	
	public void draw() {
		if (mc.gameSettings.showDebugInfo) {
			return;
		}
		
		renderLogo();
		renderInfo();
		renderTargetHUD();
		renderArrayList(new ScaledResolution(mc));    
		renderKeyStrokes();
        
		ScaledResolution scale = new ScaledResolution(mc);
		
		if(Hypnotic.instance.moduleManager.getModule(PCPinger.class).isEnabled()) 
		{
			fontRenderer4.drawString("PINGING PC " + (ColorUtils.white + MathUtils.round(Hypnotic.instance.moduleManager.pcpinger.time / 20, 0)), scale.getScaledWidth() / 2 - fontRenderer4.getStringWidth("PINGING PC") / 2, scale.getScaledHeight() / 10, ColorUtils.rainbow(4, 0.5f, 0.5f), true);
        }
		
		
		
	}
	
	public void renderLogo() {
		if (!Hypnotic.instance.moduleManager.getModule(Logo.class).isEnabled()) {
			return;
		}
		
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		
		switch(Hypnotic.instance.moduleManager.logo.colorMode.getSelected()) {
			case "Rainbow":
				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
				break;
			case "Static":
				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
				break;
			case "ColorWave":
				color = ColorUtils.fade(temp, 1, 2).getRGB();
		}
		
		if (Hypnotic.instance.moduleManager.logo.mode.getSelected().equalsIgnoreCase("Text")) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1, 1, 1);
			switch(Hypnotic.instance.moduleManager.logo.textType.getSelected()) {
				case "Single Letter Color":
					if (LogoName.name == "")
						logoName = Character.toString(Hypnotic.clientName.charAt(0)) + ColorUtils.white + Hypnotic.clientName.substring(1);
					else
						logoName = LogoName.name;
					switch(Hypnotic.instance.moduleManager.logo.font.getSelected()) {
					
					case "Roboto-Regular":
							fontRenderer3.drawString(logoName + "-" + Hypnotic.clientVersion + ColorUtils.reset, 4, 4, color, true);
							break;
					case "Minecraft":
							fr.drawString(logoName + ColorUtils.white + "-" + Hypnotic.clientVersion + ColorUtils.reset, 4, 4, color, true);
							break;
					}
					break;
				case "Full Text Color":
					if (LogoName.name == "")
						logoName = Hypnotic.clientName;
					else
						logoName = LogoName.name;
					switch(Hypnotic.instance.moduleManager.logo.font.getSelected()) {
					
					case "Roboto-Regular":
						if (Hypnotic.instance.moduleManager.logo.colorMode.is("ColorWave")) {
							//When the code is sus
							fontRenderer3.drawString("H", 4, 4, ColorUtils.fade(temp, 4, 4).getRGB(), true);
							fontRenderer3.drawString("y", 11, 4, ColorUtils.fade(temp, 4, 5).getRGB(), true);
							fontRenderer3.drawString("p", 16, 4, ColorUtils.fade(temp, 4, 6).getRGB(), true);
							fontRenderer3.drawString("n", 21, 4, ColorUtils.fade(temp, 4, 7).getRGB(), true);
							fontRenderer3.drawString("o", 26, 4, ColorUtils.fade(temp, 4, 8).getRGB(), true);
							fontRenderer3.drawString("t", 31, 4, ColorUtils.fade(temp, 4, 9).getRGB(), true);
							fontRenderer3.drawString("i", 33.5, 4, ColorUtils.fade(temp, 4, 10).getRGB(), true);
							fontRenderer3.drawString("c", 35.5, 4, ColorUtils.fade(temp, 4, 1).getRGB(), true);
							fontRenderer3.drawString("-", 42, 4, ColorUtils.fade(temp, 4, 2).getRGB(), true);
							fontRenderer3.drawString("r", 46, 4, ColorUtils.fade(temp, 4, 3).getRGB(), true);
							fontRenderer3.drawString("1", 49, 4, ColorUtils.fade(temp, 4, 4).getRGB(), true);
							fontRenderer3.drawString("0", 53.5, 4, ColorUtils.fade(temp, 4, 5).getRGB(), true);
							fontRenderer3.drawString("0", 59, 4, ColorUtils.fade(temp, 4, 6).getRGB(), true);
							fontRenderer3.drawString("7", 64, 4, ColorUtils.fade(temp, 4, 7).getRGB(), true);
						} else {
							fontRenderer3.drawString(logoName + "-" + Hypnotic.clientVersion + ColorUtils.reset, 4, 4, color, true);
						}
							break;
					case "Minecraft":
							fr.drawString(logoName + ColorUtils.white + "-" + Hypnotic.clientVersion + ColorUtils.reset, 4, 4, color, true);
							break;
					}
					break;
			}
			GlStateManager.popMatrix();
		} else if (Hypnotic.instance.moduleManager.logo.mode.getSelected().equalsIgnoreCase("Image")) {
			mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/white.png"));
			Gui.drawModalRectWithCustomSizedTexture(4, 4, 60, 15, 60, 15, 60, 15);
		}
	}
	
	public void renderInfo() {
		double serverTPS = Timer.ticksPerSecond;
		if(Hypnotic.instance.moduleManager.getModuleByName("Info").isEnabled()) 
		{
			ScaledResolution scaled = new ScaledResolution(mc);
			boolean isChatOpen = mc.currentScreen instanceof GuiChat;
			if (isChatOpen) {
				return;
			}
			boolean inNether = mc.thePlayer.worldObj.provider instanceof WorldProviderHell ? true : false;
			String overworld = ColorUtils.white + "Coords " + ColorUtils.green + df.format(mc.thePlayer.posX) + ", " + df.format(mc.thePlayer.posY) + ", " + df.format(mc.thePlayer.posZ);
			String nether = ColorUtils.white + "Coords " + ColorUtils.reset + df.format(mc.thePlayer.posX/8) + ", " + df.format(mc.thePlayer.posY) + ", " + df.format(mc.thePlayer.posZ/8);
			double motion = (float)(MathUtils.square(this.mc.thePlayer.motionX) + MathUtils.square(this.mc.thePlayer.motionZ));
			double bps = MathUtils.round(MovementUtils.getBlocksPerSecond(), 2);
			fontRenderer5.drawString(inNether ? nether : overworld, 62, scaled.getScaledHeight() - 12, inNether ? 11141120 : -1, true);
			fontRenderer5.drawString("FPS: " + ColorUtils.green + mc.getDebugFPS(), 2, scaled.getScaledHeight() - 22, -1, true);
			fontRenderer5.drawString("TPS: ", 40, scaled.getScaledHeight() - 22, -1, true);
			if(serverTPS < 5.0) {
				fontRenderer5.drawString(serverTPS + "", 62, scaled.getScaledHeight() - 22, 11141120, true);
			} else if(serverTPS < 10.0) {
				fontRenderer5.drawString(serverTPS + "", 62, scaled.getScaledHeight() - 22, 16733525, true);
			} else if(serverTPS < 15.0) {
				fontRenderer5.drawString(serverTPS + "", 62, scaled.getScaledHeight() - 22, 16777045, true);
			} else if(serverTPS < 20.0) {
				fontRenderer5.drawString(serverTPS + "", 62, scaled.getScaledHeight() - 22, 5635925, true);
			} else {
				fontRenderer5.drawString(serverTPS + "", 62, scaled.getScaledHeight() - 22, 5592575, true);
			}
			if(!mc.isSingleplayer()) {
				fontRenderer5.drawString("Ping: " + ColorUtils.green + getPing(mc.thePlayer), 82, scaled.getScaledHeight() - 22, -1, true);
			} else {
				fontRenderer5.drawString("Ping: " + ColorUtils.green + "0", 82, scaled.getScaledHeight() - 22, -1, true);
			}
			fontRenderer5.drawString("BPS: " + ColorUtils.green + bps, 122, scaled.getScaledHeight() - 22, -1, true);
			fontRenderer5.drawString("Time: " + ColorUtils.green + format.format(date).toLowerCase().replace('.', ':'), 2, scaled.getScaledHeight() - 12  , -1, true);
        }
	}
	
	public void renderArrayList(ScaledResolution sr) {
		Color temp = ColorUtil.getClickGUIColor();
		final int height = sr.getScaledHeight();    
		final int width = sr.getScaledWidth();	
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		
		
		
		CopyOnWriteArrayList<Mod> modules = Hypnotic.instance.moduleManager.getEnabledModules();

        int count = 0;
        if (!Hypnotic.instance.moduleManager.getModule(ArrayListModule.class).isEnabled()) {
        	return;
        }
        boolean showRenderMods = Hypnotic.instance.moduleManager.arrayMod.showRenderMods.isEnabled();
        /*if (Hypnotic.instance.moduleManager.arrayMod.style.getSelected().equalsIgnoreCase("Boxed")) {
	        if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
	        	Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> fontRenderer.getStringWidth(((Mod)m).getDisplayName())).reversed());
	            for (Mod m : modules) {
	            	if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
	        				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
	        				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
	        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count / 2, 4).getRGB();
	        			}
	                float diff = m.mSize - m.lastSize;
	                m.lastSize += diff / 4;
	                if (m.lastSize != m.fontRenderer.getStringWidth(m.getDisplayName()) || m.isEnabled()) {
	                    GlStateManager.enableBlend();
	                    Mod m2 = modules.get(modules.indexOf(m) + 1 < modules.size() ? modules.indexOf(m) + 1 : modules.indexOf(m));
	                    Gui.drawRect(sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 11 + m.lastSize, (count * (fontRenderer.getFontHeight() + 0)), sr.getScaledWidth() + 10 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 0, new Color(0, 0, 0, 220).getRGB());
	                    Gui.drawRect(sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 11 + m.lastSize, (count * (fontRenderer.getFontHeight() + 0)), sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 10 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 0, color);
	                    Gui.drawRect(sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 11 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 0, modules.indexOf(m2) == modules.indexOf(m) ? sr.getScaledWidth() : sr.getScaledWidth() - fontRenderer.getStringWidth(m2.getDisplayName()) - 11 + 1, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 1, color);
	                    fontRenderer.drawString(m.getDisplayName(), sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 7 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) - 0, color, true);
	                    count++;
	
	
	                }
	            }
	        } else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
	        	Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Mod)m).getDisplayName())).reversed());
	        	for (Mod m : modules) {
	        		if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
	        				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
	        				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
	        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count / 2, 4).getRGB();
	        			}
	                float diff = m.mSize - m.lastSize;
	                m.lastSize += diff / 4;
	                if (m.lastSize != m.fr.getStringWidth(m.getDisplayName()) || m.isEnabled()) {
	                	boolean hasInfo = m.getDisplayName().contains(ColorUtils.white);
	                	int infoOffset = (hasInfo ? 2 : 0);
	                    GlStateManager.enableBlend();
	                    Mod m2 = modules.get(modules.indexOf(m) + 1 < modules.size() ? modules.indexOf(m) + 1 : modules.indexOf(m));
	                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 6 + m.lastSize + infoOffset, (count * (fr.FONT_HEIGHT + 1)), sr.getScaledWidth() + 10 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 1, new Color(0, 0, 0, 220).getRGB());
	                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 6 + m.lastSize + infoOffset, (count * (fr.FONT_HEIGHT + 1)), sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 7 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 1, color);
	                    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 7 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 0, modules.indexOf(m2) == modules.indexOf(m) ? sr.getScaledWidth() - infoOffset : sr.getScaledWidth() - fr.getStringWidth(m2.getDisplayName()) - 6 + 0 - infoOffset + (infoOffset > 1 ? 4 : 0), count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 1, color);
	                    fr.drawString(m.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 3 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + 0.5, color);
	                    count++;
	
	
	                }
	
	            }
	        }
        } else*/ 
        if (Hypnotic.instance.moduleManager.arrayMod.style.getSelected().equalsIgnoreCase("Clean")) {
        	if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
            	Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> fontRenderer.getStringWidth(((Mod)m).getDisplayName())).reversed());
            	
                for (Mod m : modules) {
                	if (!m.visible.isEnabled()) {
            			continue;
            		}
	                	if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
	            				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
	            			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
	            				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
	            			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
		        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count / 1, 20).getRGB();
		        			}
	                    float diff = m.mSize - m.lastSize;
	                    m.lastSize += diff / 4;
	                    if (m.lastSize != m.fontRenderer.getStringWidth(m.getDisplayName()) || m.isEnabled()) {
	                        GlStateManager.enableBlend();
	                        Mod m2 = modules.get(modules.indexOf(m) + 1 < modules.size() ? modules.indexOf(m) + 1 : modules.indexOf(m));
	                        Gui.drawRect(sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 9 + m.lastSize, (count * (fontRenderer.getFontHeight() + 0)), sr.getScaledWidth() + 10 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 0, new Color(0, 0, 0, 220).getRGB());
	                        Gui.drawRect(sr.getScaledWidth() - 3 + m.lastSize, (count * (fontRenderer.getFontHeight() + 0)), sr.getScaledWidth() + m.lastSize, count * (fontRenderer.getFontHeight() + 0) + fontRenderer.getFontHeight() + 0, color);
	                        fontRenderer.drawString(m.getDisplayName(), sr.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 7 + m.lastSize, count * (fontRenderer.getFontHeight() + 0) - 0, color, true);
	                        count++;
	
	
	                    }
                }
                //Gui.drawRect(sr.getScaledWidth() - 3, count * fontRenderer.getFontHeight(), sr.getScaledWidth(), count * fontRenderer.getFontHeight() - 1000, color);
            } else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
            	Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Mod)m).getDisplayName())).reversed());
            	
            	for (Mod m : modules) {
            		if (!m.visible.isEnabled()) {
            			continue;
            		}
            			if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
            				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
            			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
            				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
            			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
	        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count / 2, 20).getRGB();
	        			}
	                    float diff = m.mSize - m.lastSize;
	                    m.lastSize += diff / 4;
	                    if (m.lastSize != m.fr.getStringWidth(m.getDisplayName()) || m.isEnabled()) {
	                    	boolean hasInfo = m.getDisplayName().contains(ColorUtils.white);
	                    	int infoOffset = (hasInfo ? 2 : 0);
	                        GlStateManager.enableBlend();
	                        Mod m2 = modules.get(modules.indexOf(m) + 1 < modules.size() ? modules.indexOf(m) + 1 : modules.indexOf(m));
	                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 7 + m.lastSize + infoOffset, (count * (fr.FONT_HEIGHT + 1)), sr.getScaledWidth() + 10 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 1, new Color(0, 0, 0, 220).getRGB());
	                        Gui.drawRect(sr.getScaledWidth() - 3, (count * (fr.FONT_HEIGHT + 1)), sr.getScaledWidth() + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + fr.FONT_HEIGHT + 1, color);
	                        fr.drawString(m.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 4 + m.lastSize + infoOffset, count * (fr.FONT_HEIGHT + 1) + 0.5, color);
	                        count++;
	                    }
                }
            	//Gui.drawRect(sr.getScaledWidth() - 3, count * (fr.FONT_HEIGHT + 1), sr.getScaledWidth(), count * fr.FONT_HEIGHT - 1000, color);
            }
        }
	}
	
	public void renderTargetHUD() {
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		TargetHUD targetHud = new TargetHUD();
		
		
		if (Killaura.target != null && Hypnotic.instance.moduleManager.getModule(Killaura.class).isEnabled()) {		
			if (Hypnotic.instance.moduleManager.targetHud.targetHudLook.is("New")) {
				if (Hypnotic.instance.moduleManager.getModule(TargetHUD.class).isEnabled()) {
		            if (Killaura.target instanceof EntityPlayer || Killaura.target instanceof EntityOtherPlayerMP) {
		                float width = (float) ((scaledResolution.getScaledWidth() / 2) + 100);
		                float height = (float) (scaledResolution.getScaledHeight() / 2);
		
		                EntityPlayer player = (EntityPlayer) Killaura.target;
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
				EntityLivingBase target = Killaura.target;
				
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
				if (Killaura.target instanceof EntityPlayer || Killaura.target instanceof EntityOtherPlayerMP) {
					EntityLivingBase target = Killaura.target;
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
	                Gui.drawRect(width - 34, height + 59, width + 36.5f, height + 65, new Color(50, 50, 50, 255).darker().getRGB());
	                Gui.drawRect(width - 34, height + 59, width + 70 * (targetHealthPercentage), height + 65, healthColor.getRGB());
					Gui.drawRect(width - 34, height + 59, width + 70 * (healthPercentage), height + 65, 0xff00B911);
					int outlineColor = Hypnotic.instance.moduleManager.arrayMod.colorMode.is("Rainbow") ? ColorUtils.rainbow(6, 0.5f, 0.5f) : ColorUtil.getClickGUIColor().getRGB();
					if (mc.thePlayer == null || Killaura.target == null || mc.getNetHandler() == null || Killaura.target.getUniqueID() == null || mc.getNetHandler().getPlayerInfo(Killaura.target.getUniqueID()) == null || mc.getNetHandler().getPlayerInfo(Killaura.target.getUniqueID()).getLocationSkin() == null) {
						
					} else {
						Gui.drawRect((int) width - 70 - 1, (int) height + 35 - 1, (int) width - 70 + 33, (int) height + 35 + 33, new Color(outlineColor).getRGB());
						this.drawHead(Objects.requireNonNull(mc.getNetHandler()).getPlayerInfo(Killaura.target.getUniqueID()).getLocationSkin(), width - 70, height + 35);
					}
				}
			}
		}
	}
	
	int lastA = 0;
    int lastW = 0;
    int lastS = 0;
    int lastD = 0;
    int lastSpace = 0;
    int lastLMB = 0;
    int lastRMB = 0;
    
	public void renderKeyStrokes() {
		if (!Hypnotic.instance.moduleManager.keystrokes.isEnabled()) {
			return;
		}
		
		double size = Hypnotic.instance.moduleManager.keystrokes.size.getValue();
		
		boolean A = mc.gameSettings.keyBindLeft.pressed;
        boolean W = mc.gameSettings.keyBindForward.pressed;
        boolean S = mc.gameSettings.keyBindBack.pressed;
        boolean D = mc.gameSettings.keyBindRight.pressed;
        boolean space = mc.gameSettings.keyBindJump.pressed;
        boolean lmb = mc.gameSettings.keyBindAttack.pressed;
        boolean rmb = mc.gameSettings.keyBindUseItem.pressed;
        int alphaA = A ? 255 : 0;
        int alphaW = W ? 255 : 0;
        int alphaS = S ? 255 : 0;
        int alphaD = D ? 255 : 0;
        int alphaSpace = space ? 255 : 0;
        int alphaLMB = lmb ? 255 : 0;
        int alphaRMB = rmb ? 255 : 0;

        if(lastA != alphaA){
            float diff = alphaA - lastA;
            lastA += diff / 15;
        }
        if(lastW != alphaW){
            float diff = alphaW - lastW;
            lastW += diff / 15;
        }
        if(lastS != alphaS){
            float diff = alphaS - lastS;
            lastS += diff / 15;
        }
        if(lastD != alphaD){
            float diff = alphaD - lastD;
            lastD += diff / 15;
        }
        if(lastSpace != alphaSpace){
            float diff = alphaSpace - lastSpace;
            lastSpace += diff / 15;
        }
        if(lastLMB != alphaLMB){
            float diff = alphaLMB - lastLMB;
            lastLMB += diff / 15;
        }
        if(lastRMB != alphaRMB){
            float diff = alphaRMB - lastRMB;
            lastRMB += diff / 15;
        }
        
        GlStateManager.pushMatrix();
        GlStateManager.scale(size * 0.5, size * 0.5, size * 0.5);

        Gui.drawRect(5 + 20, 29, 27 + 22, 28, -1);
        Gui.drawRect(5 + 20, 29, 26, 49, -1);
        Gui.drawRect(5 + 20 + 23, 28, 27 + 22.2, 49, -1);
        Gui.drawRect(5, 50 - 1, 26, 50 - 2, -1);
        Gui.drawRect(4, 27 + 20 + 62, 5, 50 - 2, -1);
        Gui.drawRect(5 + 22 + 21, 50 - 1, 27 + 20 + 22, 50 - 2, -1);
        Gui.drawRect(27 + 20 + 22 + 1, 27 + 20 + 62, 27 + 20 + 22, 50 - 2, -1);
        Gui.drawRect(4, 27 + 20 + 62, 27 + 20 + 23, 27 + 20 + 62 + 1, -1);
        
        Gui.drawRect(5, 50 - 1, 26, 70 - 1, new Color(lastA, lastA, lastA, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("A", 14, 53,new Color(flop(lastA, 255), flop(lastA, 255), flop(lastA, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 21, 29, 27 + 21, 49, new Color(lastW, lastW, lastW, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("W", 34, 33, new Color(flop(lastW, 255), flop(lastW, 255), flop(lastW, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 21, 25 + 24, 27 + 21, 45 + 24, new Color(lastS, lastS, lastS, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("S", 36, 53, new Color(flop(lastS, 255), flop(lastS, 255), flop(lastS, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 22 + 21, 25 + 24, 27 + 20 + 22, 45 + 24, new Color(lastD, lastD, lastD, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("D", 36 + 22, 53, new Color(flop(lastD, 255), flop(lastD, 255), flop(lastD, 255), 255).brighter().getRGB(), false);
        
        Gui.drawRect(5, 25 + 24 + 24 + 16, 27 + 20 + 22, 45 + 24 + 24 + 16, new Color(lastSpace, lastSpace, lastSpace, 255).brighter().getRGB());
        Gui.drawRect(20, 34 + 16 + 24 + 24, 27 + 5 + 22, 45 + 24 + 16 + 15, new Color(flop(lastSpace, 255), flop(lastSpace, 255), flop(lastSpace, 255), 255).brighter().getRGB());
        
        Gui.drawRect(5, 25 + 20 + 24, 27 + 21 - 12, 45 + 20 + 24, new Color(lastLMB, lastLMB, lastLMB, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("LMB", 19, 72, new Color(flop(lastLMB, 255), flop(lastLMB, 255), flop(lastLMB, 255), 255).brighter().getRGB(), false);
        
        Gui.drawRect(3 + 33, 25 + 20 + 24, 27 + 20 + 22, 45 + 20 + 24, new Color(lastRMB, lastRMB, lastRMB, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("RMB", 49, 72, new Color(flop(lastRMB, 255), flop(lastRMB, 255), flop(lastRMB, 255), 255).brighter().getRGB(), false);
       // GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.popMatrix();
	}
	
	public void drawHead(ResourceLocation skin, float width, float height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
    }
	
	public int flop(int a, int b){
        return b - a;
    }
	
	public int getPing(EntityPlayer entityPlayer) {
	    if (entityPlayer == null || mc.isSingleplayer())
	      return 0; 
	    NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
	    return (networkPlayerInfo == null) ? 0 : networkPlayerInfo.getResponseTime();
	}

}
