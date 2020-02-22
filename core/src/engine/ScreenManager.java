package engine;

import java.util.HashMap;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import screens.EditorScreen;
import screens.GameScreen;
import screens.GeneralScreen;
import screens.HelpScreen;
import screens.LoadScreen;
import screens.MenuScreen;
import screens.PrepareScreen;
import screens.ScreenType;
import stages.BackgroundStage;
import world.gameplay.MissionConfig;

public class ScreenManager {
	
	private Loader loader;
	private SpriteBatch batch;
	private BackgroundStage bgrStage;
	private HashMap<ScreenType, Screen> screens;
	
	public ScreenManager(Loader loader, SpriteBatch batch){
		screens = new HashMap<ScreenType, Screen>();
		this.loader = loader;
		this.batch = batch;
	}
	
	public void loadPrev(){
		screens.put(ScreenType.LOAD, new LoadScreen(loader, batch));
	}
	
	public void load(){
		bgrStage = new BackgroundStage(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT), batch, loader);
		screens.put(ScreenType.MENU, new MenuScreen(loader, batch, bgrStage));
		screens.put(ScreenType.HELP, new HelpScreen(loader, batch, bgrStage));
		screens.put(ScreenType.GENERAL, new GeneralScreen(loader, batch, bgrStage));
		screens.put(ScreenType.GAME, new GameScreen(loader, batch));
		screens.put(ScreenType.PREPARE, new PrepareScreen(loader, batch, bgrStage));
		screens.put(ScreenType.EDITOR, new EditorScreen(loader, batch));
	}
	
	public void show(ScreenType screen){
		Screen scr = screens.get(screen);
		setScreen(scr);
	}
	
	public void startTest(String map, PlayerData playerData){
		GameScreen gameScreen = ((GameScreen) (screens.get(ScreenType.GAME)));
		gameScreen.setMap(map, playerData);
		setScreen(gameScreen);
	}
	
	public void startMission(MissionConfig missionCfg, PlayerData playerData){
		PrepareScreen prepareScreen = ((PrepareScreen) (screens.get(ScreenType.PREPARE)));
		prepareScreen.setMission(missionCfg, playerData);
		setScreen(prepareScreen);
	}
	
	public void startGame(MissionConfig missionCfg, PlayerData playerData){
		GameScreen gameScreen = ((GameScreen) (screens.get(ScreenType.GAME)));
		gameScreen.setMission(missionCfg, playerData);
		setScreen(gameScreen);
	}
	
	private void setScreen(Screen screen){
		SpecialForces.getInstance().setScreen(screen);
	}
}
