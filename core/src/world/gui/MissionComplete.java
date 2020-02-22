package world.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import stages.World;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonType;
import world.gameplay.Mission;

public class MissionComplete extends Group{
	private Loader loader;
	private Image background, medalImg;
	private Button continueBtn;
	private Label dollarsLab, expLab, timeLab, killsLab;
	private World world;
	
	public MissionComplete(World world, Loader loader){
		this.world = world;
		this.loader = loader;
		background = new Image(loader.getBackground("missionComplete"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		continueBtn = new Button(loader, ButtonType.MENU_TEXT, "continue", null);
		continueBtn.setPosition(425, 41);
		continueBtn.addListener(new CompletedEvent(world, this));
		
		dollarsLab = new Label(loader, "", Font.DEFAULT, Align.left, 622, 350, 0, 0);
		expLab = new Label(loader, "", Font.EXPERIENCE_BIG, Align.left, dollarsLab.getX(), 250, 0, 0);
		timeLab = new Label(loader, "", Font.DEFAULT, Align.left, 208, 163, 0, 0);
		killsLab = new Label(loader, "", Font.DEFAULT, Align.left, timeLab.getX(), 60, 0, 0);
		
		medalImg = new Image(null, 69, 269);
		
		addActor(background);
		addActor(continueBtn);
		addActor(dollarsLab);
		addActor(expLab);
		addActor(timeLab);
		addActor(killsLab);
		addActor(medalImg);
	}
	
	public void update(){
		Mission m = world.manager().getMission();
		dollarsLab.setCaption(String.valueOf(m.getDollarsReward()));
		expLab.setCaption(String.valueOf(m.getExperienceReward()));
		timeLab.setCaption(world.manager().getMissionTime());
		int deaths = world.manager().getDeaths();
		killsLab.setCaption(String.valueOf(deaths));
		medalImg.setTexture(loader.getIcon("medal" + (deaths > 0 ? deaths > 4 ? 2 : 1 : 0)));
	}
	
	public void show(){
		setVisible(true);
		update();
	}
	
	public void hide(){
		setVisible(false);
	}
}

class CompletedEvent extends ClickListener{
	
	private World world;
	private MissionComplete missionComplete;
	
	public CompletedEvent(World world, MissionComplete missionComplete){
		this.world = world;
		this.missionComplete = missionComplete;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		missionComplete.hide();
		world.manager().exit();
	}
}