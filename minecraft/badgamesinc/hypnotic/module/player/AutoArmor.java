package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ArmorUtils;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Mod {

	private int[] chestplate;
	private int[] leggings;
	private int[] boots;
	private int[] helmet;
	private int delay;
	private boolean best;
	 
	public AutoArmor() {
		super("AutoArmor", 0, Category.PLAYER, "Automatically equips armor for you");
		this.chestplate = new int[] { 311, 307, 315, 303, 299 };
        this.leggings = new int[] { 312, 308, 316, 304, 300 };
        this.boots = new int[] { 313, 309, 317, 305, 301 };
        this.helmet = new int[] { 310, 306, 314, 302, 298 };
        this.delay = 0;
        this.best = true;
	}
	  
	    public void onUpdate() {
	        this.autoArmor();
	        this.betterArmor();
	        this.setDisplayName("AutoArmor");
	    }
	    
	    public void autoArmor() {
	        if (this.best) {
	            return;
	        }
	        int item = -1;
	        ++this.delay;
	        if (this.delay >= 10) {
	            if (mc.thePlayer.inventory.armorInventory[0] == null) {
	                int[] boots;
	                for (int length = (boots = this.boots).length, i = 0; i < length; ++i) {
	                    final int id = boots[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (mc.thePlayer.inventory.armorInventory[1] == null) {
	                int[] leggings;
	                for (int length = (leggings = this.leggings).length, i = 0; i < length; ++i) {
	                    final int id = leggings[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (mc.thePlayer.inventory.armorInventory[2] == null) {
	                int[] chestplate;
	                for (int length = (chestplate = this.chestplate).length, i = 0; i < length; ++i) {
	                    final int id = chestplate[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (mc.thePlayer.inventory.armorInventory[3] == null) {
	                int[] helmet;
	                for (int length = (helmet = this.helmet).length, i = 0; i < length; ++i) {
	                    final int id = helmet[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (item != -1) {
	                mc.playerController.windowClick(0, item, 0, 1, mc.thePlayer);
	                this.delay = 0;
	            }
	        }
	    }
	    
	    public void betterArmor() {
	        if (!this.best) {
	            return;
	        }
	        ++this.delay;
	        if (this.delay >= 10 && (mc.thePlayer.openContainer == null || mc.thePlayer.openContainer.windowId == 0)) {
	            boolean switchArmor = false;
	            int item = -1;
	            if (mc.thePlayer.inventory.armorInventory[0] == null) {
	                int[] array;
	                for (int j = (array = this.boots).length, i = 0; i < j; ++i) {
	                    final int id = array[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (ArmorUtils.isBetterArmor(0, this.boots)) {
	                item = 8;
	                switchArmor = true;
	            }
	            if (mc.thePlayer.inventory.armorInventory[3] == null) {
	                int[] array;
	                for (int j = (array = this.helmet).length, i = 0; i < j; ++i) {
	                    final int id = array[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (ArmorUtils.isBetterArmor(3, this.helmet)) {
	                item = 5;
	                switchArmor = true;
	            }
	            if (mc.thePlayer.inventory.armorInventory[1] == null) {
	                int[] array;
	                for (int j = (array = this.leggings).length, i = 0; i < j; ++i) {
	                    final int id = array[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (ArmorUtils.isBetterArmor(1, this.leggings)) {
	                item = 7;
	                switchArmor = true;
	            }
	            if (mc.thePlayer.inventory.armorInventory[2] == null) {
	                int[] array;
	                for (int j = (array = this.chestplate).length, i = 0; i < j; ++i) {
	                    final int id = array[i];
	                    if (ArmorUtils.getItem(id) != -1) {
	                        item = ArmorUtils.getItem(id);
	                        break;
	                    }
	                }
	            }
	            if (ArmorUtils.isBetterArmor(2, this.chestplate)) {
	                item = 6;
	                switchArmor = true;
	            }
	            boolean b = false;
	            ItemStack[] stackArray;
	            for (int k = (stackArray = mc.thePlayer.inventory.mainInventory).length, l = 0; l < k; ++l) {
	                final ItemStack stack = stackArray[l];
	                if (stack == null) {
	                    b = true;
	                    break;
	                }
	            }
	            switchArmor = (switchArmor && !b);
	            if (item != -1) {
	                mc.playerController.windowClick(0, item, 0, switchArmor ? 4 : 1, mc.thePlayer);
	                this.delay = 0;
	            }
	        }
	    }
	
	

}
