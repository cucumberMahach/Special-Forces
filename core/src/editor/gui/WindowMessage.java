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
import view.gui.ButtonType;

public class WindowMessage extends Group{
	private Editor editor;
	private Image background;
	private Label captionLab;
	private Button okBtn;
	
	public WindowMessage(Editor editor, Loader loader){
		this.editor = editor;
		background = new Image(loader.getBackground("weaponStat"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		captionLab = new Label(loader, "", Font.DEFAULT, Align.center, 0, 0, 0, 0);
		captionLab.setPosition(getOriginX(), getOriginY() + 100, Align.center);
		
		okBtn = new Button(loader, ButtonType.BUY, "ok", null);
		okBtn.setPosition(getOriginX(), 100, Align.center);
		okBtn.addListener(new OkEvent(this));
		
		addActor(background);
		addActor(captionLab);
		addActor(okBtn);
		
		//hide();
	}
	
	public void show(String message){
		captionLab.setCaption(message);
		setVisible(true);
		editor.clearControllers();
	}
	
	public void hide(){
		setVisible(false);
	}
	
	class OkEvent extends ClickListener{
		private WindowMessage parent;
		
		public OkEvent(WindowMessage parent) {
			this.parent = parent;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.hide();
		}
	}
}
