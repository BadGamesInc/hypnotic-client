package badgamesinc.hypnotic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.Minecraft;

public class SaveLoad {
    public File dir;
    public File configs;
    public File dataFile;

    public SaveLoad() {
        dir = new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic");
        if (!dir.exists()) {
            dir.mkdir();
        }
        configs = new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic/configs");
        if (!configs.exists()) {
            configs.mkdir();
        }
        dataFile = new File(dir, "data.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}
        }

        this.load();
    }


    public void save() {

        ArrayList<String> toSave = new ArrayList<String>();

        for (Mod mod : Hypnotic.instance.moduleManager.getModules()) {
            toSave.add("MOD:" + mod.getName() + ":" + mod.isEnabled() + ":" + mod.getKey());
        }

        for (Setting set : Hypnotic.instance.setmgr.getSettings()) {
            if (set.isCheck()) {
                toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValBoolean());
            }
            if (set.isCombo()) {
                toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValString());
            }
            if (set.isSlider()) {
                toSave.add("SET:" + set.getName() + ":" + set.getParentMod().getName() + ":" + set.getValDouble());
            }
        }

        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            for (String str : toSave) {
                pw.println(str);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void load() {


        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : lines) {
            String[] args = s.split(":");
            if (s.toLowerCase().startsWith("mod:")) {
                Mod m = Hypnotic.instance.moduleManager.getModuleByName(args[1]);
                if (m != null) {
                    m.setEnabled(Boolean.parseBoolean(args[2]));
                    m.setKey(Integer.parseInt(args[3]));
                }
            } 
            if (s.toLowerCase().startsWith("set:")) {
                Mod m = Hypnotic.instance.moduleManager.getModuleByName(args[2]);
                if (m != null) {
                    Setting set = Hypnotic.instance.setmgr.getSettingByName(args[1]);
                    if (set != null) {
                        if (set.isCheck()) {
                            set.setValBoolean(Boolean.parseBoolean(args[3]));
                        }
                        if (set.isCombo()) {
                            set.setValString(args[3]);
                        }
                        if (set.isSlider()) {
                            set.setValDouble(Double.parseDouble(args[3]));
                        }
                    }
                }
            }
        }
    }
}