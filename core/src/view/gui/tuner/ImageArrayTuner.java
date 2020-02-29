package view.gui.tuner;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.Loader;
import view.Image;

public class ImageArrayTuner extends Tuner implements Setable{
	private Image image;
	private Map<String, TextureRegion> textures;
	private String[] keys;
	
	public ImageArrayTuner(Loader loader, float x, float y, Map<String, TextureRegion> array){
		super(loader, x, y);
		this.textures = array;
		keys = new String[array.size()];
		keys = array.keySet().toArray(keys);
		
		image = new Image(getTexture(0), getRight() + 85, getOriginY(), true);
		
		setWidth(image.getRight());
		setSetable(this);
		setBounds(0, textures.size() - 1);
		setFastAdjustment(false);
		addActor(image);
	}
	
	public ImageArrayTuner(Loader loader, HashMap<String, TextureRegion> array) {
		this(loader, 0, 0, array);
	}
	
	private TextureRegion getTexture(int index){
		return textures.get(keys[index]);
	}
	
	private void setImageIndex(int index){
		image.setTexture(getTexture(index));
	}

	@Override
	public void setValue(int value) {
		setImageIndex(value);
	}
	
	public String getArrayKey(){
		return keys[getValue()];
	}

	public void setNameValue(String name) {
		for (int i = 0; i < keys.length; i++){
			if (keys[i].equals(name)){
				setStatValue(i);
				return;
			}
		}
	}
}