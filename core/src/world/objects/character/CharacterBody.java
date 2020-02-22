package world.objects.character;

import com.badlogic.gdx.math.Vector2;

import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import engine.utils.Motion;
import world.objects.Item;

public abstract class CharacterBody {
	
	private Character character;
	private final float maxSpeed = 2f;
	private float speed, off, stepFreq;
	private Vector2 vel;
	private String ignore;
	private boolean canPickup = true;
	
	public CharacterBody(Character character){
		this.character = character;
		vel = new Vector2();
		character.setCollideBox(Style.TILE_SIZE - 6, Style.TILE_SIZE - 6);
		off = Style.TILE_SIZE / 2f - character.getCollideWidth() / 2;
		speed = maxSpeed;
	}
	
	protected void setCanPickupItems(boolean value){
		canPickup = value;
	}
	
	protected void setIgnoreClass(String className){
		ignore = className;
	}
	
	public void update(float delta){
		if (vel.x == 0 && vel.y == 0)
			return;
		updateColliding();
		character.moveBy(vel.x, vel.y);
		if (canPickup)
			updateItems();
		if (isMoving())
			sound(delta);
	}
	
	private void sound(float delta){
		if (stepFreq > Style.STEP_FREQ * (maxSpeed / speed)){
			int tile = character.getWorld().map().get(Maths.toMapX(character.getCenterX()), Maths.toMapY(character.getCenterY())).tile;
			SpecialForces.getInstance().sounds().playStep(tile);
			stepFreq = 0;
		}
		stepFreq += delta;
	}
	
	private void updateItems(){
		final Item item = character.getWorld().physics().getItem(character.getX(), character.getY(), character.getCollideWidth(), character.getCollideHeight());
		if (item != null)
			character.pickUp(item);
	}
	
	private void updateColliding(){
		final Motion motion = character.getWorld().physics().canIMove(character.getX() + off, character.getY() + off, character.getCollideWidth(), character.getCollideHeight(), vel.x, vel.y, character);
		if (ignore != null && motion.isAnyBarrierIs(ignore))
			return;
		if (!motion.x)
			vel.x = 0;
		if (!motion.y)
			vel.y = 0;
	}
	
	public boolean isMoving(){
		return vel.x != 0 || vel.y != 0;
	}
	
	public void setSpeedRate(float rate){
		speed = maxSpeed * rate;
	}
	
	protected Vector2 getVelocity(){
		return vel;
	}
	
	protected float getSpeed(){
		return speed;
	}
	
	protected float getSpeedPercent(){
		return speed / maxSpeed;
	}
}
