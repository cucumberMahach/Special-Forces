package world.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.Loader;
import engine.Style;
import view.Actor;

public class Tile extends Actor{
	
	private TextureRegion texture;
	private OrthographicCamera camera;
	private boolean inView;
	
	public Tile(Loader loader, char tile, int x, int y, OrthographicCamera camera){
		this.camera = camera;
		if ((int) tile != 20){
			texture = loader.getMapTile((int) tile);
			setTexture(texture);
		}else{
			setTexture(null);
			setSize(Style.TILE_SIZE, Style.TILE_SIZE);
		}
		setPosition(x * Style.TILE_SIZE, y * Style.TILE_SIZE);
	}
	
	public void setTexture(TextureRegion texture){
		this.texture = texture;
	}
	
	@Override
	public void act(float delta) {
		final float x = camera.position.x - (camera.viewportWidth/2) * camera.zoom;
		final float y = camera.position.y - (camera.viewportHeight/2) * camera.zoom;
		final float w = camera.viewportWidth * camera.zoom;
		final float h = camera.viewportHeight * camera.zoom;
		inView = getX() + Style.TILE_SIZE > x && getX() < x + w && getY() + Style.TILE_SIZE > y && getY() < y + h;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!inView || texture == null)
			return;
		batch.draw(texture, getX(), getY(), Style.TILE_SIZE, Style.TILE_SIZE);
	}

}
