package view.gui.tuner;

import engine.Loader;
import view.Image;

public class TileTuner extends Tuner implements Setable{
	private Image image;
	private Loader loader;
	
	public TileTuner(Loader loader, float x, float y){
		super(loader, x, y);
		this.loader = loader;
		
		image = new Image(loader.getMapTile(0));
		image.setPosition(getRight() + 10, 0);
		
		setWidth(image.getRight());
		setSetable(this);
		setBounds(0, loader.getMapTiles().length - 1);
		setFastAdjustment(false);
		addActor(image);
	}
	
	public TileTuner(Loader loader) {
		this(loader, 0, 0);
	}
	
	private void setTile(int index){
		image.setTexture(loader.getMapTile(index));
	}

	@Override
	public void setValue(int value) {
		setTile(value);
	}
}