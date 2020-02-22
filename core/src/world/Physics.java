package world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

import engine.Style;
import engine.utils.Maths;
import engine.utils.Motion;
import stages.World;
import world.gameplay.Shoot;
import world.map.Cell;
import world.map.TileType;
import world.objects.Item;
import world.objects.MapObject;
import world.objects.ObjectType;

public class Physics {
	private World world;
	
	private Motion tmpMotion;
	private BoundingBox tmpBox;
	private Vector3 tmpVect, minVect, maxVect;
	private Shoot tmpShoot1, tmpShoot2;
	private Ray tmpRay;
	
	public Physics(World world){
		this.world = world;
		tmpMotion = new Motion();
		tmpVect = new Vector3();
		tmpBox = new BoundingBox();
		minVect = tmpBox.min;
		maxVect = tmpBox.max;
		tmpShoot1 = new Shoot();
		tmpShoot2 = new Shoot();
		tmpRay = new Ray();
	}
	
	public boolean isObjectPrevent(float origX, float origY, float dx, float dy, MapObject obj){
		tmpRay.origin.set(origX, origY, 0);
		tmpRay.direction.set(dx, dy, 0);
		return intersectRayBounds(obj.getX(), obj.getY(), obj.getCollideWidth(), obj.getCollideHeight(), tmpRay, tmpVect);
	}
	
	public MapObject getRaycastObject(ObjectType type, float x, float y, float startAngle, float range, float viewDistance){
		for (float ang = -range / 2; ang < range / 2; ang += 0.3f){
			final MapObject obj;
			final float viewAng = startAngle + ang;
			obj = raycastLine(x, y, MathUtils.cos(viewAng), MathUtils.sin(viewAng), viewDistance, type);
			if (obj != null)
				return obj;
		}
		return null;
	}
	
	private MapObject raycastLine(float orgX, float orgY, float dx, float dy, float distance, ObjectType type){
		final Map map = world.map();
		orgY = flipMap(orgY);
		for (int i = 0; i < distance; i += 16){
			final int x = (int) ((orgX + i * dx) / Style.TILE_SIZE);
			final int y = (int) ((orgY + i * dy) / Style.TILE_SIZE);
			final Cell cell = map.get(x, y);
			if (cell.type == TileType.WALL)
				return null;
			if (cell.type == TileType.OBJECT){
				for(int k = 0; k < cell.objects.size; k++){
					final MapObject obj = cell.objects.get(k);
					if (obj.getType() == type)
						return obj;
				}
			}
		}
		return null;
	}
	
	public int getMaxRoomLen(float x, float y){
		int maxLen = 0;
		for (float ang = 0; ang < MathUtils.PI; ang += 0.1f){
			final int len;
			len = getLineLen(x, y, MathUtils.cos(ang), MathUtils.sin(ang)) + getLineLen(x, y, MathUtils.cos(MathUtils.PI+ang), MathUtils.sin(MathUtils.PI+ang));
			if (len > maxLen)
				maxLen = len;
		}
		return maxLen;
	}
	
	private int getLineLen(float orgX, float orgY, float dx, float dy){
		final Map map = world.map();
		orgX = orgX / Style.TILE_SIZE;
		orgY = flipMap(orgY) / Style.TILE_SIZE;
		int mapX = (int) orgX;
		int mapY = (int) orgY;
		float sideDistX, sideDistY;
		final float deltaDistX = (float) Math.sqrt(1 + (dy * dy) / (dx * dx));
		final float deltaDistY = (float) Math.sqrt(1 + (dx * dx) / (dy * dy));
		final int stepX, stepY;
		if (dx < 0){
			stepX = -1;
			sideDistX = (orgX - mapX) * deltaDistX;
		}else{
			stepX = 1;
			sideDistX = (mapX + 1f - orgX) * deltaDistX;
		}
		if (dy < 0){
			stepY = -1;
			sideDistY = (orgY - mapY) * deltaDistY;
		}else{
			stepY = 1;
			sideDistY = (mapY + 1f - orgY) * deltaDistY;
		}
		for (int i = 0; i < Style.MAX_VISIBLE_DISTANCE_INT; i++){
			if (sideDistX < sideDistY){
				sideDistX += deltaDistX;
				mapX += stepX;
			}else{
				sideDistY += deltaDistY;
				mapY += stepY;
			}
			if (map.getTileType(mapX, mapY) == TileType.WALL)
				return i * Style.TILE_SIZE;
		}
		return (int) Style.MAX_VISIBLE_DISTANCE;
	}
	
	public Item getItem(float x, float y, float width, float height){
		Map map = world.map();
		final int mapX = toMapX(x);
		final int mapY = toMapY(y + height);
		int toX = toMapX(x + width)+1;
		int toY = toMapY(y)+1;
		toX += mapX == toX ? 1 : 0;
		toY += mapY == toY ? 1 : 0;
		for (int i = mapY; i < toY; i++){
			for (int j = mapX; j < toX; j++){
				final Cell cell = map.get(j, i);
				for (int k = 0; k < cell.items.size; k++){
					final Item item = cell.items.get(k);
					if (isRectOverlaps(x, y, width, height, item.getX(), item.getY(), item.getWidth(), item.getHeight()))
						return item;
				}
			}	
		}
		return null;
	}
	
	public boolean isVisible(Ray ray, float x, float y, float visibleDistance, float eyeRange){
		final float distance = getDistanceFast(ray.origin.x, ray.origin.y, x, y);
		return distance < visibleDistance * visibleDistance && inEyeRange(ray, x, y, eyeRange) && !isWallPreventFast(ray.origin.x, ray.origin.y, x, y, visibleDistance);
	}
	
	@SuppressWarnings("unused")
	private boolean isWallPrevent(float orgX, float orgY, float px, float py){
		Map map = world.map();
		orgY = flipMap(orgY);
		final float dx = px - orgX;
		final float dy = flipMap(py) - orgY;
		int steps;
		if (Math.abs(dx) < Math.abs(dy)){
			steps = (int) dy;
		}else{
			steps = (int) dx;
		}
		steps = Math.abs(steps);
		final float incX = (float) dx / steps;
		final float incY = (float) dy / steps;
		for (int i = 0; i < Style.PHYSICS_RAY_ACCURACY; i++){
			final int x = toMapCoords(orgX + i * incX);
			final int y = toMapCoords(orgY + i * incY);
			final TileType type = map.getTileType(x, y);
			if (type == TileType.WALL)
				return true;
			if (isPointInRect(fromMapX(x), fromMapY(y), Style.TILE_SIZE, Style.TILE_SIZE, px, py))
				return false;
		}
		return false;
	}
	
	private boolean isWallPreventFast(float orgX, float orgY, float px, float py, float distance){
		final float vecX = px - orgX;
		final float vecY = py - orgY;
		final float vecLen = (float) Math.sqrt(vecX * vecX + vecY * vecY);
		final float dx = vecX / vecLen;
		final float dy = -vecY / vecLen;
		final Map map = world.map();
		orgX = orgX / Style.TILE_SIZE;
		orgY = flipMap(orgY) / Style.TILE_SIZE;
		int mapX = (int) orgX;
		int mapY = (int) orgY;
		float sideDistX, sideDistY;
		final float deltaDistX = (float) Math.sqrt(1 + (dy * dy) / (dx * dx));
		final float deltaDistY = (float) Math.sqrt(1 + (dx * dx) / (dy * dy));
		final int stepX, stepY;
		if (dx < 0){
			stepX = -1;
			sideDistX = (orgX - mapX) * deltaDistX;
		}else{
			stepX = 1;
			sideDistX = (mapX + 1f - orgX) * deltaDistX;
		}
		if (dy < 0){
			stepY = -1;
			sideDistY = (orgY - mapY) * deltaDistY;
		}else{
			stepY = 1;
			sideDistY = (mapY + 1f - orgY) * deltaDistY;
		}
		final int rayLen = (int) (distance / Style.TILE_SIZE);
		for (int i = 0; i < rayLen; i++){
			if (sideDistX < sideDistY){
				sideDistX += deltaDistX;
				mapX += stepX;
			}else{
				sideDistY += deltaDistY;
				mapY += stepY;
			}
			if (map.getTileType(mapX, mapY) == TileType.WALL)
				return true;
			if (isPointInRect(fromMapX(mapX), fromMapY(mapY), Style.TILE_SIZE, Style.TILE_SIZE, px, py))
				return false;
		}
		return false;
	}
	
	
	private boolean inEyeRange(Ray ray, float x, float y, float range){
		float vec1x = x - ray.origin.x;
		float vec1y = y - ray.origin.y;
		float vec2x = ray.direction.x;
		float vec2y = ray.direction.y;
		final float len1 = Maths.len(0, 0, vec1x, vec1y);
		final float len2 = Maths.len(0, 0, vec2x, vec2y);
		vec1x /= len1;
		vec1y /= len1;
		vec2x /= len2;
		vec2y /= len2;
		final float view = vec1x * vec2x + vec1y * vec2y;
		return view > range;
	}
	
	public void checkExplosion(float centerFloatX, float centerFloatY, float floatRadius, float damage){
		Iterator<MapObject> iter = world.objects().getObjects().iterator();
		MapObject obj;
		float len;
		float dRad = floatRadius * floatRadius;
		while(iter.hasNext()){
			obj = iter.next();
			if (!obj.canToExplode())
				return;
			len = getDistanceFast(centerFloatX, centerFloatY, obj.getCenterX(), obj.getCenterY());
			if (len < dRad)
				obj.getDamage(damage - (damage / floatRadius * (float) Math.sqrt(len)), true);
		}
		// more 'fast' new explosion version
		/*HashSet<Cell> cells = new HashSet<>();

		int radius = toMapCoords(floatRadius);
		int cx = toMapX(centerFloatX);
		int cy = toMapY(centerFloatY);

		int x = 0;
		int y = radius;
		int delta = 1 - 2 * radius;
		int error;
		while (y >= 0) {
			for (int i = cx - x; i <= cx + x; i++) {
				int y1 = cy + y;
				int y2 = cy - y;
				cells.add(world.map().get(i, y1));
				cells.add(world.map().get(i, y2));
			}

			error = 2 * (delta + y) - 1;
			if ((delta < 0) && (error <= 0)) {
				delta += 2 * ++x + 1;
				continue;
			}
			if ((delta > 0) && (error > 0)) {
				delta -= 2 * --y + 1;
				continue;
			}
			delta +=2 * (++x - y--);
		}

		float dRad = floatRadius * floatRadius;

		ArrayList<MapObject> checkedObjects = new ArrayList<>();

		for (Cell c: cells) {
			for (MapObject obj: c.objects){
				if (!obj.canToExplode() || checkedObjects.contains(obj))
					continue;
				float len = getDistanceFast(centerFloatX, centerFloatY, obj.getCenterX(), obj.getCenterY());
				if (len < dRad)
					obj.getDamage(damage - (damage / floatRadius * (float) Math.sqrt(len)), true);
				checkedObjects.add(obj);
			}
		}*/
	}
	
	public Shoot checkShoot(Ray ray, MapObject exception){
		Shoot shoot;
		tmpShoot1.object = null;
		checkObjectsShoot(ray, exception, tmpShoot1);
		if (tmpShoot1.object == null){
			checkWallsShoot(ray, tmpShoot1);
			return tmpShoot1;
		}else{
			checkWallsShoot(ray, tmpShoot2);
			shoot = tmpShoot1.dist() < tmpShoot2.dist() ? tmpShoot1 : tmpShoot2;
			return shoot;
		}
		//	return tmpShoot1; //new shoot version
	}
	
	private void checkWallsShoot(Ray ray, Shoot shoot){
		Map map = world.map();
		float orgX = ray.origin.x;
		float orgY = flipMap(ray.origin.y);
		float incX = ray.direction.x;
		float incY = -ray.direction.y;
		shoot.setOrigin(ray.origin.x, ray.origin.y);
		shoot.setDirection(ray.direction.x, ray.direction.y);
		for (int i = 0; i < Style.PHYSICS_RAY_ACCURACY; i++){
			final int x = toMapCoords(orgX + i * incX);
			final int y = toMapCoords(orgY + i * incY);
			//world.debugger().drawRect(fromMapX(x), fromMapY(y), Color.RED, true);
			if (map.getTileType(x, y) == TileType.WALL){
				intersectRayBounds(fromMapX(x), fromMapY(y), Style.TILE_SIZE, Style.TILE_SIZE, ray, tmpVect);
				shoot.setHit(tmpVect.x, tmpVect.y);
				return;
			}
		}
		shoot.setHit(0, 0);
	}

	private Array<MapObject> getObjectByLine(Ray ray, Shoot shoot){ //new shoot version
		Array<MapObject> allObjects = new Array<>();
		Map map = world.map();
		float orgX = ray.origin.x;
		float orgY = flipMap(ray.origin.y);
		float incX = ray.direction.x;
		float incY = -ray.direction.y;
		shoot.setOrigin(ray.origin.x, ray.origin.y);
		shoot.setDirection(ray.direction.x, ray.direction.y);
		for (int i = 0; i < Style.PHYSICS_RAY_ACCURACY; i++){
			final int x = toMapCoords(orgX + i * incX);
			final int y = toMapCoords(orgY + i * incY);
			final Cell cell = map.get(x, y);
			allObjects.addAll(cell.objects);
			if (cell.type == TileType.WALL){
				intersectRayBounds(fromMapX(x), fromMapY(y), Style.TILE_SIZE, Style.TILE_SIZE, ray, tmpVect);
				shoot.setHit(tmpVect.x, tmpVect.y);
				return allObjects;
			}
		}
		shoot.setHit(0, 0);
		return allObjects;
	}

	private void checkObjectsShoot(Ray ray, MapObject exception, Shoot shoot){
		Iterator<MapObject> iter = world.objects().getObjects().iterator();
		//Iterator<MapObject> iter = getObjectByLine(ray, shoot).iterator(); //new shoot version
		MapObject obj, nearObj = null;
		boolean hitted = false;
		float minDist = -1, dist;
		shoot.setOrigin(ray.origin.x, ray.origin.y);
		shoot.setDirection(ray.direction.x, ray.direction.y);
		while(iter.hasNext()){
			obj = iter.next();
			if (obj == exception || !obj.canToShoot())
				continue;
			hitted = intersectRayBounds(obj.getX(), obj.getY(), obj.getCollideWidth(), obj.getCollideHeight(), ray, tmpVect);
			if (hitted){
				dist = getDistanceFast(ray.origin.x, ray.origin.y, obj.getCenterX(), obj.getCenterY());
				if (dist < minDist || minDist == -1){
					nearObj = obj;
					minDist = dist;
					shoot.setHit(tmpVect.x, tmpVect.y);
				}
			}
			shoot.object = nearObj;
		}
	}
	
	public Motion canIMove(float x, float y, float width, float height, float stepX, float stepY, boolean checkObjects){
		return canIMove(x, y, width, height, stepX, stepY, null, checkObjects);
	}
	
	public Motion canIMove(float x, float y, float width, float height, float stepX, float stepY){
		return canIMove(x, y, width, height, stepX, stepY, null, true);
	}
	
	public Motion canIMove(float x, float y, float width, float height, float stepX, float stepY, MapObject exception){
		return canIMove(x, y, width, height, stepX, stepY, exception, true);
	}
	
	public Motion canIMove(float x, float y, float width, float height, float stepX, float stepY, MapObject exception, boolean checkObject){
		tmpMotion.clear();
		canMapMove(x, y, width, height, stepX, stepY, tmpMotion);
		if (checkObject && (tmpMotion.x || tmpMotion.y))
			canObjectsMove(x, y, width, height, stepX, stepY, exception, tmpMotion);
		return tmpMotion;
	}
	
	private void canObjectsMove(float x, float y, float width, float height, float stepX, float stepY, MapObject exception, Motion m){
		Map map = world.map();
		final int mapX = toMapX(x + stepX);
		final int mapY = toMapY(y + stepY + height);
		int toX = toMapX(x + width + stepX*2)+1;
		int toY = toMapY(y + stepY)+1;
		toX += mapX == toX ? 1 : 0;
		toY += mapY == toY ? 1 : 0;
		for (int i = mapY; i < toY; i++){
			for (int j = mapX; j < toX; j++){
				Cell cell = map.get(j, i);
				for (int k = 0; k < cell.objects.size; k++){
					final MapObject obj = cell.objects.get(k);
					final float tx = obj.getX();
					final float ty = obj.getY();
					final float tw = obj.getCollideWidth();
					final float th = obj.getCollideHeight();
					if (!obj.canToCollide() || obj.equals(exception))
						continue;
					if (isRectOverlaps(x + stepX, y, width, height, tx, ty, tw, th)){
						m.barriers[0] = obj;
						m.x = false;
					}
					if (isRectOverlaps(x, y + stepY, width, height, tx, ty, tw, th)){
						m.barriers[1] = obj;
						m.y = false;
					}
					if (!m.x && !m.y)
						return;
				}
			}
		}
	}
	
	private void canMapMove(float x, float y, float width, float height, float stepX, float stepY, Motion m){
		Map map = world.map();
		final int mapX = toMapX(x + stepX);
		final int mapY = toMapY(y + stepY + height);
		int toX = toMapX(x + width + stepX*2)+1;
		int toY = toMapY(y + stepY)+1;
		int i, j;
		toX += mapX == toX ? 1 : 0;
		toY += mapY == toY ? 1 : 0;
		m.set(true, true);
		for (i = mapY; i < toY; i++){
			for (j = mapX; j < toX; j++){
				if (map.getTileType(j, i) == TileType.WALL){
					if (isRectOverlaps(x + stepX, y, width, height, fromMapX(j), fromMapY(i), Style.TILE_SIZE, Style.TILE_SIZE))
						m.x = false;
					if (isRectOverlaps(x, y + stepY, width, height, fromMapX(j), fromMapY(i), Style.TILE_SIZE, Style.TILE_SIZE))
						m.y = false;
					if (!m.x && !m.y)
						return;
				}
			}
		}
	}
	
	private boolean isPointInRect(float x, float y, float width, float height, float px, float py){
		return px >= x && py >= y && px <= x + width && py <= y + height;
	}
	
	private boolean	intersectRayBounds(float x, float y, float width, float height, Ray ray, Vector3 vect){
		minVect.set(x, y, 0);
		maxVect.set(x + width, y + height, 0);
		return Intersector.intersectRayBounds(ray, tmpBox, vect);
	}
	
	private float getDistanceFast(float x1, float y1, float x2, float y2){
		return (float) (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}
	
	private float fromMapX(int x){
		return x * Style.TILE_SIZE;
	}
	
	private float fromMapY(int y){
		return (world.map().getMapHeight() - y - 1) * Style.TILE_SIZE;
	}
	
	private float flipMap(float y){
		return world.map().getMapHeight() * Style.TILE_SIZE - y;
	}
	
	private int toMapX(float x){
		return toMapCoords(x); 
	}
	
	private int toMapY(float y){
		return toMapCoords(world.map().getMapHeight() * Style.TILE_SIZE - y);
	}
	
	private int toMapCoords(float value){
		return (int) (value / Style.TILE_SIZE);
	}
	
	public static boolean isRectOverlaps(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
		return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
	}
}
