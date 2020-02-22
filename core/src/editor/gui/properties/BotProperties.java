package editor.gui.properties;

import com.badlogic.gdx.utils.Align;

import editor.objects.EditorBot;
import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Label;
import view.gui.tuner.NumericTuner;
import view.gui.tuner.TunerListener;
import view.gui.tuner.WeaponTuner;
import world.gameplay.WeaponType;

public class BotProperties extends Group{
	private Editor editor;
	
	private Label healthLab;
	private NumericTuner healthTuner;
	
	private Label weaponLab;
	private WeaponTuner weaponTuner;
	
	private Label ammoLab;
	private NumericTuner ammoTuner;
	
	public BotProperties(Editor editor, Loader loader){
		this.editor = editor;
		setSize(350, 450);
		healthLab = new Label(loader, "health", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		healthLab.setPosition(10, getHeight() - healthLab.getHeight() - 10);
		healthTuner = new NumericTuner(loader);
		healthTuner.setPosition(healthLab.getX(), healthLab.getY() - healthTuner.getHeight() - 10);
		healthTuner.addListener(new HealthEvent(this));
		healthTuner.setBounds(0, 100);
		
		weaponLab = new Label(loader, "weapon", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		weaponLab.setPosition(healthLab.getX(), healthTuner.getY() - weaponLab.getHeight() - 10);
		weaponTuner = new WeaponTuner(loader);
		weaponTuner.setPosition(weaponLab.getX(), weaponLab.getY() - weaponTuner.getHeight() - 10);
		weaponTuner.addListener(new WeaponEvent(this));
		
		ammoLab = new Label(loader, "ammo", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		ammoLab.setPosition(healthLab.getX(), weaponTuner.getY() - ammoLab.getHeight() - 10);
		ammoTuner = new NumericTuner(loader);
		ammoTuner.setPosition(ammoLab.getX(), ammoLab.getY() - ammoTuner.getHeight() - 10);
		ammoTuner.addListener(new AmmoEvent(this));
		ammoTuner.setMinBound(0);
		
		
		addActor(healthLab);
		addActor(healthTuner);
		addActor(weaponLab);
		addActor(weaponTuner);
		addActor(ammoLab);
		addActor(ammoTuner);
	}
	
	public void ammoChanged(int value){
		((EditorBot) editor.commands().getSelectObject()).setAmmo(value);
	}
	
	public void weaponChanged(int value){
		((EditorBot) editor.commands().getSelectObject()).setWeaponType(WeaponType.values()[value]);
	}
	
	public void healthChanged(int value){
		((EditorBot) editor.commands().getSelectObject()).setHealth(value);
	}
	
	public void setAmmoValue(int value){
		ammoTuner.setStatValue(value);
	}
	
	public void setWeaponValue(int value){
		weaponTuner.setStatValue(value);
	}
	
	public void setHealthValue(int value){
		healthTuner.setStatValue(value);
	}
}

class AmmoEvent implements TunerListener{
	private BotProperties properties;
	
	public AmmoEvent(BotProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.ammoChanged(value);
	}
	
}

class WeaponEvent implements TunerListener{
	private BotProperties properties;
	
	public WeaponEvent(BotProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.weaponChanged(value);
	}
	
}

class HealthEvent implements TunerListener{
	private BotProperties properties;
	
	public HealthEvent(BotProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.healthChanged(value);
	}
	
}