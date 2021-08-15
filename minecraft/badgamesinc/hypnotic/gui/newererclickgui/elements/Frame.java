package badgamesinc.hypnotic.gui.newererclickgui.elements;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newererclickgui.elements.button.Button;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class Frame {

	public ArrayList<Element> elements;
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
	private int openAnimation = 0;
	private float actualHeight = 0;
	
	public Frame(Category cat) {
		this.elements = new ArrayList<Element>();
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
			this.elements.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Element> getElements() {
		return elements;
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
	
	int compSize = 0;
	public void renderFrame(FontRenderer fontRenderer) {
		String title = category.name;
		boolean rainbow = Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled();
		GL11.glPushMatrix();
		GL11.glEnable(3089);
		RenderUtils.scissor(this.x, this.y, this.x + width, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
		
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(0xFF222222).darker().getRGB());
		FontManager.robotoSmall.drawStringWithShadow(title, (this.getX() + 3), (this.getY() + 0.5f), 0xFFFFFFFF);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("hypnotic/textures/clickgui/" + category.name + ".png"));
		Gui.drawModalRectWithCustomSizedTexture(this.x + this.width - 14, this.y, 12, 12, 12, 12, 12, 12);
//		FontManager.robotoSmall.drawStringWithShadow((this.open ? "-" : "+"), (this.x + this.width - 16) + 5, (this.y + 0.5f), -1);
		//ClickGui.instance.drawGradientRect(this.x, this.barHeight, this.x + this.width, this.barHeight - 100, -1072689136, -804253680);
		
		if(this.open) {
			if (this.openAnimation < actualHeight * 100) {
				this.openAnimation+=0.1f;
			}
			if(!this.elements.isEmpty()) {
				for(Element component : elements) {
					actualHeight = elements.size();
					compSize = elements.size();
					component.drawButton();
				}
			}
		} else {
			if (this.openAnimation > 0) {
				this.openAnimation-=0.001;
			}
		}
		GL11.glDisable(3089);
		GL11.glPopMatrix();
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Element elem : elements) {
			elem.setOff(off);
			off += elem.getHeight();
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
