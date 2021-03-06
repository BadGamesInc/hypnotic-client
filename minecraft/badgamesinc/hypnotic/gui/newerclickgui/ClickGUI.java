package badgamesinc.hypnotic.gui.newerclickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.button.Button;
import badgamesinc.hypnotic.gui.newerclickgui.button.CategoryButton;
import badgamesinc.hypnotic.gui.newerclickgui.button.ConfigsButton;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGUI extends GuiScreen {
    public Category currentCategory;
    ArrayList<CategoryButton> buttons = new ArrayList<>();
    ArrayList<Button> mods = new ArrayList<>();
    ConfigsButton cfgButton = new ConfigsButton();
    public static int color = ColorUtils.rainbow(6, 0.5f, 0.5f);
    public static GlyphPageFontRenderer bigFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 36, false, false, false);
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
    int offset = 0;
    int lastOffset = 0;
    double divisor = -7;
    double fadeIn = 0;
    public TimerUtils timer = new TimerUtils();
    
    public ClickGUI(){}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }

        if(lastOffset != offset){
            int diff = offset - lastOffset;
            lastOffset += diff / 4;
        }
        int left = width / 6;
        int top = height / 7;
        int right = width - left;
        int bottom = height - top;
        GlStateManager.pushMatrix();
        if (Button.settingsWindow == null) {
	        this.prepareScissorBox(left, top, right, bottom);
	        GL11.glEnable(3089);
	        boolean uglyAnimation = Hypnotic.instance.moduleManager.clickGui.animation.getSelected().equalsIgnoreCase("Fade in (ugly)");
	        RenderUtils.drawRoundedRect(left, top, right, bottom, 8, new Color(48, 48, 48, uglyAnimation ? (int) fadeIn : 255));
	        RenderUtils.drawRoundedRect(left, top, left + 80, bottom, 8, new Color(60, 60, 60, uglyAnimation ? (int) fadeIn : 255));
	        for(CategoryButton button : buttons){
	            button.draw(mouseX, mouseY);
	        }
	        //cfgButton.drawButton(width / 6 + 4, height - 100, mouseX, mouseY);
	        bigFontRenderer.drawString(Hypnotic.clientName, width / 6 + 4, height / 7 - 5 + 15, color, true);
	        fontRenderer.drawString(Hypnotic.clientVersion, width / 6 + 44, height / 7 - 5 + 35, color, true);
	        if(currentCategory != null){
		
	            for(Button b : mods){
	                b.setY(b.start - lastOffset);
	                b.draw(mouseX, mouseY);
	            }
	            for(Button b : mods){
	                b.setY(b.start - lastOffset);
	                b.drawString();
	            }
		
		    }
        }
        if(!Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled()) 
        {
        	this.color = ColorUtil.getClickGUIColor().getRGB();
        }
        else 
        {
        	this.color = ColorUtils.rainbow(6, 0.5f, 0.5f);
        }
        for(Button b : mods){
            if(b.settingsWindow != null){
                b.settingsWindow.draw(mouseX, mouseY);
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }



    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int left = width / 6;
        int top = height / 7;
        for(Button b : mods){
            if(b.settingsWindow != null){
                b.settingsWindow.mouseClicked(mouseX, mouseY);
                return;
            }
        }
        for(CategoryButton button : buttons){
            button.mouseClicked(mouseX, mouseY);
            if (button.isHovered(mouseX, mouseY)){
                mods.clear();
                int count = 0;
                for(Mod m : Hypnotic.instance.moduleManager.getModulesInCategory(currentCategory)){
                    Button b = new Button(currentCategory, left + 86, top + 5 + count * 30, m);
                    b.setStart(top + 5 + count * 30);
                    this.mods.add(b);

                    count++;
                }
                offset = 0;

            }
        }

        for(Button b : mods){

            b.mouseClicked(mouseX, mouseY, mouseButton);
        }
        
        //cfgButton.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for(Button b : mods){
            if(b.settingsWindow != null){
                b.settingsWindow.mouseReleased();
            }
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
    	
    	
        int left = width / 6;
        int top = height / 7;
        int count = 1;
        int countMultiplier = 35;
        
        if (mc.gameSettings.guiScale > 2) {
        	countMultiplier = 22;
        }
        for (Category category : Category.values()) {
            buttons.add(new CategoryButton(left + 15, top + 10 + countMultiplier * count, category, this));
            count++;
        }
        super.initGui();
    }

    public void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(this.mc);
        final int factor = scale.getScaleFactor();
        boolean uglyAnimation = Hypnotic.instance.moduleManager.clickGui.animation.getSelected().equalsIgnoreCase("Fade in (ugly)");
        boolean slideAnimation = Hypnotic.instance.moduleManager.clickGui.animation.getSelected().equalsIgnoreCase("Slide");
        if (uglyAnimation) {
        	if (fadeIn < 255)
		        fadeIn += 5;
        } else if (slideAnimation) {
        	if (timer.hasTimeElapsed(25L, false)) {
	        	if (mc.gameSettings.guiScale <= 2) {
		        	if (divisor < 1.9)
		        	divisor += 0.2;
	        	} else {
	        		if (divisor < 3)
	    	        	divisor += 0.2;
	        	}
	        if (mc.gameSettings.guiScale <= 2) {
		        if (divisor < 2)
		        	divisor += 0.1;
	        } else {
	        	if (divisor < 3)
	    	        divisor += 0.5;
	        }
        }
        GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * divisor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Button b : mods){
            if (b.settingsWindow != null) {
            	b.keyTyped(typedChar, keyCode);
                if (keyCode == 1) {
                    b.settingsWindow = null;
                }
                return;
            }
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    public void onGuiClosed() {
    	Hypnotic.instance.saveload.save();
    	Hypnotic.cfgManager.saveConfig();
    	for (Button b : mods) {
    		b.settingsWindow = null;
    	}
    	super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
}
