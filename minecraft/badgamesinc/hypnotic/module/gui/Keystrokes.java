package badgamesinc.hypnotic.module.gui;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event2D;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;

public class Keystrokes extends Mod {

	public NumberSetting size = new NumberSetting("Scale", 2, 1, 2, 0.05);
	
	int lastA = 0;
    int lastW = 0;
    int lastS = 0;
    int lastD = 0;
    int lastSpace = 0;
    int lastLMB = 0;
    int lastRMB = 0;
    public boolean dragging = false;
    
    public int x, y, width, height, left, bottom, width1, height1, left1, bottom1;
    
	public Keystrokes() {
		super("Keystrokes", 0, Category.GUI, "Renders live keystrokes");
		this.setEnabled(true);
		addSettings(size);
	}
	
	public void onUpdate() {
		width = 27 + 20 + 22 + 1 + getX();
        height = 29 - 1 + getY();
        left = 5 + getX() - 1;
        bottom = 45 + 24 + 24 + 16 + 1 + getY();
        width1 = 27 + 20 + 22 + 1;
        height1 = 29 - 1;
        left1 = 5 - 1;
        bottom1 = 45 + 24 + 24 + 16 + 1;
	}
	
	@EventTarget
	public void event2d(Event2D e) {
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
        
        if (mc.currentScreen instanceof GuiChat) {
        	Gui.drawRect(left, bottom + 1, left - 1, height - 1, ColorUtil.getClickGUIColor().getRGB());
        	Gui.drawRect(width, bottom + 1, width + 1, height - 1, ColorUtil.getClickGUIColor().getRGB());
        	Gui.drawRect(left, bottom, width, bottom + 1, ColorUtil.getClickGUIColor().getRGB());
        	Gui.drawRect(left, height, width, height - 1, ColorUtil.getClickGUIColor().getRGB());
        }
        
        Gui.drawRect(5 + 20 + x, 29 + getY(), 27 + 22 + getX(), 28 + getY(), -1);
        Gui.drawRect(5 + 20 + getX(), 29 + getY(), 26 + getX(), 49 + getY(), -1);
        Gui.drawRect(5 + 20 + 23 + getX(), 28 + getY(), 27 + 22.2 + getX(), 49 + getY(), -1);
        Gui.drawRect(5 + getX(), 50 - 1 + getY(), 26 + getX(), 50 - 2 + getY(), -1);
        Gui.drawRect(4 + getX(), 27 + 20 + 62 + getY(), 5 + getX(), 50 - 2 + getY(), -1);
        Gui.drawRect(5 + 22 + 21 + getX(), 50 - 1 + getY(), 27 + 20 + 22 + getX(), 50 - 2 + getY(), -1);
        Gui.drawRect(27 + 20 + 22 + 1 + getX(), 27 + 20 + 62 + getY(), 27 + 20 + 22 + getX(), 50 - 2 + getY(), -1);
        Gui.drawRect(4 + getX(), 27 + 20 + 62 + getY(), 27 + 20 + 23 + getX(), 27 + 20 + 62 + 1 + getY(), -1);
        
        Gui.drawRect(5 + getX(), 50 - 1 + getY(), 26 + getX(), 70 - 1 + getY(), new Color(lastA, lastA, lastA, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("A", 14 + getX(), 53 + getY(),new Color(flop(lastA, 255), flop(lastA, 255), flop(lastA, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 21 + getX(), 29 + getY(), 27 + 21 + getX(), 49 + getY(), new Color(lastW, lastW, lastW, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("W", 34 + getX(), 33 + getY(), new Color(flop(lastW, 255), flop(lastW, 255), flop(lastW, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 21 + getX(), 25 + 24 + getY(), 27 + 21 + getX(), 45 + 24 + getY(), new Color(lastS, lastS, lastS, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("S", 36 + getX(), 53 + getY(), new Color(flop(lastS, 255), flop(lastS, 255), flop(lastS, 255), 255).brighter().getRGB(), false);

        Gui.drawRect(5 + 22 + 21 + getX(), 25 + 24 + getY(), 27 + 20 + 22 + getX(), 45 + 24 + getY(), new Color(lastD, lastD, lastD, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("D", 36 + 22 + getX(), 53 + getY(), new Color(flop(lastD, 255), flop(lastD, 255), flop(lastD, 255), 255).brighter().getRGB(), false);
        
        Gui.drawRect(5 + getX(), 25 + 24 + 24 + 16 + getY(), 27 + 20 + 22 + getX(), 45 + 24 + 24 + 16 + getY(), new Color(lastSpace, lastSpace, lastSpace, 255).brighter().getRGB());
        Gui.drawRect(20 + getX(), 34 + 16 + 24 + 24 + getY(), 27 + 5 + 22 + getX(), 45 + 24 + 16 + 15 + getY(), new Color(flop(lastSpace, 255), flop(lastSpace, 255), flop(lastSpace, 255), 255).brighter().getRGB());
        
        Gui.drawRect(5 + getX(), 25 + 20 + 24 + getY(), 27 + 21 - 12 + getX(), 45 + 20 + 24 + getY(), new Color(lastLMB, lastLMB, lastLMB, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("LMB", 19 + getX(), 72 + getY(), new Color(flop(lastLMB, 255), flop(lastLMB, 255), flop(lastLMB, 255), 255).brighter().getRGB(), false);
        
        Gui.drawRect(3 + 33 + getX(), 25 + 20 + 24 + getY(), 27 + 20 + 22 + getX(), 45 + 20 + 24 + getY(), new Color(lastRMB, lastRMB, lastRMB, 255).brighter().getRGB());
        fontRenderer.drawCenteredString("RMB", 49 + getX(), 72 + getY(), new Color(flop(lastRMB, 255), flop(lastRMB, 255), flop(lastRMB, 255), 255).brighter().getRGB(), false);
	}
	
	public void renderKeystrokes() {
		
	}
	
	public int flop(int a, int b){
        return b - a;
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
	
	public boolean isDragging() {
		return dragging;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	public boolean hovered(int mouseX, int mouseY) {
		//System.out.println("Mouse");
		//System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
		//System.out.println("Stuff");
		//System.out.println("Left: " + left * size.getValue() + " Width: " + width * size.getValue() + " Bottom: " + bottom * size.getValue() + " Height: " + height * size.getValue());
		
		return mouseX >= left * size.getValue() && mouseX <= width * size.getValue() && mouseY <= bottom * size.getValue() && mouseY >= height * size.getValue();
	}
}
