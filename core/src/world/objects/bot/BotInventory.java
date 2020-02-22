package world.objects.bot;

import engine.Loader;
import world.objects.character.*;
import world.objects.character.Character;

public class BotInventory extends CharacterInventory{
	
	public BotInventory(Character character, Loader loader, boolean halfDamage){
		super(character, loader, loader.createWeapons(halfDamage));
	}
	
}