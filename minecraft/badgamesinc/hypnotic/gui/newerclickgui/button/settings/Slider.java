package badgamesinc.hypnotic.gui.newerclickgui.button.settings;

import java.awt.Color;

import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.gui.Gui;

public class Slider extends Component{
	
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
	public static GlyphPageFontRenderer smallFontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 14, false, false, false);
	
    public Slider(int x, int y, Setting set, SettingsWindow window){
        super(x, y, set, window);
    }

    boolean isSliding = false;

    @Override
    public void draw(int mouseX, int mouseY) {
    	NumberSetting numSet = (NumberSetting)set;
        int height = 20;
        int width = 100;
        double progress;
        if (this.isSliding) {
            progress = (double)mouseX;
            double mouseProgress = (progress - (double)this.x) / (double)width;
            numSet.setValue((numSet.getMax() - numSet.getMin()) * mouseProgress);
        }

        if (numSet.getValue() <= numSet.getMin()) {
            numSet.setValue(numSet.getMin());
        }

        if (numSet.getValue() >= numSet.getMax()) {
            numSet.setValue(numSet.getMax());
        }

        progress = numSet.getValue() / numSet.getMax();
        Gui.drawRect(x, y + height / 2 - 1, x + width, y + height / 2 + 1, new Color(150, 150, 150, 255).getRGB());
        Gui.drawRect(x, y + height / 2 - 1, (this.x + (double)width * progress), y + height / 2 + 1, isWithinComponent(mouseX, mouseY) ? ClickGUI.color : ClickGUI.color);
        RenderUtils.drawFilledCircle((int) (this.x + (double)width * progress)    , y + height / 2, 3, isWithinComponent(mouseX, mouseY) ? ClickGUI.color : ClickGUI.color);
        if (mc.gameSettings.guiScale <= 2)
        	fontRenderer.drawString(numSet.name + " : " + ((double)Math.round(numSet.getValue() * 1000.0D) / 1000.0D), x + width + 5, y + 1, -1, true);
        else
        	smallFontRenderer.drawString(numSet.name + " : " + ((double)Math.round(numSet.getValue() * 1000.0D) / 1000.0D), x + width + 5, y + 1, -1, true);

        
        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if(isWithinComponent(mouseX, mouseY)){
            this.isSliding = true;
        }
        super.mouseClicked(mouseX, mouseY);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        int height = 20;
        int width = 100;
        return mouseX > x && x < x + width && mouseY > y && mouseY < y + height - 5;
    }

    @Override
    public void mouseRelease() {
        isSliding = false;
        super.mouseRelease();
    }
}
