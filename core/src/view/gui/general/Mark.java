package view.gui.general;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Image;
import view.Label;
import view.gui.PressController;
import view.gui.Pressable;
import world.gameplay.Mission;

public class Mark extends Group implements Pressable{
	
	private final float focusColor = 0.8f, downColor = 0.6f;
	private Loader loader;
	private Image markImg;
	private Label nameLab;
	private boolean down, hover;
	
	private Mission mission;
	private int missionIndex;
	
	public Mark(Loader loader, Mission mission, int missionIndex){
		this.loader = loader;
		markImg = new Image(loader.getIcon(mission.getStatus().getMarkIcon()));
		setSize(markImg.getWidth(), markImg.getHeight());
		
		nameLab = new Label(loader, "", Font.MARK, Align.left, markImg.getWidth() + 5, 115, 100, 0);
		nameLab.setTouchable(Touchable.disabled);
		
		addActor(markImg);
		addActor(nameLab);
		
		addListener(new PressController(this));
		setMission(mission, missionIndex);
	}
	
	public void setMission(Mission mission, int missionIndex){
		this.mission = mission;
		this.missionIndex = missionIndex;
		setPosition(mission.getMarkX() - markImg.getOriginX(), mission.getMarkY());
		nameLab.setCaption(mission.getName().replace(' ', '\n'));
		update();
	}
	
	public void update(){
		markImg.setTexture(loader.getIcon(mission.getStatus().getMarkIcon()));
	}
	
	public Mission getMission(){
		return mission;
	}
	
	public int getMissionIndex(){
		return missionIndex;
	}

	@Override
	public void setDown(boolean value) {
		down = value;
	}

	@Override
	public void setFocus(boolean value) {
		hover = value;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (hover)
			markImg.setColor(focusColor, focusColor, focusColor, 1f);
		if (down)
			markImg.setColor(downColor, downColor, downColor, 1f);
		super.draw(batch, parentAlpha);
		markImg.setColor(1f, 1f, 1f, 1f);
	}
	
}