package engine.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface Tiled {
	public int getMapWidth();
	public int getMapHeight();
	public OrthographicCamera getOrtCam();
	public float getPlayerX();
	public float getPlayerY();
}
