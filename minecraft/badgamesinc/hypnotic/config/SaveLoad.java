package badgamesinc.hypnotic.config;

import java.io.*;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import net.minecraft.client.Minecraft;

public class SaveLoad {
    public File dir;
    public File configs;
    public File dataFile;

    //Saves keybinds and friends
    
    public SaveLoad() {
        dir = new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic");
        if (!dir.exists()) {
            dir.mkdir();
        }
        configs = new File(Minecraft.getMinecraft().mcDataDir, "Hypnotic/configs");
        if (!configs.exists()) {
            configs.mkdir();
        }
        dataFile = new File(configs, "data.txt");
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
        
        for (String friends : Hypnotic.instance.friendManager.friends) {
        	toSave.add("FRIEND:" + friends);
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
                    m.setKey(Integer.parseInt(args[3]));
                }
                
            } else if (s.toLowerCase().startsWith("friend:")) {
            	Hypnotic.instance.friendManager.addFriend(args[1]);
            }
        }
    }
}
