package world.objects.bot;

import engine.Loader;
import world.gameplay.Shoot;
import world.objects.character.Character;

public class Bot extends Character{
	
	private BotBody body;
	private BotInventory inventory;
	private BotActions actions;
	private BotCommands commands;
	private BotAI botAi;
	
	public Bot(Loader loader, String skin, boolean halfDamage, float x, float y){
		body = new BotBody(this);
		actions = new BotActions(this);
		inventory = new BotInventory(this, loader, halfDamage);
		set(loader, skin, x, y, body, actions, inventory);
		
		setHealth(50);
		setEnergy(100);
		
		commands = new BotCommands(this);
		botAi = new BotAI(this);
	}
	
	@Override
	public void act(float delta) {
		botAi.update(delta);
		commands.update(delta);
		super.act(delta);
	}
	
	public BotAI ai(){
		return botAi;
	}
	
	public BotCommands commands(){
		return commands;
	}
	
	public BotActions actions(){
		return actions;
	}
	
	public void setDirection(float dx, float dy){
		body.setDirection(dx, dy);
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		super.getDamage(damage, isExplosion);
	}

	@Override
	public void getShot(Shoot shoot) {
		if (getType() != shoot.from.getType())
			commands.rotateTo(shoot.origX, shoot.origY);
		super.getShot(shoot);
	}
}
