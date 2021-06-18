package badgamesinc.hypnotic.gui.newererclickgui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.component.Component;
import badgamesinc.hypnotic.gui.newererclickgui.component.Frame;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends GuiScreen {

	public ArrayList<Frame> frames;
	public static int color;
	public static int rainbowOrder = 0;
	public static ClickGui instance = new ClickGui();
	
	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Category category : Category.values()) {
			if (category == Category.HIDDEN)
				continue;
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(!Hypnotic.instance.moduleManager.clickGui.rainbowGUI.isEnabled()) 
        {
        	this.color = ColorUtil.getClickGUIColor().getRGB();
        }
        else 
        {
        	this.color = ColorUtils.rainbow(6, 0.5f, 0.5f, rainbowOrder);
        }
		this.drawDefaultBackground();
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
		
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}
	
	@Override
	public void onGuiClosed() {
		for(Frame frame : frames) {
			if (frame.isOpen()) {
				frame.shouldBeOpen = true;
			}
		}
		Hypnotic.instance.saveload.save();
		super.onGuiClosed();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
