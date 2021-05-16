package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import java.awt.Color;

import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;

public class ComboBox extends Component {
	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);

    public ComboBox(int x, int y, Setting set, SettingsWindow window){
        super(x, y, set, window);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtils.drawRoundedRect(x, y, x + 150, y + 16, 3, isWithinComponent(mouseX, mouseY) ? new Color(100, 100, 100, 255) : new Color(80, 80, 80, 255));
        fontRenderer.drawString(set.getName() + " : " + set.getValString(),x + 2, y + 1, -1, true);

        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if(isWithinComponent(mouseX, mouseY)){
            int index = set.getOptions().indexOf(set.getValString());
            if(index + 1 >= set.getOptions().size()){
                set.setValString(set.getOptions().get(0));
            }else {
                set.setValString(set.getOptions().get(index + 1));
            }
        }
        super.mouseClicked(mouseX, mouseY);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 150 && mouseY > y && mouseY < y + 16;
    }
}
