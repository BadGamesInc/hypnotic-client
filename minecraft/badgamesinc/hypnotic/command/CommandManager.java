package badgamesinc.hypnotic.command;

import java.util.concurrent.CopyOnWriteArrayList;

import badgamesinc.hypnotic.command.commands.*;
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
