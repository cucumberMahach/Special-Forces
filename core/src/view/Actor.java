package view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

import engine.SpecialForces;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor{
	
	public float getPpuX(){
		return SpecialForces.getInstance().getPpuX();
	}
	
	public float getPpuY(){
		return SpecialForces.getInstance().getPpuY();
	}
	
	public void drawDebug(SpriteBatch batch) {
		batch.end();
		SpecialForces.getInstance().getShaper().begin(ShapeType.Line);
		SpecialForces.getInstance().getShaper().setColor(1, 0, 0, 1);
		SpecialForces.getInstance().getShaper().rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		SpecialForces.getInstance().getShaper().end();
		batch.begin();
	}
	
	@Override
	protected void drawDebugBounds(ShapeRenderer shapes) {
		if (!getDebug()) return;
		shapes.set(ShapeType.Line);
		shapes.setColor(1, 0, 0, 1);
		shapes.circle(getX() + getOriginX(), getY() + getOriginY(), 1);
		super.drawDebugBounds(shapes);
	}
	
	@Override
	protected void setStage(Stage stage) {
		//setDebug(true);
		super.setStage(stage);
	}
	
}
