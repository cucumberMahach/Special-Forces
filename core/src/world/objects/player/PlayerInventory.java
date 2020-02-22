package world.objects.player;

import engine.Loader;
import world.gameplay.Weapon;
import world.objects.character.Character;
import world.objects.character.CharacterInventory;

public class PlayerInventory extends CharacterInventory{
	
	public PlayerInventory(Character character, Loader loader, Weapon weapons[]){
		super(character, loader, weapons);
	}
	
}