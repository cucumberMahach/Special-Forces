package world.gui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import stages.World;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonType;

public class MapComplete extends Group{
	
	private Image background;
	private Button continueBtn;
	private Label areasLab, timeLab;
	private World world;
	
	public MapComplete(World world, Loader loader){
		this.world = world;
		background = new Image(loader.getBackground("areaComplete"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		continueBtn = new Button(loader, ButtonType.DIALOG, "continue", null);
		continueBtn.setPosition(getOriginX() - continueBtn.getOriginX(), 20);
		continueBtn.addListener(new NextLevelEvent(world, this));
		
		areasLab = new Label(loader, "", Font.DEFAULT, Align.left, 334, 315, 0, 0);
		timeLab = new Label(loader, "", Font.DEFAULT, Align.left, 421, 204, 0, 0);
		
		addActor(background);
		addActor(continueBtn);
		addActor(areasLab);
		addActor(timeLab);
	}
	
	public void update(){
		areasLab.setCaption(String.format("areas completed: %d of %d", world.manager().getMapIndex()+1, world.manager().getMapsCount()));
		timeLab.setCaption(world.manager().getMapTime());
	}
	
	public void show(){
		setPosition(-getWidth(), SpecialForces.HEIGHT / 2 - getOriginY());
		MoveToAction action = new MoveToAction();
		action.setPosition(SpecialForces.WIDTH / 2 - getOriginX(), getY());
		action.setDuration(2);
		action.setInterpolation(new Interpolation.ExpOut(100, 2));
		addAction(action);
		update();
	}
	
	public void hide(){
		clearActions();
		setPosition(-getWidth(), SpecialForces.HEIGHT / 2 - getOriginY());
	}
}

class NextLevelEvent extends ClickListener{
	
	private World world;
	private MapComplete mapComplete;
	
	public NextLevelEvent(World world, MapComplete mapComplete){
		this.world = world;
		this.mapComplete = mapComplete;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		mapComplete.hide();
		world.manager().mapContinue();
	}
}