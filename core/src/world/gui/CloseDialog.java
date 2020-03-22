package world.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.CursorType;
import engine.Loader;
import engine.SpecialForces;
import stages.World;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonType;

public class CloseDialog extends Group{
	
	private Image background;
	private Button yesBtn, noBtn;
	private Label textLab;
	private World world;
	
	public CloseDialog(World world, Loader loader){
		this.world = world;
		background = new Image(loader.getBackground("weaponStat"));
		setSize(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		setOrigin(Align.center);
		background.setPosition(getOriginX(), getOriginY(), Align.center);
		
		textLab = new Label(loader, "do you really want\nto exit?", Font.DEFAULT, Align.center, getOriginX(), getOriginY() + 100, 0, 0);
		yesBtn = new Button(loader, ButtonType.BUY, "yes", null);
		noBtn = new Button(loader, ButtonType.BUY, "no", null);
		
		yesBtn.setPosition(background.getCenterX()-background.getOriginX()/2, background.getCenterY()-130, Align.center);
		noBtn.setPosition(background.getCenterX()+background.getOriginX()/2, yesBtn.getCenterY(), Align.center);
		
		yesBtn.addListener(new CloseEvent(this, true));
		noBtn.addListener(new CloseEvent(this, false));
		
		addActor(background);
		addActor(yesBtn);
		addActor(noBtn);
		addActor(textLab);
	}
	
	public void select(boolean action){
		world.setPause(false);
		if (action)
			world.manager().exit();
		else
			SpecialForces.getInstance().cursors().setCursor(CursorType.AIM);
		setVisible(false);
	}
	
	public void show(){
		if (world.manager().isMapCompleted())
			return;
		world.setPause(true);
		setVisible(true);
		SpecialForces.getInstance().cursors().setCursor(CursorType.ARROW);
	}
	
}

class CloseEvent extends ClickListener{
	private CloseDialog dialog;
	private boolean action;
	
	public CloseEvent(CloseDialog dialog, boolean action){
		this.dialog = dialog;
		this.action = action;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		dialog.select(action);
	}
}