package world.objects.player;

import world.objects.character.CharacterActions;

public class PlayerActions extends CharacterActions{
	
	private Player player;
	
	public PlayerActions(Player player){
		super(player);
		this.player = player;
	}
	
	@Override
	public void shoot(float x, float y) {
		player.mouseRotation(true);
		super.shoot(x, y);
	}
}
