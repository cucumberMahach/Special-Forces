package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.Loader;
import engine.SpecialForces;
import stages.BackgroundStage;
import view.Image;
import view.gui.Button;
import view.gui.ButtonType;

public class HelpScreen implements Screen{
	
	private Stage stage, bgrStage;
	private Image caption, text;
	private Button backBtn;
	
	
	public HelpScreen(Loader loader, SpriteBatch batch, BackgroundStage bgrStage) {
		this.bgrStage = bgrStage;
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		stage = new Stage(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch);
		
		caption = new Image(loader.getCaption("help"));
		caption.setPosition(SpecialForces.WIDTH / 2 - caption.getWidth() / 2, 597);
		text = new Image(SpecialForces.getInstance().isAndroid() ? loader.getOther("gameHelp_android") : loader.getOther("gameHelp_pc"));
		backBtn = new Button(loader, ButtonType.MENU_TEXT, "back", null);
		backBtn.setPosition(867, 30);
		backBtn.addListener(new BackEvent());
		
		stage.addActor(caption);
		stage.addActor(text);
		stage.addActor(backBtn);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
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
	
	class BackEvent extends ClickListener{
		@Override
		public void clicked(InputEvent event, float x, float y) {
			SpecialForces.getInstance().screenManager().show(ScreenType.MENU);
		}
	}
}

