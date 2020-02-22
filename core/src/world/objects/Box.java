package world.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import world.gameplay.Shoot;

public class Box extends MapObject{
	
	private TextureRegion texture;
	private int health;
	private boolean exploded;
	
	private final Vector2 vect;
	
	public Box(Loader loader, float x, float y){
		texture = loader.getObject("box");
		setPosition(x, y);
		setSize(Style.TILE_SIZE, Style.TILE_SIZE);
		setOrigin(Align.center);
		health = 30;
		vect = Maths.getTmpVector();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		checkHealth();
	}
	
	private void checkHealth(){
		if (health <= 0)
			broke();
	}
	
	private void broke(){
		final float vel = exploded ? Style.EXPLODE_DROP_SCL : 1;
		for (int i = 0; i < 4; i++){
			vect.set(5,5).scl(vel);
			vect.rotate(MathUtils.random(360));
			getWorld().effects().dropParticle("wood", getCenterX(), getCenterY(), vect.x, vect.y, 0.9f, 10, MathUtils.random(0.7f, 0.8f), this);
		}
		removeGlobal();
		SpecialForces.getInstance().sounds().wood(2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isInView())
			batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		health -= damage;
		SpecialForces.getInstance().sounds().wood(3);
		exploded = isExplosion;
	}

	@Override
	public void getShot(Shoot shoot) {
		if (getWorld() == null)
			return;
		vect.set(shoot.hitX - getCenterX(), shoot.hitY - getCenterY());
		vect.nor().scl(3);
		vect.rotate(MathUtils.randomTriangular(90));
		getWorld().effects().dropParticle("wood", shoot.hitX, shoot.hitY, vect.x, vect.y, 0.9f, 10, MathUtils.random(Style.CHARACTER_SIZECOF, 0.3f), this);
	}

}
