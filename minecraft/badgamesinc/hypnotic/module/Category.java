package badgamesinc.hypnotic.module;

import badgamesinc.hypnotic.Hypnotic;

public enum Category {
	COMBAT, MOVEMENT, PLAYER, RENDER, WORLD, MISC, GUI;
	
	public static int size(Category c) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(c)) {
				i++;
			}
		}
		
		return i;
	}
	
	public static int placeInListRENDER(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(RENDER) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(RENDER) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
	
	public static int placeInListMOVEMENT(Mod mod) {
			
			int i = 0;
			
			for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
				if (m.getCategory().equals(MOVEMENT) && !m.equals(mod)) {
					i++;
					continue;
				}
				
				if (m.getCategory().equals(MOVEMENT) && m.equals(mod)) {
					return i;
				}
			}
			
			return 0;
		}
	
	public static int placeInListCOMBAT(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(COMBAT) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(COMBAT) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
	
	public static int placeInListPLAYER(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(PLAYER) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(PLAYER) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
	
	public static int placeInListWORLD(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(WORLD) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(WORLD) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
	
	public static int placeInListMISC(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(MISC) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(MISC) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
	
	public static int placeInListGUI(Mod mod) {
		
		int i = 0;
		
		for (Mod m : Hypnotic.instance.moduleManager.getModules()) {
			if (m.getCategory().equals(GUI) && !m.equals(mod)) {
				i++;
				continue;
			}
			
			if (m.getCategory().equals(GUI) && m.equals(mod)) {
				return i;
			}
		}
		
		return 0;
	}
}
