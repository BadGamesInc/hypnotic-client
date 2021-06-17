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
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.gui.ArrayListModule;
import badgamesinc.hypnotic.module.gui.Logo;
import badgamesinc.hypnotic.module.misc.PCPinger;
import badgamesinc.hypnotic.module.render.Sigma;
import badgamesinc.hypnotic.module.render.TargetHUD;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.FontUtil;
import badgamesinc.hypnotic.util.MathUtils;
import badgamesinc.hypnotic.util.MovementUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.TimerUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import badgamesinc.hypnotic.util.font.SigmaFontRenderer;
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
import net.minecraft.network.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.WorldProviderHell;

public class HUD {

	public String logoName;
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr = mc.fontRendererObj;
	public ScaledResolution sr = new ScaledResolution(mc);
	private SigmaFontRenderer fontRenderer = FontManager.roboto;
	private static GlyphPageFontRenderer sigma = GlyphPageFontRenderer.create("dfgdgdg", 22, false, false, false);
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
	
	DecimalFormat df = new DecimalFormat("###.#");
	
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("hh.mm aa");
	
	public void draw() {
		if (mc.gameSettings.showDebugInfo) {
			return;
		}
		
		renderLogo();
		renderInfo();
		renderArrayList(new ScaledResolution(mc));    
        
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
		
		if (!Hypnotic.instance.moduleManager.getModule(Sigma.class).isEnabled()) {
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
		} else {
			GlStateManager.enableBlend();
			mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/sigma/sigma2.png"));
			Gui.drawModalRectWithCustomSizedTexture(10, 10, 0, 0, 169.75 / 2, 75 / 2, 169.75 / 2, 75 / 2);
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
		
		CopyOnWriteArrayList<Mod> modules = new CopyOnWriteArrayList<Mod>(Hypnotic.instance.moduleManager.modules);

        int count = 0;
        if (!Hypnotic.instance.moduleManager.getModule(ArrayListModule.class).isEnabled()) {
        	return;
        }
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
        boolean shouldmove = animationTimer.hasTimeElapsed(1000 / 75, true);
        if (!Hypnotic.instance.moduleManager.getModule(Sigma.class).isEnabled()) {
	        if (Hypnotic.instance.moduleManager.arrayMod.style.getSelected().equalsIgnoreCase("Clean")) {
	        	if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Roboto-Regular")) {
	            	Hypnotic.instance.moduleManager.modules.sort(Comparator.comparingInt(m -> (int) fontRenderer.getStringWidth(((Mod)m).getDisplayName().replace("[", "").replace("]", "").replace(ColorUtils.white, ColorUtils.gray))).reversed());
	            	
	                for (Mod m : modules) {
	                	boolean hasInfo = m.getDisplayName().contains(ColorUtils.white);
	                	int infoOffset = (hasInfo ? 2 : 0);
	                	if (!m.visible.isEnabled()) {
	            			continue;
	            		}
	                	if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
	        				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
	        				color = temp.getRGB();
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
	        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count * 2, 20).getRGB();
	        			}
	                	
	                	
	                	
	                	if (m.animation > 0) {
	    					float offset = ((fontRenderer.getHeight() + 1.5f) * count) - 6;
	    					String name = m.getDisplayName().replace("[", "").replace("]", "").replace(ColorUtils.white, ColorUtils.gray);
	    					double length = fontRenderer.getStringWidth(name);
	    					
	    					if (Hypnotic.instance.moduleManager.arrayMod.background.isEnabled())
	    						Gui.drawRect(sr.getScaledWidth() - (((length) / 100) * m.animation) - 9 - 0, (count * (fontRenderer.getHeight() + 2) - 0), sr.getScaledWidth() - 4 + 100 - m.animation, count * (fontRenderer.getHeight() + 2) + 2 + fontRenderer.getHeight() + 0, new Color(0, 0, 0, 220).getRGB());
	    					fontRenderer.drawStringWithShadow(name, sr.getScaledWidth() - (((length) / 100) * m.animation) - (name.contains(ColorUtils.gray) ? 5 : 7) - infoOffset - 0, count * (fontRenderer.getHeight() + 2) - 1, color);
	    					Gui.drawRect(sr.getScaledWidth() - 4 + 100 - m.animation, (count * (fontRenderer.getHeight() + 2)), sr.getScaledWidth() + 100 - m.animation, count * (fontRenderer.getHeight() + 2) - 0 + fontRenderer.getHeight() + 2, color);
	                        count++;
	    				}
	    				
	    				if (shouldmove) {
	    					if (m.isEnabled()) {
	    						if (m.animation < 100) {
	    							m.animation += 10;
	    						}
	    					}else {
	    						if (m.animation > 0) {
	    							m.animation -= 10;
	    						}
	    					}
	    				}
	    				
	    				
	                }
	                //Gui.drawRect(sr.getScaledWidth() - 3, count * fontRenderer.getFontHeight(), sr.getScaledWidth(), count * fontRenderer.getFontHeight() - 1000, color);
	            } else if (Hypnotic.instance.moduleManager.arrayMod.font.getSelected().equalsIgnoreCase("Minecraft")) {
	            	modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Mod)m).getDisplayName().replace("[", "").replace("]", "").replace(ColorUtils.white, ColorUtils.gray))).reversed());
	            	
	            	for (Mod m : modules) {
	            		if (!m.visible.isEnabled()) {
	            			continue;
	            		}
	            		if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Rainbow")) {
	        				color = ColorUtils.rainbow(4.0f, 0.5f, 1f, count * 120);
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Static")) {
	        				color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
	        			} else if (Hypnotic.instance.moduleManager.arrayMod.colorMode.getSelected().equalsIgnoreCase("Color Wave")) {
	        				color = ColorUtils.fade(new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255), count * 2, 20).getRGB();
	        			}
	            		String name = m.getDisplayName().replace("[", "").replace("]", "").replace(ColorUtils.white, ColorUtils.gray);
	            		if (m.animation > 0) {
	    					float offset = ((fr.FONT_HEIGHT + 1.5f) * count) - 6;
	    					double length = fr.getStringWidth(name);
	    					if (Hypnotic.instance.moduleManager.arrayMod.background.isEnabled())
	    						Gui.drawRect(sr.getScaledWidth() - (((length) / 100) * m.animation) - 13 + 4, (count * (fr.FONT_HEIGHT + 0)), sr.getScaledWidth() + 10 + m.lastSize, count * (fr.FONT_HEIGHT + 0) + fr.FONT_HEIGHT + 0, new Color(0, 0, 0, 220).getRGB());
	    					fr.drawString(name, sr.getScaledWidth() - (((length) / 100) * m.animation) - 10 + 4, count * (fr.FONT_HEIGHT + 0) - 0, color, true);
	    					Gui.drawRect(sr.getScaledWidth() - 4, (count * (fr.FONT_HEIGHT + 0)), sr.getScaledWidth() + 4, count * (fr.FONT_HEIGHT + 0) + fr.FONT_HEIGHT + 0, color);
	                        count++;
	    				}
	    				
	    				if (shouldmove) {
	    					if (m.isEnabled()) {
	    						if (m.animation < 100) {
	    							m.animation += 10;
	    						}
	    					} else {
	    						if (m.animation > 0) {
	    							m.animation -= 10;
	    						}
	    					}
	    				}
	            			
		                    /*float diff = m.mSize - m.lastSize;
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
		                    }*/
	                }
	            	//Gui.drawRect(sr.getScaledWidth() - 3, count * (fr.FONT_HEIGHT + 1), sr.getScaledWidth(), count * fr.FONT_HEIGHT - 1000, color);
	            }
	        }
		} else {
			modules.sort(Comparator.comparingInt(m -> (int)FontManager.bigJello.getStringWidth(((Mod)m).name)).reversed());
			float yStart = 1;
			float xStart = 0;
			int colorFade = 0;
			for (Mod m : modules) {
				if (!m.visible.isEnabled())
					continue;
				
				if (m.animation > 0) {
					xStart = (float) (sr.getScaledWidth() - FontManager.bigJello.getStringWidth(m.name) - 5);
					GlStateManager.enableBlend();
					GlStateManager.disableAlpha();
					GlStateManager.pushMatrix();
					GL11.glColor3f(1, 1, 1);
					GlStateManager.popMatrix();
					mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/sigma/arraylistshadow.png"));
					Gui.drawModalRectWithCustomSizedTexture(xStart - 8 - 2 - 1, yStart + 2 - 2.5f - 1.5f - 1.5f - 1.5f - 6 - 1,
							0, 0, FontManager.bigJello.getStringWidth(m.name) * 1 + 20 + 10, 18.5 + 6 + 12 + 2,
							FontManager.bigJello.getStringWidth(m.name) * 1 + 20 + 10, 18.5 + 6 + 12 + 2);
					FontManager.bigJello.drawString(m.name, xStart + 112 - (((100) / 100) * m.animation) - 13, yStart + 7f, 0xffffffff);
					yStart += 7.5f + 5.25f;
					
				}
				
				if (shouldmove) {
					if (m.isEnabled()) {
						if (m.animation < 100) {
							m.animation += 10;
						}
					} else {
						if (m.animation > 0) {
							m.animation -= 10;
						}
					}
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
