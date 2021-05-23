package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import java.awt.Color;

import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;

public class Keybind extends Component {
	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	public static GlyphPageFontRenderer smallFontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 14, false, false, false);
	public static boolean listening = false;

    public Keybind(int x, int y, Setting set, SettingsWindow window){
        super(x, y, set, window);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawRoundedRect(x, y, x + 150, y + 16, 3, isWithinComponent(mouseX, mouseY) ? new Color(100, 100, 100, 255) : new Color(80, 80, 80, 255));
        if (mc.gameSettings.guiScale <= 2)
        	fontRenderer.drawString("Keybind: " + (listening ? "LISTENING" : parent.parent.mod.getKey()), x + 6, y + 1, -1, true);
        else
        	smallFontRenderer.drawString("Keybind: " + (listening ? "LISTENING" : parent.parent.mod.getKey()),x + 6, y + 1, -1, true);

        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if(isWithinComponent(mouseX, mouseY)){
            listening = true;
        }
        super.mouseClicked(mouseX, mouseY);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 150 && mouseY > y && mouseY < y + 16;
    }
}
