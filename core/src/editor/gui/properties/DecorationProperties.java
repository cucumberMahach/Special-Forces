package editor.gui.properties;

import com.badlogic.gdx.utils.Align;

import editor.objects.EditorDecoration;
import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Label;
import view.gui.tuner.ImageArrayTuner;
import view.gui.tuner.NumericTuner;
import view.gui.tuner.TunerListener;

public class DecorationProperties extends Group{
	private Editor editor;

	private Label typeLab, healthLab;
	private ImageArrayTuner typeTuner;
	private NumericTuner healthTuner;
	
	
	public DecorationProperties(Editor editor, Loader loader){
		this.editor = editor;
		setSize(350, 450);
		
		typeLab = new Label(loader, "type", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		typeLab.setPosition(10, getHeight() - typeLab.getHeight() - 10);
		typeTuner = new ImageArrayTuner(loader, loader.getObject());
		typeTuner.setPosition(typeLab.getX(), typeLab.getY() - typeTuner.getHeight() - 10);
		typeTuner.addListener(new DecorationTypeEvent(this));
		
		healthLab = new Label(loader, "health", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		healthLab.setPosition(typeLab.getX(), typeTuner.getY() - healthLab.getHeight() - 10);
		healthTuner = new NumericTuner(loader);
		healthTuner.setPosition(healthLab.getX(), healthLab.getY() - healthTuner.getHeight() - 10);
		healthTuner.addListener(new DecorationHealthEvent(this));
		healthTuner.setMinBound(0);
		

		addActor(typeLab);
		addActor(typeTuner);
		addActor(healthLab);
		addActor(healthTuner);
	}
	
	public void healthChanged(int value){
		((EditorDecoration) editor.commands().getSelectObject()).setHealth(value);
	}
	
	public void typeChanged(int value){
		((EditorDecoration) editor.commands().getSelectObject()).setName(typeTuner.getArrayKey());
	}
	
	public void setNameValue(String name){
		typeTuner.setNameValue(name);
	}
	
	public void setHealthValue(int health){
		healthTuner.setStatValue(health);
	}
}

class DecorationTypeEvent implements TunerListener{
	private DecorationProperties properties;
	
	public DecorationTypeEvent(DecorationProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.typeChanged(value);
	}
	
}

class DecorationHealthEvent implements TunerListener{
	private DecorationProperties properties;
	
	public DecorationHealthEvent(DecorationProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.healthChanged(value);
	}
	
}