package badgamesinc.hypnotic.gui.newererclickgui.component;

import badgamesinc.hypnotic.gui.newererclickgui.component.components.Button;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class Component {

	public Setting set;
	public Button button;
	public float offset;
	
	public Component(Setting set, Button button, int offset) {
		this.set = set;
		this.button = button;
		this.offset = offset;
	}
	
	public Component(Mod mod, Frame parent, int offset) {
		
	}
	
	public void renderComponent() {
		
	}
	
	public void updateComponent(int mouseX, int mouseY) {
		
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
