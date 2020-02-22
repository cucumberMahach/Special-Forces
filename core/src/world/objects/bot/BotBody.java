package world.objects.bot;

import com.badlogic.gdx.math.Vector2;

import world.objects.character.CharacterBody;

public class BotBody extends CharacterBody{
	
	private float dx, dy;
	
	private Vector2 vel;
	
	public BotBody(Bot bot){
		super(bot);
		vel = getVelocity();
		setCanPickupItems(false);
	}
	
	public void update(float delta){
		vel.set(dx, dy).scl(getSpeed());
		super.update(delta);
	}
	
	public void setDirection(float dx, float dy){
		this.dx = dx;
		this.dy = dy;
	}
}
