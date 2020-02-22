package editor.gui.properties;

import com.badlogic.gdx.utils.Align;

import editor.objects.EditorItem;
import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Label;
import view.gui.tuner.NumericTuner;
import view.gui.tuner.TunerListener;

public class ItemProperties extends Group{
	private Editor editor;

	private Label ammoLab;
	private NumericTuner ammoTuner;
	
	public ItemProperties(Editor editor, Loader loader){
		this.editor = editor;
		setSize(350, 450);
		
		ammoLab = new Label(loader, "ammo", Font.SMALL, Align.left, 0, 0, getWidth(), 30);
		ammoLab.setPosition(10, getHeight() - ammoLab.getHeight() - 10);
		ammoTuner = new NumericTuner(loader);
		ammoTuner.setPosition(ammoLab.getX(), ammoLab.getY() - ammoTuner.getHeight() - 10);
		ammoTuner.addListener(new ItemAmmoEvent(this));
		ammoTuner.setMinBound(0);
		

		addActor(ammoLab);
		addActor(ammoTuner);
	}
	
	public void ammoChanged(int value){
		((EditorItem) editor.commands().getSelectObject()).setAmmo(value);
	}
	public void setAmmoValue(int value){
		ammoTuner.setStatValue(value);
	}

}

class ItemAmmoEvent implements TunerListener{
	private ItemProperties properties;
	
	public ItemAmmoEvent(ItemProperties properties){
		this.properties = properties;
	}

	@Override
	public void valueChanged(int value) {
		properties.ammoChanged(value);
	}
	
}