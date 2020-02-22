package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import screens.GeneralScreen;
import stages.BackgroundStage;
import view.Font;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import world.gameplay.Location;
import world.gameplay.Mission;
import world.gameplay.MissionStatus;

public class Missions extends Group{
	private GeneralScreen generalScreen;
	
	private BackgroundStage bgrStage;
	private Label locationName;
	private ReadyDialog dialog;
	private Button[] buttons;
	private Location[] locations;
	private Mark[][] marks;
	
	private int locationIndex, missionIndex;
	private Mission selectedMission;
	
	public Missions(Loader loader, BackgroundStage bgrStage, Location[] locations, GeneralScreen generalScreen){
		this.bgrStage = bgrStage;
		this.locations = locations;
		this.generalScreen = generalScreen;
		
		setOrigin(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2);
		locationName = new Label(loader, "", Font.DEFAULT, Align.center, getOriginX(), 570, 0, 0);
		addActor(locationName);
		
		buttons = new Button[locations.length];
		
		marks = new Mark[locations.length][];
		Button btn;
		Mark mark;
		Mission[] missions;
		final float px = 26, py = 525;
		for (int i = 0, k = 0; i < locations.length; i++){
			btn = new Button(loader, ButtonType.LOCATION, locations[i].getName(), ButtonIcon.LOCATION);
			btn.setPosition(px, py - i * (btn.getHeight() + 35));
			btn.addListener(new SelectEvent(this, i));
			buttons[i] = btn;
			addActor(btn);
			missions = locations[i].getMissions();
			marks[i] = new Mark[missions.length];
			for (k = 0; k < missions.length; k++){
				mark = new Mark(loader, missions[k], k);
				mark.addListener(new MarkEvent(this, mark));
				mark.setVisible(false);
				addActor(mark);
				marks[i][k] = mark;
			}
		}
		
		dialog = new ReadyDialog(loader, this);
		dialog.setVisible(false);
		addActor(dialog);
		
		selectLocation(0);
	}
	
	public void startMission(){
		generalScreen.startMission(locationIndex, missionIndex);
	}
	
	public void selectMark(Mark mark){
		selectedMission = mark.getMission();
		missionIndex = mark.getMissionIndex();
		if (selectedMission.getStatus() != MissionStatus.LOCKED)
			dialog.setVisible(true);
	}
	
	public void selectLocation(int index){
		locationName.setCaption(locations[index].getName());
		setVisibleMarks(locationIndex, false);
		setVisibleMarks(index, true);
		bgrStage.showMap(index);
		locationIndex = index;
	}
	
	private void setVisibleMarks(int locationIndex, boolean visible){
		final Mark[] marks = this.marks[locationIndex];
		for (Mark m: marks)
			m.setVisible(visible);
	}
	
	public void update(){
		for (int i = 0; i < marks.length; i++)
			for (int k = 0; k < marks[i].length; k++)
				marks[i][k].update();
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible){
			update();
			bgrStage.showMap(locations[locationIndex].getMapIndex());
		}else{
			bgrStage.hideMap();
		}
	}
	
	class MarkEvent extends ClickListener{
		private Missions parent;
		private Mark mark;
		
		public MarkEvent(Missions parent, Mark mark){
			this.parent = parent;
			this.mark = mark;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.selectMark(mark);
		}
	}
	
	class SelectEvent extends ClickListener{
		private Missions missions;
		private int index;
		
		public SelectEvent(Missions missions, int index){
			this.missions = missions;
			this.index = index;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			missions.selectLocation(index);
		}
	}
}
