package badgamesinc.hypnotic.gui.newclickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.Panel;
import badgamesinc.hypnotic.gui.clickgui.elements.ModuleButton;
import badgamesinc.hypnotic.gui.clickgui.settings.SettingsManager;
import badgamesinc.hypnotic.gui.clickgui.util.ColorUtil;
import badgamesinc.hypnotic.gui.newclickgui.button.Button;
import badgamesinc.hypnotic.gui.newclickgui.button.Element;
import badgamesinc.hypnotic.gui.newclickgui.button.settingelements.ElementSlider;
import badgamesinc.hypnotic.module.Category;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen {

	public static ClickGUI instance = new ClickGUI();
	public SettingsManager setmgr;
	public Button but = null;
	ArrayList<Frame> frames;
	public float offset;
	
	public ClickGUI() {
		setmgr = Hypnotic.instance.setmgr;
		frames = new ArrayList<>();
		
		offset = 0.1f;
		for (Category category : Category.values()) {
			frames.add(new Frame(category, 10 + offset, 15));
			offset += 110;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.drawDefaultBackground();
		for (Frame frame : frames) {
			frame.render(mouseX, mouseY);
		}
		
		for (Frame frame : frames) {
				for (Button b : frame.buttons) {
					if (b.extended && b.buttonelements != null && !b.buttonelements.isEmpty()) {
						double off = 0;
						Color temp = ColorUtil.getClickGUIColor().darker();
						int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
						
						for (Element e : b.buttonelements) {
							e.offset = off;
							e.update();
							if(Hypnotic.instance.setmgr.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
								Gui.drawRect(e.x, e.y, e.x + e.width + 0, e.y + e.height, outlineColor);
							}
							e.drawScreen(mouseX, mouseY, partialTicks);
							off += e.height;
						}
					}
				}
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(but != null)return;

		for (Frame frame : frames) {
				for (Button b : frame.buttons) {
					if (b.extended) {
						for (Element e : b.buttonelements) {
							if (e.mouseClicked(mouseX, mouseY, mouseButton))
								return;
						}
					}
				}
		}
		
		for (Frame frame : frames) {
			frame.onClick(mouseX, mouseY, mouseButton);
		}
		
		
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		
		if(but != null)return;
		
		for (Frame frame : frames) {

				for (Button b : frame.buttons) {
					if (b.extended) {
						for (Element e : b.buttonelements) {
							e.mouseReleased(mouseX, mouseY, state);
						}
					}
				}
			
		}
		
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		for (Frame frame : frames) {
			for (Button b : frame.buttons) {
				if (b.extended) {
					for (Element e : b.buttonelements) {
						if(e instanceof ElementSlider){
							((ElementSlider)e).dragging = false;
						}
					}
				}
			}
		}
	}
	
	public void closeAllSettings() {
		for (Frame frame : frames) {
				for (Button button : frame.buttons) {
					button.extended = false;
				}
		}
	}
}
