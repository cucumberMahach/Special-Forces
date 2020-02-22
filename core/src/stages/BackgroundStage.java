package stages;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import engine.Loader;
import view.Image;

public class BackgroundStage extends Stage{
	private Loader loader;
	private Image background, shadow, map;
	
	public BackgroundStage(Viewport viewport, Batch batch, Loader loader){
		super(viewport, batch);
		this.loader = loader;
		background = new Image(loader.getBackground("dynamic"));
		background.setPosition(getWidth()/2 - background.getOriginX(), getHeight()/2 - background.getOriginY());
		shadow = new Image(loader.getOther("shadow"));
		map = new Image(null);
		
		addActor(background);
		addActor(map);
		addActor(shadow);
	}
	
	public void showMap(int index){
		map.setTexture(loader.getBackground("map".concat(String.valueOf(index))));
	}
	
	public void hideMap(){
		map.setTexture(null);
	}
	
	private float angle;
	
	@Override
	public void act(float delta) {
		background.moveBy(MathUtils.cos(angle/10)*0.84f, MathUtils.cos(angle/3)*0.6f);
		angle += 0.017f;
	}
}
