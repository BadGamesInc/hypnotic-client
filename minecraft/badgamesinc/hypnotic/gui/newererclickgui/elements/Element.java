package badgamesinc.hypnotic.gui.newererclickgui.elements;

import badgamesinc.hypnotic.gui.newererclickgui.elements.button.Button;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class Element {

	public Setting set;
	public Button button;
	public float offset;
	
	public Element(Setting set, Button button, int offset) {
		this.set = set;
		this.button = button;
		this.offset = offset;
	}
	
	public Element(Mod mod, Frame parent, int offset) {
		
	}
	
	public void drawButton() {
		
	}
	
	public void updateElement(int mouseX, int mouseY) {
		
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		
	}
	
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
	}
	
	public int getParentHeight() {
		return 0;
	}
	
	public void keyTyped(char typedChar, int key) {
		
	}
	
	public void setOff(int newOff) {
		
	}
	
	public int getHeight() {
		return 0;
	}
}
