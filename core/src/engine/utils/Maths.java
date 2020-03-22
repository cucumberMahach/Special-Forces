package engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import engine.SpecialForces;
import engine.Style;

public class Maths {
	private static Tiled tiled;
	private static Vector2 tmpVector = new Vector2();

	public static boolean RandomChance(int percent){
		float value = percent / 100f;
		return MathUtils.random() > (1 - value);
	}

	public static float FPSCompensation(){
		return (float) SpecialForces.FPS / Gdx.graphics.getFramesPerSecond();
	}
	
	public static float calcDegrees(float dx, float dy){
		return radiansToDegrees(MathUtils.atan2(dy, dx));
	}
	
	public static void calcDirection(float degrees, Point p){
		final float rad = degreesToRadians(degrees);
		p.set((float) MathUtils.cos(rad), (float) MathUtils.sin(rad));
	}
	
	public static float len2(float x1, float y1, float x2, float y2){
		final float x = x2 - x1;
		final float y = y2 - y1;
		return x * x + y * y;
	}
	
	public static float len(float x1, float y1, float x2, float y2){
		return (float) Math.sqrt(len2(x1, y1, x2, y2));
	}

	public static float len2(Point p1, Point p2){
		final float x = p2.x - p1.x;
		final float y = p2.y - p1.y;
		return x * x + y * y;
	}
	
	public static float len(Point p1, Point p2){
		return (float) Math.sqrt(len2(p1, p2));
	}
	
	public static float fromMapX(int x){
		return x * Style.TILE_SIZE;
	}
	
	public static float fromMapY(int y){
		return (tiled.getMapHeight() - y - 1) * Style.TILE_SIZE;
	}
	
	public static float flipMap(float y){
		return tiled.getMapHeight() * Style.TILE_SIZE - y;
	}
	
	public static int toMapX(float x){
		return toMapCoords(x);
	}
	
	public static int toMapY(float y){
		return toMapCoords(tiled.getMapHeight() * Style.TILE_SIZE - y);
	}
	
	public static int toMapCoords(float value){
		return (int) (value / Style.TILE_SIZE);
	}
	
	public static void mouseToWorld(float x, float y, Point point){
		point.x = x * tiled.getOrtCam().zoom + getCameraX();
		point.y = y * tiled.getOrtCam().zoom + getCameraY();
	}
	
	public static void mouseToPlayer(float x, float y, Point point){
		point.x = x * tiled.getOrtCam().zoom - tiled.getPlayerX() + getCameraX();
		point.y = y * tiled.getOrtCam().zoom - tiled.getPlayerY() + getCameraY();
	}
	
	public static void mouseToWorldYTop(float x, float y, Point point){
		//System.out.printf("Zoom: %f CamX: %f CamY: %f H-y:%f\n", tiled.getOrtCam().zoom, getCameraX(), getCameraY(), (Gdx.graphics.getHeight() - y));

		x /= SpecialForces.getInstance().getPpuX();
		y /= SpecialForces.getInstance().getPpuY();
		point.x = x  * tiled.getOrtCam().zoom + getCameraX();
		point.y = (SpecialForces.HEIGHT - y) * tiled.getOrtCam().zoom + getCameraY();

	}

	private static float fitPpuX(){
		return (1 + (1 - SpecialForces.getInstance().getPpuX()));
	}

	public static void mouseToPlayerYTop(float x, float y, Point point){
		x /= SpecialForces.getInstance().getPpuX();
		y /= SpecialForces.getInstance().getPpuY();
		point.x = x * tiled.getOrtCam().zoom - tiled.getPlayerX() + getCameraX();
		point.y = (SpecialForces.HEIGHT - y) * tiled.getOrtCam().zoom - tiled.getPlayerY() + getCameraY();
	}
	
	public static float degreesToRadians(float degrees){
		return (degrees + 90) * MathUtils.degreesToRadians;
	}
	
	public static float radiansToDegrees(float radians){
		return radians * MathUtils.radiansToDegrees - 90;
	}
	
	private static float getCameraX(){
		return tiled.getOrtCam().position.x - (SpecialForces.WIDTH/2) * tiled.getOrtCam().zoom;
	}
	
	private static float getCameraY(){
		return tiled.getOrtCam().position.y - (SpecialForces.HEIGHT/2) * tiled.getOrtCam().zoom;
	}
	
	public static Vector2 getTmpVector(){
		return tmpVector;
	}
	
	public static void setWorld(Tiled t){
		tiled = t;
	}
	
}
