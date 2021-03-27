package badgamesinc.hypnotic.module.movement;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

public class InventoryMove extends Mod{

	public InventoryMove() {
        super("InventoryMove", 0, Category.MOVEMENT, "Allows you to walk in GUIs");
    }
    
    @Override
    public void onUpdate() {
        KeyBinding[] arrayofKeyBinding;
        final KeyBinding[] moveKeys = arrayofKeyBinding = new KeyBinding[] { mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump };
        if (!(mc.currentScreen instanceof GuiContainer) || mc.currentScreen instanceof GuiIngameMenu) {
            if (mc.currentScreen == null) {
                for (int j = moveKeys.length, i = 0; i < j; ++i) {
                    final KeyBinding bind = arrayofKeyBinding[i];
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
        else {
            if (mc.currentScreen instanceof GuiChat) {
                return;
            }
            arrayofKeyBinding = moveKeys;
            for (int j = moveKeys.length, i = 0; i < j; ++i) {
                final KeyBinding bind = arrayofKeyBinding[i];
                bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
            }
            mc.thePlayer.setSprinting(false);
        }
    }
}
