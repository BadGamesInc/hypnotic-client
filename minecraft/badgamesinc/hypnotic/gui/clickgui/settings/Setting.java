package badgamesinc.hypnotic.gui.clickgui.settings;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Mod;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Setting {


    public String name;
    private Mod parent;
    public String mode;

    private String sval;
    private ArrayList<String> options;

    private boolean bval;

    private double dval;
    private double min;
    private double max;
    private boolean onlyint = false;


    public Setting(String name, Mod parent, String sval, ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Combo";
	if (Hypnotic.instance.saveload != null) {
            sval = this.getValString
        }
	    //probably doesnt work, will fix when i get home
    }

    public Setting(String name, Mod parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Check";
	    if (Hypnotic.instance.saveload != null) {
            bval = this.getValBoolean
        }
	    //probably doesnt work, will fix when i get home
    }

    public Setting(String name, Mod parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
	    if (Hypnotic.instance.saveload != null) {
            dval = this.getValDouble
        }
	    //probably doesnt work, will fix when i get home
    }

    public String getName() {
        return name;
    }

    public Mod getParentMod() {
        return parent;
    }

    public String getValString() {
        return this.sval;
    }


    public ArrayList<String> getOptions() {
        return this.options;
    }

    public boolean getValBoolean() {
        return this.bval;
    }

    public void setValBoolean(boolean in) {
        this.bval = in;
        if (Hypnotic.instance.saveload != null) {
            Hypnotic.instance.saveload.save();
        }
    }

    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int) dval;
        }
        return this.dval;
    }

    public void setValString(String in) {
        this.sval = in;
        if (Hypnotic.instance.saveload != null) {
            Hypnotic.instance.saveload.save();
        }
    }

    public int getValInt() {
        if (this.onlyint) {
            this.dval = (int) dval;
        }
        return (int) this.dval;
    }

    public void setValDouble(double in) {
        this.dval = in;
        if (Hypnotic.instance.saveload != null) {
            Hypnotic.instance.saveload.save();
        }
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Combo");
    }

    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }

    public boolean onlyInt() {
        return this.onlyint;
    }

	public boolean is(String string) {
		if(getValString().equalsIgnoreCase(string)) {
			return true;
		}
			return false;
	}

}
