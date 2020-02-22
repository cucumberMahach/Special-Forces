package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import stages.BackgroundStage;
import view.Image;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;
import world.gameplay.MissionConfig;

public class PrepareScreen implements Screen{
	
	private Stage stage, bgrStage;
	private Image caption, sold1, sold2;
	private ProgressBar loadBar;
	
	private MissionConfig missionCfg;
	private PlayerData playerData;
	private float progress, genNum, genTime;
	
	public PrepareScreen(Loader loader, SpriteBatch batch, BackgroundStage bgrStage) {
		this.bgrStage = bgrStage;
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		stage = new Stage(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch);
		
		caption = new Image(loader.getCaption("title"));
		caption.setPosition(SpecialForces.WIDTH / 2 - caption.getWidth() / 2, 470);
		loadBar = new ProgressBar(loader, ProgressBarStyle.LOAD);
		loadBar.setPosition(SpecialForces.WIDTH / 2 - loadBar.getWidth() / 2, 145);
		
		sold1 = new Image(loader.getOther("soldier1"), 27, -181);
		sold2 = new Image(loader.getOther("soldier4"), 944, -236);
		
		stage.addActor(caption);
		stage.addActor(loadBar);
		stage.addActor(sold1);
		stage.addActor(sold2);
	}
	
	public void setMission(MissionConfig missionCfg, PlayerData playerData){
		this.missionCfg = missionCfg;
		this.playerData = playerData;
	}
	
	private void startGame(){
		SpecialForces.getInstance().screenManager().startGame(missionCfg, playerData);
	}
	
	public void updateProgress(){
		genTime -= 0.05;
		if (genTime <= 0) {
			genNum = MathUtils.random(0.05f);
			genTime = 1;
		}
		progress += genNum;
		loadBar.setProgress(progress);
		if (progress >= 1)
			startGame();
	}

	@Override
	public void show() {
		progress = 0;
		Gdx.input.setInputProcessor(stage);
		SpecialForces.getInstance().sounds().stopAllMusic();
	}

	@Override
	public void render(float delta) {
		updateProgress();
		bgrStage.act(delta);
		bgrStage.draw();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}


