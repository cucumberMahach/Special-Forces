package editor.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import view.gui.tuner.NumericTuner;
import view.gui.tuner.TileTuner;

public class WindowNewMap extends Group{
	private Editor editor;
	private Image background;
	private Label captionLab, widthLab, heightLab, tileLab;
	private NumericTuner widthTuner, heightTuner;
	private TileTuner tileTuner;
	private Button closeBtn, createBtn;
	
	public WindowNewMap(Editor editor, Loader loader){
		this.editor = editor;
		background = new Image(loader.getBackground("weaponStat"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		captionLab = new Label(loader, "create new map", Font.DEFAULT, Align.left, 0, 0, getWidth(), 30);
		captionLab.setPosition(20, getHeight() - captionLab.getHeight() - 20);
		closeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		closeBtn.setPosition(getWidth() - closeBtn.getWidth() - 10, getHeight() - closeBtn.getHeight() - 10);
		closeBtn.addListener(new CloseEvent(this));
		
		widthTuner = new NumericTuner(loader);
		widthTuner.setPosition(getOriginX(), getOriginY() + 90, Align.center);
		widthTuner.setBounds(10, 200);
		widthTuner.setValue(20);
		heightTuner = new NumericTuner(loader);
		heightTuner.setPosition(widthTuner.getX(), widthTuner.getY() - heightTuner.getHeight() - 10);
		heightTuner.setBounds(10, 200);
		heightTuner.setValue(20);
		tileTuner = new TileTuner(loader);
		tileTuner.setPosition(heightTuner.getX(), heightTuner.getY() - heightTuner.getHeight() - 10);
		tileTuner.setBounds(0, loader.getMapTiles().length-1);
		
		widthLab = new Label(loader, "width:", Font.SMALL, Align.right, widthTuner.getX() - 10, widthTuner.getY() + widthTuner.getOriginY() - 15, 0, 0);
		heightLab = new Label(loader, "height:", Font.SMALL, Align.right, heightTuner.getX() - 10, heightTuner.getY() + heightTuner.getOriginY() - 15, 0, 0);
		tileLab = new Label(loader, "tile:", Font.SMALL, Align.right, tileTuner.getX() - 10, tileTuner.getY() + tileTuner.getOriginY() - 15, 0, 0);
		
		createBtn = new Button(loader, ButtonType.BUY, "create", null);
		createBtn.setPosition(getOriginX(), 50, Align.center);
		createBtn.addListener(new CreateEvent(this));
		
		addActor(background);
		addActor(captionLab);
		addActor(closeBtn);
		addActor(widthTuner);
		addActor(heightTuner);
		addActor(tileTuner);
		addActor(widthLab);
		addActor(heightLab);
		addActor(tileLab);
		addActor(createBtn);
		
		setVisible(false);
	}
	
	public void create() {
		setVisible(false);
		editor.manager().newMap(widthTuner.getValue(), heightTuner.getValue(), tileTuner.getValue());
	}
	
	public void show(){
		setVisible(true);
		editor.clearControllers();
	}
	
	class CloseEvent extends ClickListener{
		private WindowNewMap parent;
		
		public CloseEvent(WindowNewMap parent){
			this.parent = parent;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.setVisible(false);
		}
	}
	
	class CreateEvent extends ClickListener{
		private WindowNewMap parent;
		
		public CreateEvent(WindowNewMap parent){
			this.parent = parent;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.create();
		}
	}
}