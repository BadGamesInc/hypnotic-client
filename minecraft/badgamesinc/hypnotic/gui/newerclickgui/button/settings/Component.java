package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;

public class Component {

    int x, y;
    Setting set;
    SettingsWindow parent;
    Minecraft mc = Minecraft.getMinecraft();
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);

    public Component(int x, int y, Setting set, SettingsWindow parent) {
        this.x = x;
        this.y = y;
        this.set = set;
        this.parent = parent;
    }

    public void draw(int mouseX, int mouseY){}

    public void mouseClicked(int mouseX, int mouseY){}

    public void mouseRelease(){}

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

    public Setting getSet() {
        return set;
    }

    public void setSet(Setting set) {
        this.set = set;
    }

    public SettingsWindow getParent() {
        return parent;
    }

    public void setParent(SettingsWindow parent) {
        this.parent = parent;
    }
}
