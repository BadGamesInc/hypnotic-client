package badgamesinc.hypnotic.gui.newclickgui.button;

import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.gui.clickgui.util.FontUtil;
import badgamesinc.hypnotic.gui.newclickgui.ClickGUI;
import badgamesinc.hypnotic.util.font.UnicodeFontRenderer;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;

public class Element {
	public ClickGUI clickgui;
	public Button parent;
	public Setting set;
	public double offset;
	public double x;
	public double y;
	public double width;
	public double height;
	
	public static UnicodeFontRenderer ufr = UnicodeFontRenderer.getFontFromAssets("Roboto-Medium", 20, 0, 1, 1);
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	
	
	public String setstrg;
	
	public boolean comboextended;
	
	public void setup(){
		clickgui = ClickGUI.instance;
	}
	
	public void update(){
		x = parent.x + 12;
		y = parent.y + offset + 12;
		width = parent.width - 24;
		height = 15;

		String sname = set.getName();
		if(set.isCheck()){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			double textx = x + width - fontRenderer.getStringWidth(setstrg);
			if (textx < x + 13) {
				width += (x + 13) - textx + 1;
			}
		}else if(set.isCombo()){
			height = comboextended ? set.getOptions().size() * (fontRenderer.getFontHeight() - 0.4f) + 15 : 15;
			
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			int longest = fontRenderer.getStringWidth(setstrg);
			for (String s : set.getOptions()) {
				int temp = fontRenderer.getStringWidth(s);
				if (temp > longest) {
					longest = temp;
				}
			}
			double textx = x + width - longest;
			if (textx < x) {
				width += x - textx + 1;
			}
		}else if(set.isSlider()){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
			String displaymax = "" + Math.round(set.getMax() * 100D)/ 100D;
			double textx = x + width - fontRenderer.getStringWidth(setstrg) - fontRenderer.getStringWidth(displaymax) - 4;
			if (textx < x) {
				width += x - textx + 1;
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		return isHovered(mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
