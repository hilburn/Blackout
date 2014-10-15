package com.hilburn.blackout.handlers;



import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import com.hilburn.blackout.Blackout;
import com.hilburn.blackout.tileentity.stellarfabricator.Recipe;

public class CommandHandler extends CommandBase
{
	public String getCommandName(){
		return "blackout";
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel(){
		return 2;
	}

	public String getCommandUsage(ICommandSender par1ICommandSender){
		return "";
	}

	public void processCommand(ICommandSender sender, String[] args){
		
		if(args[0].equals("reload")){
			Recipe.clearRecipes();
			ConfigHandler.init(Blackout.instance.config);
			return;
		}
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr){
		return null;
	}

	/**
	 * Return whether the specified command parameter index is a username parameter.
	 */
	public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2){
		return par2 == 0;
	}
}