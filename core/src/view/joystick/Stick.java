package view.joystick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import engine.utils.Maths;
import view.Button;

public class Stick extends Button{
	private float pos_x, pos_y, down_x, down_y, cof, len;
	private final float radius = 40;
	private Vector2 vect;
	
	private JoystickListener listener;
	private Joystick joystick;

	public Stick(Loader loader, Joystick joystick) {
		super(loader.getHud("btnMove"), 0, 0);
		this.joystick = joystick;
		setSize(83*2.7f, 83*2.7f);
		setActiveInvisible(true);
		//setOrigin(Align.center);
		//setDebug(true);
		vect = Maths.getTmpVector();
		addListener(new StickListener(this));
	}
	
	public void dragDown(float x, float y){
		pos_x = getX();
		pos_y = getY();
		down_x = x;
		down_y = y;

		/*if (listener != null)
			listener.dragDown(x, y);*/
	}
	
	public void dragged(float x, float y){
		float x1 = pos_x + getWidth()/2f;
		float y1 = pos_y + getHeight()/2f;

		float x2 = getX() + getWidth()/2f;
		float y2 = getY() + getHeight()/2f;

		len = vect.set(x2 - x1, y2 - y1).len();
		vect.nor();
		cof = 1f / radius * len;
		if (cof > 1)
			cof = 1;

		setPosition(x - getParent().getX() - down_x, y - getParent().getY() - down_y);

		if (listener != null)
			listener.dragged(vect.x, vect.y, cof);
	}
	
	public void dragUp(){
		setPosition(pos_x, pos_y);
		if (listener != null)
			listener.dragUp();
	}
	
	public void setListener(JoystickListener listener){
		this.listener = listener;
	}

}