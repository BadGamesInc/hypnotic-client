package badgamesinc.hypnotic.config;

import java.io.*;
import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newclickgui.ClickGUI;
import badgamesinc.hypnotic.gui.newererclickgui.ClickGui;
import badgamesinc.hypnotic.gui.newererclickgui.component.Frame;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class SaveLoad {
    public File dir;
    public File configs;
    public File dataFile;

    //Saves various aspects of the client
    
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
        
        for (String message : Hypnotic.instance.moduleManager.chatSpammer.custom) {
        	toSave.add("MESSAGE:" + message);
        }
        
        for (Frame frame : ClickGui.instance.frames) {
        	toSave.add("CLICKGUI:" + frame.category + ":X:" + frame.getX() + ":Y:" + frame.getY() + ":OPEN:" + frame.isOpen());
        }
        
        toSave.add("MAINMENUBG:" + GuiMainMenu.getMenuIndex());

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
            } else if (s.toLowerCase().startsWith("message:")) {
            	Hypnotic.instance.moduleManager.chatSpammer.custom.add(args[1]);
            } else if (s.toLowerCase().startsWith("mainmenubg")) {
            	GuiMainMenu.menuIndex = Integer.parseInt(args[1]);
            } else if (s.toLowerCase().startsWith("clickgui")) {
//            	for (Frame frame : ClickGui.instance.frames) {
//            		if (s.toLowerCase().contains("clickgui:combat") && frame.category.name.equalsIgnoreCase("Comabt")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:movement") && frame.category.name.equalsIgnoreCase("Movement")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:player") && frame.category.name.equalsIgnoreCase("Player")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:render") && frame.category.name.equalsIgnoreCase("Render")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:world") && frame.category.name.equalsIgnoreCase("World")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:misc") && frame.category.name.equalsIgnoreCase("Misc")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		} else if (s.toLowerCase().contains("clickgui:gui") && frame.category.name.equalsIgnoreCase("Gui")) {
//            			frame.setX(Integer.parseInt(args[3]));
//            			frame.setX(Integer.parseInt(args[5]));
//            			frame.setOpen(Boolean.parseBoolean(args[7]));
//            		}
//            	}
            }
        }
    }
    
    private Frame getFrameByName(String frameName) {
		for(Frame frame : ClickGui.instance.frames) {
			if ((frame.category.name.trim().equalsIgnoreCase(frameName)) || (frame.toString().trim().equalsIgnoreCase(frameName.trim()))) {
				return frame;
			}
		}
		return null;
	}
}
