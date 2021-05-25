package badgamesinc.hypnotic.settings.settingtypes;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.settings.Setting;

public class KeybindSetting extends Setting {

    private int code;

    public KeybindSetting(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code == -1 ? Keyboard.KEY_NONE : code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
