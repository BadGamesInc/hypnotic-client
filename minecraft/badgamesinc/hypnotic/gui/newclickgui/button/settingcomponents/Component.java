package badgamesinc.hypnotic.gui.newclickgui.button.settingcomponents;

import badgamesinc.hypnotic.gui.newclickgui.button.Button;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;

public class Component {

	float x, y;
    Setting set;
    Button parent;
    Minecraft mc = Minecraft.getMinecraft();
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);

    public Component(float x, float y, Setting set, Button parent) {
        this.x = x;
        this.y = y;
        this.set = set;
        this.parent = parent;
    }

    public void draw(int mouseX, int mouseY){}

    public void mouseClicked(int mouseX, int mouseY, int mouseButton){}

    public void mouseRelease(){}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Setting getSet() {
        return set;
    }

    public void setSet(Setting set) {
        this.set = set;
    }

    public Button getParent() {
        return parent;
    }

    public void setParent(Button parent) {
        this.parent = parent;
    }
    
}
