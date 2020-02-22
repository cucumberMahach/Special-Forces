package world.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import engine.SpecialForces;
import engine.Style;
import view.Actor;

public class Path extends 	Actor{
	
	private float dx, dy;
	private float distance, pos1, pos2;
	private final float step, len;
	private boolean remove, active;
	private ShapeRenderer shaper;
	
	public Path(){
		shaper = SpecialForces.getInstance().getShaper();
		step = Style.BULLET_PATH_STEP;
		len = Style.BULLET_PATH_LEN;
	}
	
	public void start(float origX, float origY, float dirX, float dirY, float distance){
		this.dx = dirX;
		this.dy = dirY;
		this.distance = distance;
		setPosition(origX, origY);
		start();
	}
	
	@Override
	public void act(float delta){
		if (!active)
			return;
		
		if (pos2 >= distance || remove)
			stop();
		
		if (pos1 + step < distance){
			pos1 += step;
		}else{
			pos1 = distance;
		}
		
		if (pos1 - (pos2 + step) >= len || pos1 == distance){
			if (pos2 + step < distance){
				pos2 += step;
			}else{
				remove = true;
			}
		}
	}
	
	private void start(){
		pos1 = 0;
		pos2 = 0;
		remove = false;
		active = true;
	}
	
	private void stop(){
		active = false;
	}
	
	public void restore(){
		remove = false;
		stop();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!active)
			return;
		batch.end();
		shaper.begin(ShapeType.Line);
		shaper.line(getX() + dx * pos2, getY() + dy * pos2, getX() + dx * pos1, getY() + dy * pos1, Style.BULLET_PATH_COLOR1, Style.BULLET_PATH_COLOR2);
		shaper.end();
		batch.begin();
	}
}
