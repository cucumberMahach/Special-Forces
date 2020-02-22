package world.objects.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import controller.Direction;
import controller.PlayerController;
import engine.Loader;
import engine.SpecialForces;
import stages.World;
import world.gameplay.Shoot;
import world.gameplay.Weapon;
import world.objects.ObjectType;
import world.objects.character.Character;

public class Player extends Character{

	private PlayerController controller;
	private PlayerBody body;
	private PlayerInventory inventory;
	private PlayerActions actions;
	private ObjectType shootedObjectType;
	
	private boolean mouseRotation;
	
	public Player(Loader loader, Weapon weapons[], float x, float y){
		body = new PlayerBody(this);
		actions = new PlayerActions(this);
		inventory = new PlayerInventory(this, loader, weapons);
		set(loader, "player", x, y, body, actions, inventory);
		
		ignorePathFinding(true);
		
		setType(ObjectType.PLAYER);
		setHealth(100);
		setEnergy(100);
	}
	
	@Override
	public void act(float delta) {
		updateFeets(delta);
		if (controller != null)
			controller.update();
		body.update(delta);
		updateRotation();
		actions.update(delta);
		updateHealth();
	}
	
	@Override
	protected void die(boolean exploded) {
		getWorld().manager().incPlayerDie();
		Timer.instance().scheduleTask(new RestartTask(getWorld()), 3);
		super.die(exploded);
	}
	
	public void mouseRotation(boolean value){
		mouseRotation = value;
	}
	
	private void updateRotation(){
		/*if (mouseRotation)
			mouseRotation = false;*/
	}
	
	public boolean isMouseRotation(){
		return mouseRotation;
	}
	
	public PlayerActions actions(){
		return actions;
	}
	
	public void setDirection(Direction direction){
		body.setDirection(direction);
	}
	
	public void setDirection(float x, float y){
		body.setDirection(x, y);
	}
	
	public void setSpeedRate(float rate){
		body.setSpeedRate(rate);
	}
	
	public void setController(PlayerController controller){
		this.controller = controller;
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		if (shootedObjectType != ObjectType.TEAMMATE)
			super.getDamage(damage, isExplosion);
	}

	@Override
	public void getShot(Shoot shoot) {
		shootedObjectType = shoot.object.getType();
		super.getShot(shoot);
	}
}

class RestartTask extends Task{
	private World world;
	
	public RestartTask(World world) {
		this.world = world;
	}
	
	@Override
	public void run() {
		world.manager().restart();
	}
	
}
