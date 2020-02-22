package controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import view.gui.general.Team;

public class SlideController extends InputListener{
	private Team team;
	private float downX, offset;
	
	public SlideController(Team team) {
		this.team = team;
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if (button != 0)
			return false;
		downX = x;
		offset = 0;
		return true;
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		final float slide = x - downX;
		team.slideContracts(slide - offset);
		offset = slide;
	}
}
