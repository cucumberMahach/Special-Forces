package view.gui.tuner;

import engine.Loader;
import view.Image;

public class WeaponTuner extends Tuner implements Setable{
	private Image image;
	private Loader loader;
	
	public WeaponTuner(Loader loader, float x, float y){
		super(loader, x, y);
		this.loader = loader;
		
		image = new Image(loader.getWeaponIcon(0));
		image.setPosition(getRight() + 10, 15);
		
		setWidth(image.getRight());
		setSetable(this);
		setBounds(0, loader.getWeaponsCount() - 1);
		setFastAdjustment(false);
		addActor(image);
	}
	
	public WeaponTuner(Loader loader) {
		this(loader, 0, 0);
	}
	
	private void setWeapon(int index){
		image.setTexture(loader.getWeaponIcon(index));
	}

	@Override
	public void setValue(int value) {
		setWeapon(value);
	}
}