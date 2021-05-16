package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class CheckBox extends Component{
	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	public static GlyphPageFontRenderer smallFontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 14, false, false, false);

    public CheckBox(int x, int y, Setting set, SettingsWindow window){
        super(x,y,set,window);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f );
        if (mc.gameSettings.guiScale <= 2)
        	fontRenderer.drawString(set.getName(),x + 15, y + 2 , -1, true);
        else
        	smallFontRenderer.drawString(set.getName(),x + 15, y + 2 , -1, true);

        if (!set.getValBoolean()) {
            RenderUtils.drawCircle(x + 4, y + 8, 6, 100, isWithinComponent(mouseX, mouseY) ? 0xFF707070 : 0xFF505050);
        } else {
            RenderUtils.drawCircle(x + 4, y + 8, 6, 100, -1);
            RenderUtils.drawFilledCircle(x + 4, y + 8, 4, isWithinComponent(mouseX, mouseY) ? ColorUtils.rainbow(6, 0.3f, 0.3f): ColorUtils.rainbow(6, 0.5f, 0.5f));
        }
        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if(isWithinComponent(mouseX, mouseY)) {
            set.setValBoolean(!set.getValBoolean());
        }
        super.mouseClicked(mouseX, mouseY);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 15 + fontRenderer.getStringWidth(set.getName()) && mouseY > y && mouseY < y + 12;
    }
}
