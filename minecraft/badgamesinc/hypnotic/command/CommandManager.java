package badgamesinc.hypnotic.command;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.command.commands.About;
import badgamesinc.hypnotic.command.commands.Bind;
import badgamesinc.hypnotic.command.commands.Enchant;
import badgamesinc.hypnotic.command.commands.Help;
import badgamesinc.hypnotic.command.commands.Load;
import badgamesinc.hypnotic.command.commands.Modules;
import badgamesinc.hypnotic.command.commands.NBT;
import badgamesinc.hypnotic.command.commands.Save;
import badgamesinc.hypnotic.command.commands.Say;
import badgamesinc.hypnotic.command.commands.Seed;
import badgamesinc.hypnotic.command.commands.Server;
import badgamesinc.hypnotic.command.commands.Teleport;
import badgamesinc.hypnotic.command.commands.Toggle;
import badgamesinc.hypnotic.command.commands.VClip;
import badgamesinc.hypnotic.util.Wrapper;

public class CommandManager {

	public CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();
	
	public CommandManager() {
		commands.add(new Toggle());
		commands.add(new VClip());
		commands.add(new Say());
		commands.add(new Bind());
		commands.add(new Server());
		commands.add(new Save());
		commands.add(new Teleport());
		commands.add(new Load());
		commands.add(new Seed());
		commands.add(new Enchant());
		commands.add(new Help());
		commands.add(new About());
		commands.add(new Modules());
		commands.add(new NBT());
		//commands.add(new Plugins());
	}
	
	public CopyOnWriteArrayList<Command> getCommands() {
		return commands;
	}
	
	public void callCommand(String input){
		String[] split = input.split(" ");
		String command = split[0];
		String args = input.substring(command.length()).trim();
		for(Command c: getCommands()){
			if(c.getAlias().equalsIgnoreCase(command)){
				try{
					c.onCommand(args, args.split(" "));
				}catch(Exception e){
					Wrapper.tellPlayer("Invalid Command Usage!");
					Wrapper.tellPlayer(c.getSyntax());
				}
				return;
			}
		}

	}
}
