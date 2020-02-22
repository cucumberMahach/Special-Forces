package world;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import stages.World;
import view.Group;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import world.gui.CloseDialog;
import world.gui.MapComplete;
import world.gui.MissionComplete;

public class Gui extends Group{
	private Button menuBtn;
	private MapComplete mapComplete;
	private MissionComplete missionComplete;
	private CloseDialog closeDialog;
	
	public Gui(World world, Loader loader){
		menuBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		menuBtn.setPosition(10, SpecialForces.HEIGHT - menuBtn.getHeight() - 10);
		menuBtn.addListener(new MenuEvent(world));
		
		mapComplete = new MapComplete(world, loader);
		mapComplete.setPosition(-mapComplete.getWidth(), SpecialForces.HEIGHT / 2 - mapComplete.getOriginY());
		
		missionComplete = new MissionComplete(world, loader);
		missionComplete.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		missionComplete.hide();
		
		closeDialog = new CloseDialog(world, loader);
		closeDialog.setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
		closeDialog.setVisible(false);
		
		addActor(menuBtn);
		addActor(mapComplete);
		addActor(missionComplete);
		addActor(closeDialog);
	}
	
	public void showMapComplete(){
		mapComplete.show();
	}
	
	public void showMissionComplete(){
		missionComplete.show();
	}
	
	public void showCloseDialog(){
		closeDialog.show();
	}

}

class MenuEvent extends ClickListener{
	private World world;
	
	public MenuEvent(World world) {
		this.world = world;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		world.gui().showCloseDialog();
	}
}