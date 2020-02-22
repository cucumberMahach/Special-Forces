package world.objects.player;

import com.badlogic.gdx.math.Vector2;

import controller.ControllerType;
import controller.Direction;
import engine.SpecialForces;
import world.objects.character.CharacterBody;

public class PlayerBody extends CharacterBody{
	
	private Player player;
	private Direction direction;
	private float dx, dy;
	private Vector2 vel;
	private ControllerType controllerType;
	
	public PlayerBody(Player player){
		super(player);
		this.player = player;
		vel = getVelocity();
		direction = Direction.NONE;
		controllerType = SpecialForces.getInstance().getControllerType();
		setIgnoreClass("Teammate");
	}
	
	public void update(float delta){
		if (controllerType == ControllerType.KEYS)
			updateVelocity();
		else
			vel.set(dx, dy).scl(getSpeed());
		super.update(delta);
	}

	private void updateVelocity(){
		switch (direction) {
			case DOWN:
				vel.set(0, -getSpeed());
				break;
			case DOWN_LEFT:
				vel.set(-getSpeed(), -getSpeed());
				break;
			case RIGHT_DOWN:
				vel.set(getSpeed(), -getSpeed());
				break;
			case LEFT:
				vel.set(-getSpeed(), 0);
				break;
			case RIGHT:
				vel.set(getSpeed(), 0);
				break;
			case UP:
				vel.set(0, getSpeed());
				break;
			case LEFT_UP:
				vel.set(-getSpeed(), getSpeed());
				break;
			case UP_RIGHT:
				vel.set(getSpeed(), getSpeed());
				break;
			case NONE:
				vel.set(0, 0);
				break;
		}
		if (direction == Direction.NONE || player.isMouseRotation())
			return;
		updateRotation();
	}
	
	private void updateRotation(){
		final float degrees = -45 * direction.ordinal();
		player.setRotation(degrees);
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public void setDirection(float dx, float dy){
		this.dx = dx;
		this.dy = dy;
	}
}
