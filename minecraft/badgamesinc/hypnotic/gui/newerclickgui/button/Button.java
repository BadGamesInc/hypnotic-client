package badgamesinc.hypnotic.gui.newerclickgui.button;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.button.settings.Keybind;
import badgamesinc.hypnotic.gui.newerclickgui.button.settings.SettingsWindow;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Button {
    Minecraft mc = Minecraft.getMinecraft();
    Category category;
    int x, y;
    public Mod mod;
    public int start = 0;
    public static boolean open = false;
    int slide;
    int lastSlide = 0;
    public static SettingsWindow settingsWindow = null;
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);

    public Button(Category category, int x, int y, Mod mod) {
        this.category = category;
        this.x = x;
        this.y = y;
        start = y;
        this.mod = mod;
    }
    int mouseTicks = 0;
    int last = 500;

    public Minecraft getMc() {
        return mc;
    }

    public void setMc(Minecraft mc) {
        this.mc = mc;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getMouseTicks() {
        return mouseTicks;
    }

    public void setMouseTicks(int mouseTicks) {
        this.mouseTicks = mouseTicks;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public void draw(int mouseX, int mouseY){
        if(isWithinButton(mouseX, mouseY)){
            if(mouseTicks < 2){
                mouseTicks++;
            }
        }else {
            if(mouseTicks > 0){
                mouseTicks--;
            }
        }
        mc.getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/clickgui/modulebackdrop.png"));

        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        int guiScaleThing = 0;
        if (mc.gameSettings.guiScale > 2) {
        	guiScaleThing = 210;
        }
        Gui.drawModalRectWithCustomSizedTexture(x - mouseTicks, y - mouseTicks, 0, 0, 550 + mouseTicks, 35 + mouseTicks, 550 + mouseTicks - guiScaleThing    , 35 + mouseTicks);

        GlStateManager.color(1.0f, 1.0f, 1.0f ,1.0f);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        int target = mod.isEnabled() ? 518 : 500;
        float diff  = target - last;
        last += diff / 4;
        int guiScaleThing2 = 500;
        if (mc.gameSettings.guiScale > 2) {
        	guiScaleThing2 = 310;
        }
        for(int i = 0; i < 15; i++){
            RenderUtils.drawFilledCircle(x + guiScaleThing2 +  i, y + 17, 4, mod.isEnabled() ? ColorUtils.rainbow(6, 0.5f, 0.5f) : new Color(150, 150, 150, 255).getRGB());
        }
        int guiScaleThing3 = 0;
        if (mc.gameSettings.guiScale > 2) {
        	guiScaleThing3 = 190;
        }
        RenderUtils.drawFilledCircle(x + last - guiScaleThing3, y + 17, 6, -1);
    }

    public boolean isWithinButton(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 550 && mouseY > y && mouseY < y + 35;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(isWithinButton(mouseX, mouseY)){
            if(mouseButton == 0){
                if(!open) {
                    mod.toggle();
                }
            }else {
            	if (Hypnotic.instance.setmgr.getSettingByName("Sound").getValBoolean()) {
            		mc.thePlayer.playSound("random.click", 0.5f, 0.5f);
            	}
                settingsWindow = new SettingsWindow(this);
            }
        }
    }
    
    public void keyTyped(char typedChar, int keyCode) {
    	if (Keybind.listening) {
    		if (keyCode != Keyboard.KEY_ESCAPE)
    			mod.setKey(keyCode);
    		 else
    			mod.setKey(Keyboard.KEY_NONE);
    		
    		Keybind.listening = false;
    	}
    }

    public void drawString(){
        fontRenderer.drawString(mod.getName(), x + 20, y + 35 / 2 - fontRenderer.getFontHeight() / 2 - 2, mod.isEnabled() ? ColorUtils.rainbow(6, 0.5f, 0.5f) : -1, true);
        fontRenderer.drawString("+", x + 528, y + 35 / 2 - fontRenderer.getFontHeight() / 2 - 2, mod.isEnabled() ? ColorUtils.rainbow(6, 0.5f, 0.5f) : -1, true);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Mod getMod() {
        return mod;
    }

    public void setMod(Mod mod) {
        this.mod = mod;
    }
}
