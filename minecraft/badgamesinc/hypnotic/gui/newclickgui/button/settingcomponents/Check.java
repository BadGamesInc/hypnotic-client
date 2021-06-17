package badgamesinc.hypnotic.gui.newclickgui.button.settingcomponents;

import java.awt.Color;

import badgamesinc.hypnotic.gui.newclickgui.button.Button;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class Check extends Component {
	
	BooleanSetting boolSet = (BooleanSetting)set;

	public Check(float x, float y, Setting set, Button parent) {
		super(x, y, set, parent);
	}
	
	@Override
	public void draw(int mouseX, int mouseY) {
		GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f );
        fontRenderer.drawString(boolSet.name,x + 15, y + 2 , -1, true);

        if (!boolSet.isEnabled()) {
            RenderUtils.drawCircle(x + 4, y + 8, 6, 100, isWithinComponent(mouseX, mouseY) ? 0xFF707070 : 0xFF505050);
        } else {
            RenderUtils.drawCircle(x + 4, y + 8, 6, 100, -1);
            RenderUtils.drawFilledCircle((int)x + 4, (int)y + 8, 4, isWithinComponent(mouseX, mouseY) ? ClickGUI.color: ClickGUI.color);
        }
       // Gui.drawRect(x, y + parent.height, x + parent.width, y + parent.height, new Color(0, 0, 0, 150).darker().getRGB());
		super.draw(mouseX, mouseY);
	}
	
	@Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isWithinComponent(mouseX, mouseY) && mouseButton == 0) {
        	boolSet.setEnabled(!boolSet.isEnabled());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isWithinComponent(int mouseX, int mouseY){
        return mouseX > x && mouseX < x + 15 + fontRenderer.getStringWidth(boolSet.name) && mouseY > y && mouseY < y + 12;
    }

}
