package badgamesinc.hypnotic.module.render;

import java.awt.Color;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class ChestESP extends Mod {

	public BooleanSetting solid = new BooleanSetting("Solid", true);
	public BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
	
	public ChestESP() {
		super("ChestESP", 0, Category.RENDER, "Renders an outline around chests");
		addSettings(solid, rainbow);
	}
	
	@EventTarget
	public void even3d(Event3D e) {
		for (TileEntity te : mc.theWorld.loadedTileEntityList) {
			if (te instanceof TileEntityChest) {
				Color color = new Color(ColorUtils.rainbow(4, 0.5f, 0.5f));
				if (!solid.isEnabled())
					RenderUtils.blockESPBox(te.getPos(), new Color(10, 10, 105, 255).getRGB());
				else
					RenderUtils.drawSolidBlockESP(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), color.getRed(), color.getGreen(), color.getBlue(), 2);
			}			
		}
	}

}
