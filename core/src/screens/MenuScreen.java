package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.CursorType;
import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import stages.BackgroundStage;
import view.Font;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import view.Image;

public class MenuScreen implements Screen{
	
	private Loader loader;
	private Stage stage, bgrStage;
	private Image caption, sold1, sold2, sold3, sold4;
	private Button playBtn, editorBtn, musicBtn, helpBtn, exitBtn, fullscreenBtn, codeBtn;
	private Label sendMapLab;
	private boolean showFirstly = true;
	private static final float BUTTON_SPACE = 20;
	
	public MenuScreen(Loader loader, SpriteBatch batch, BackgroundStage bgrStage) {
		this.bgrStage = bgrStage;
		this.loader = loader;
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		StretchViewport viewport = new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera);
		stage = new Stage(viewport, batch);
		caption = new Image(loader.getCaption("title"));
		caption.setPosition(SpecialForces.WIDTH / 2 - caption.getWidth() / 2, 470);
		sold1 = new Image(loader.getOther("soldier1"), -30 - 700, -266);
		sold2 = new Image(loader.getOther("soldier2"), 100 - 1000, -266);
		sold3 = new Image(loader.getOther("soldier3"), 873 + 700, -320);
		sold4 = new Image(loader.getOther("soldier4"), 1011 + 1000, -320);
		playBtn = new Button(loader, ButtonType.MENU_TEXT, "play", null);
		playBtn.setPosition(SpecialForces.WIDTH / 2 - playBtn.getWidth() / 2, 350);//350
		playBtn.addListener(new ScreenEvent(ScreenType.GENERAL));
		
		editorBtn = new Button(loader, ButtonType.MENU_TEXT, "map editor", null);
		editorBtn.setPosition(playBtn.getX(), playBtn.getY() - editorBtn.getHeight() - BUTTON_SPACE);
		editorBtn.addListener(new ScreenEvent(ScreenType.EDITOR));
		
		musicBtn = new Button(loader, ButtonType.MENU_ICON, "", ButtonIcon.MUSIC);
		musicBtn.setIconScale(1.7f);
		musicBtn.setPosition(editorBtn.getX(), editorBtn.getY() - musicBtn.getHeight() - BUTTON_SPACE);
		musicBtn.addListener(new ToggleMusicEvent(musicBtn, loader));

		exitBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		exitBtn.setPosition(1198,641);
		exitBtn.addListener(new MenuScreen.ExitEvent());

		fullscreenBtn = new Button(loader, ButtonType.MENU_ICON, "", ButtonIcon.FULLSCREEN);
		fullscreenBtn.setPosition(589, musicBtn.getY());
		fullscreenBtn.addListener(new FullScreenEvent());

		helpBtn = new Button(loader, ButtonType.MENU_ICON, "", ButtonIcon.HELP);
		helpBtn.setPosition(735, fullscreenBtn.getY());
		helpBtn.addListener(new HelpEvent());

		codeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CODE);
		codeBtn.setPosition(82 - codeBtn.getWidth(), 641);
		codeBtn.addListener(new EnterCodeEvent());

		stage.addActor(caption);
		stage.addActor(sold2);
		stage.addActor(sold1);
		stage.addActor(sold3);
		stage.addActor(sold4);
		stage.addActor(playBtn);
		stage.addActor(editorBtn);
		stage.addActor(musicBtn);
		stage.addActor(helpBtn);
		stage.addActor(exitBtn);
		stage.addActor(codeBtn);

		if (!SpecialForces.getInstance().isAndroid()) {
			stage.addActor(fullscreenBtn);
		}else {
			musicBtn.setX(513);
			helpBtn.setX(666);
		}

		MoveToAction moveToAction = new MoveToAction();
		moveToAction.setPosition(-30, -266);
		moveToAction.setInterpolation(Interpolation.pow3Out);
		moveToAction.setDuration(3f);
		sold1.addAction(moveToAction);

		moveToAction = new MoveToAction();
		moveToAction.setPosition(100, -266);
		moveToAction.setInterpolation(Interpolation.pow3Out);
		moveToAction.setDuration(3f);
		sold2.addAction(moveToAction);

		moveToAction = new MoveToAction();
		moveToAction.setPosition(873, -320);
		moveToAction.setInterpolation(Interpolation.pow3Out);
		moveToAction.setDuration(3f);
		sold3.addAction(moveToAction);

		moveToAction = new MoveToAction();
		moveToAction.setPosition(1011, -320);
		moveToAction.setInterpolation(Interpolation.pow3Out);
		moveToAction.setDuration(3f);
		sold4.addAction(moveToAction);

	}
	
	private void updateGui(){
		musicBtn.setIcon(SpecialForces.getInstance().sounds().isMusicEnabled() ? loader.getIcon("black_music") : loader.getIcon("black_music_off"));
		if (!showFirstly){
			sold1.setPosition(-30, -266);
			sold2.setPosition(100, -266);
			sold3.setPosition(873, -320);
			sold4.setPosition(1011, -320);
			sold1.clearActions();
			sold2.clearActions();
			sold3.clearActions();
			sold4.clearActions();

		}
	}

	@Override
	public void show() {
		updateGui();
		Gdx.input.setInputProcessor(stage);
		SpecialForces.getInstance().cursors().setCursor(CursorType.ARROW);
		SpecialForces.getInstance().sounds().playMusic("menu", true);
		if (showFirstly)
			showFirstly = false;
	}

	@Override
	public void render(float delta) {
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
	
	class ToggleMusicEvent extends ClickListener{
		
		private Button btn;
		private Loader loader;
		
		public ToggleMusicEvent(Button btn, Loader loader){
			this.btn = btn;
			this.loader = loader;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().sounds().toggleMusic();
			btn.setIcon(SpecialForces.getInstance().sounds().isMusicEnabled() ? loader.getIcon("black_music") : loader.getIcon("black_music_off"));
		}
	}
	
	class ScreenEvent extends ClickListener{
		private ScreenType screen;
		
		public ScreenEvent(ScreenType screen){
			this.screen = screen;
		}
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().screenManager().show(screen);
		}
	}

	class HelpEvent extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().screenManager().show(ScreenType.HELP);
		}
	}

	class ExitEvent extends ClickListener{

		@Override
		public void clicked(InputEvent event, float x, float y) {
			Gdx.app.exit();
		}
	}

	class FullScreenEvent extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (Gdx.graphics.isFullscreen()){
				Gdx.graphics.setWindowedMode(SpecialForces.WIDTH, SpecialForces.HEIGHT);
			}else {
				Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
				Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
				Gdx.graphics.setFullscreenMode(displayMode);
			}
			SpecialForces.getInstance().screenManager().show(ScreenType.MENU);
		}
	}

	class EnterCodeEvent extends ClickListener{

		private boolean closed = true;

		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (!closed)
				return;

			EnterCodeInputListener listener = new EnterCodeInputListener(this);
			Gdx.input.getTextInput(listener, "Enter cheat code", "", "");

			closed = false;

			super.clicked(event, x, y);
		}

		public void closed(){
			closed = true;
		}
	}

	public class EnterCodeInputListener implements Input.TextInputListener {

		private EnterCodeEvent event;

		public EnterCodeInputListener(EnterCodeEvent event){
			this.event = event;
		}

		@Override
		public void input (String text) {
			event.closed();
			try {
				String word = text.substring(0, 5).toLowerCase(); //money500

				if (word.equals("money")) {
					String number = text.substring(5);
					int money = Integer.parseInt(number);

					PlayerData pd = SpecialForces.getInstance().playerData();
					pd.changeDollars(money);
					pd.save();

					SpecialForces.getInstance().sounds().buy();
				}
			}catch (Exception ex){};
		}

		@Override
		public void canceled () {
			event.closed();
		}
	}

}

