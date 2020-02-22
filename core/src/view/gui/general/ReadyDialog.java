package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import view.Image;
import view.gui.Button;
import view.gui.ButtonType;

public class ReadyDialog extends Group{
	private Image background;
	private Button yesBtn, noBtn;
	private Missions parent;
	
	public ReadyDialog(Loader loader, Missions parent){
		this.parent = parent;
		background = new Image(loader.getBackground("ready"));
		setSize(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		setOrigin(Align.center);
		background.setPosition(getOriginX(), getOriginY(), Align.center);
		
		yesBtn = new Button(loader, ButtonType.DIALOG, "yes", null);
		noBtn = new Button(loader, ButtonType.DIALOG, "no", null);
		
		yesBtn.setPosition(background.getX() + 45, background.getY() + 35);
		noBtn.setPosition(background.getX() + 370, yesBtn.getY());
		
		yesBtn.addListener(new DialogEvent(this, true));
		noBtn.addListener(new DialogEvent(this, false));
		
		addActor(background);
		addActor(yesBtn);
		addActor(noBtn);
	}
	
	public void request(boolean value){
		setVisible(false);
		if (value)
			parent.startMission();
	}
	
	class DialogEvent extends ClickListener{
		private ReadyDialog parent;
		private boolean request;
		
		public DialogEvent(ReadyDialog parent, boolean request){
			this.parent = parent;
			this.request = request;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.request(request);
		}
	}
}
