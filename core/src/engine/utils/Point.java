package engine.utils;

public class Point {
	public float x, y;
	
	public Point(float x, float y){
		set(x, y);
	}
	
	public Point(){
		this(0, 0);
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void copy(Point point){
		this.x = point.x;
		this.y = point.y;
	}
	
	public void scl(float value){
		x *= value;
		y *= value;
	}
	
	public void sub(float x, float y){
		this.x -= x;
		this.y -= y;
	}
	
	public void add(float x, float y){
		this.x += x;
		this.y += y;
	}

	public void set(Point point) {
		set(point.x, point.y);
	}

	public void sub(Point oldPoint) {
		sub(oldPoint.x, oldPoint.y);
	}
}
