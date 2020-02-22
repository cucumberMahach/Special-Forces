package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.Loader;
import engine.SpecialForces;
import engine.utils.timer.Timer;
import engine.utils.timer.TimerEvent;
import view.Image;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;

public class LoadScreen implements Screen{

	private final float ALPHA_GRADIENT_DURATION = 3;

	private Loader loader;
	private Stage stage;
	private Image background;
	private ProgressBar loadBar;

	private Timer timerHide = new Timer();
	private MoveToAction moveToAction = new MoveToAction();
	
	public LoadScreen(Loader loader, SpriteBatch batch) {
		this.loader = loader;
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		stage = new Stage(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch);
		background = new Image(loader.getBackground("load"));
		loadBar = new ProgressBar(loader, ProgressBarStyle.LOAD);
		loadBar.setPosition(SpecialForces.WIDTH / 2 - loadBar.getWidth() / 2,-loadBar.getHeight() - 300);

		moveToAction.setPosition(SpecialForces.WIDTH / 2 - loadBar.getWidth() / 2, 80);
		moveToAction.setInterpolation(Interpolation.pow3Out);
		moveToAction.setDuration(3);
		loadBar.addAction(moveToAction);

		timerHide.setInterval(1.0f);
        timerHide.setRepeat(false);

		timerHide.setEvent(new TimerEvent() {
			@Override
			public void event() {

				SpecialForces.getInstance().screenManager().show(ScreenType.MENU);
			}
		});
		
		stage.addActor(background);
		stage.addActor(loadBar);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	private boolean loadingDone = false;
	private float alphaTime = 0;

	@Override
	public void render(float delta) {
		if (alphaTime < ALPHA_GRADIENT_DURATION) {
			alphaTime += delta;
			background.setColor(1,1,1,alphaTime / ALPHA_GRADIENT_DURATION * 1f);
		}else{
			background.setColor(1,1,1,1);
		}

		if (loader.getAssetManager().update() && !loadingDone){
			loadingDone = true;
			SpecialForces.getInstance().loadingDone();
			stage.draw();
			timerHide.start();
		}
		loadBar.setProgress(loader.getAssetManager().getProgress());
		stage.act(delta);
		timerHide.update(delta);
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
