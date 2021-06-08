package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import java.awt.Color;

import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;

public class ComboBox extends Component {
	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
	public static GlyphPageFontRenderer smallFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 14, false, false, false);
	ModeSetting modeSet = (ModeSetting)set;

    public ComboBox(int x, int y, Setting set, SettingsWindow window){
        super(x, y, set, window);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawRoundedRect(x, y, x + 150, y + 16, 3, isWithinComponent(mouseX, mouseY) ? new Color(100, 100, 100, 255) : new Color(80, 80, 80, 255));
        if (mc.gameSettings.guiScale <= 2)
        	fontRenderer.drawString(modeSet.name + " : " + modeSet.getSelected(),x + 6, y + 1, -1, true);
        else
        	smallFontRenderer.drawString(modeSet.name + " : " + modeSet.getSelected(),x + 6, y + 1, -1, true);

        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if(isWithinComponent(mouseX, mouseY)){
            int index = modeSet.getModes().indexOf(modeSet.getSelected());
            if(index + 1 >= modeSet.getModes().size()){
                modeSet.setSelected(modeSet.getModes().get(0));
            }else {
            	modeSet.setSelected(modeSet.getModes().get(index + 1));
            }
        }
        super.mouseClicked(mouseX, mouseY);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 150 && mouseY > y && mouseY < y + 16;
    }
}
