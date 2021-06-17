package badgamesinc.hypnotic.gui.newererclickgui.component;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.sun.security.ntlm.Client;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.component.components.Button;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	public boolean shouldBeOpen = false;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	
	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = true;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Mod mod : Hypnotic.instance.moduleManager.getModulesInCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		String title = category.name;
		
		boolean rainbow = Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled();
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(0xFF222222).darker().getRGB());
		FontManager.robotoSmall.drawStringWithShadow(title, (this.getX() + 3), (this.getY() + 0.5f), 0xFFFFFFFF);
		FontManager.robotoSmall.drawStringWithShadow((this.open ? "-" : "+"), (this.x + this.width - 16) + 5, (this.y + 0.5f), -1);
		ClickGui.instance.drawGradientRect(this.x, this.barHeight, this.x + this.width, this.barHeight - 100, -1072689136, -804253680);
		if(this.open) {
			//if (this.open) {
				
			//}
			if(!this.components.isEmpty()) {
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
