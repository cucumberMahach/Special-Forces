package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import engine.Loader;
import engine.SpecialForces;
import stages.Editor;

public class EditorScreen implements Screen{
	
	private Editor editor;
	private InputMultiplexer multiplexer;
	
	public EditorScreen(Loader loader, SpriteBatch batch) {
		OrthographicCamera camera = new OrthographicCamera(SpecialForces.WIDTH, SpecialForces.HEIGHT);
		multiplexer = new InputMultiplexer();
		
		editor = new Editor(new StretchViewport(SpecialForces.WIDTH, SpecialForces.HEIGHT, camera), batch, loader, multiplexer);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
		editor.manager().show();
		SpecialForces.getInstance().sounds().stopAllMusic();
	}

	@Override
	public void render(float delta) {
		editor.act(delta);
		editor.draw();
	}

	@Override
	public void resize(int width, int height) {
		editor.getViewport().setScreenSize(width, height);
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
		editor.manager().hide();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		editor.dispose();
	}

}
