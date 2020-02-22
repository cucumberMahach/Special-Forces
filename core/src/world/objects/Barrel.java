package world.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import engine.Loader;

import engine.Style;
import world.gameplay.Shoot;

public class Barrel extends MapObject{
	private TextureRegion texture;
	private boolean exploded;
	
	private float health;
	
	public Barrel(Loader loader, float x, float y, int type){
		texture = getTexture(loader, type);
		
		setPosition(x, y);
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
		setOrigin(Align.center);
		health = 20;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		checkHealth();
	}
	
	private void checkHealth(){
		if (health <= 0)
			explode();
	}
	
	private void explode(){
		if (exploded)
			return;
		exploded = true;
		getWorld().explode(getCenterX(), getCenterY(), Style.EXPLOSION_RADIUS, 100);
		removeGlobal();
	}

	@Override
	public void getShot(Shoot shoot) {
		
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		health -= isExplosion ? health : damage;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isInView())
			batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	public static TextureRegion getTexture(Loader loader, int type){
		switch (type) {
		case 0:
			return loader.getObject("barrelStand");
		case 1:
			return loader.getObject("barrelFall");
		default:
			return loader.getObject("boxTnt");
		}
	}
	
}
