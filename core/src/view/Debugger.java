package view;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import engine.SpecialForces;
import engine.Style;

public class Debugger extends Actor{
	private ShapeRenderer shaper;
	private ConcurrentLinkedQueue<DebugRect> rects;
	private ConcurrentLinkedQueue<DebugLine> lines;
	private ConcurrentLinkedQueue<DebugCircle> circles;
	
	public Debugger(){
		shaper = SpecialForces.getInstance().getShaper();
		rects = new ConcurrentLinkedQueue<DebugRect>();
		lines = new ConcurrentLinkedQueue<DebugLine>();
		circles = new ConcurrentLinkedQueue<DebugCircle>();
	}
	
	public void drawCircle(float x, float y, float radius, Color color, boolean filled){
		circles.add(new DebugCircle(x, y, radius, color, filled));
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, Color color){
		lines.add(new DebugLine(x1, y1, x2, y2, color));
	}
	
	public void drawRect(float x, float y, float width, float height, Color color, boolean filled){
		rects.add(new DebugRect(x, y, width, height, color, filled));
	}
	
	public void drawRect(float x, float y, Color color, boolean filled){
		drawRect(x, y, Style.TILE_SIZE, Style.TILE_SIZE, color, filled);
	}
	
	public void drawRect(float x, float y, Color color){
		drawRect(x, y, Style.TILE_SIZE, Style.TILE_SIZE, color, true);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		DebugRect r;
		DebugLine l;
		DebugCircle c;
		batch.end();
		shaper.begin(ShapeType.Filled);
		shaper.setProjectionMatrix(getStage().getCamera().combined);
		shaper.setAutoShapeType(true);
		while(!circles.isEmpty()){
			c = circles.poll();
			shaper.setColor(c.color);
			shaper.set(c.filled ? ShapeType.Filled : ShapeType.Line);
			shaper.circle(c.x, c.y, c.radius);
		}
		while(!rects.isEmpty()){
			r = rects.poll();
			shaper.setColor(r.color);
			shaper.set(r.filled ? ShapeType.Filled : ShapeType.Line);
			shaper.rect(r.x, r.y, r.width, r.height);
		}
		while(!lines.isEmpty()){
			l = lines.poll();
			shaper.setColor(l.color);
			shaper.line(l.x1, l.y1, l.x2, l.y2);
		}
		shaper.end();
		batch.begin();
	}
}

class DebugCircle{
	public float x, y, radius;
	public Color color;
	public boolean filled;
	public DebugCircle(float x, float y, float radius, Color color, boolean filled){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		this.filled = filled;
	}
}

class DebugRect{
	public float x, y, width, height;
	public Color color;
	public boolean filled;
	public DebugRect(float x, float y, float width, float height, Color color, boolean filled){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.filled = filled;
	}
}

class DebugLine{
	public float x1, y1, x2, y2;
	public Color color;
	public DebugLine(float x1, float y1, float x2, float y2, Color color){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
}
