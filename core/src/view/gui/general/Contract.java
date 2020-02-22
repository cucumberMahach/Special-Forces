package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import world.gameplay.Soldier;
import world.gameplay.WeaponType;

public class Contract extends Group{
	private Loader loader;
	private Image background, iconBack, info, weapon, face;
	private Button removeButton;
	private Label nameLab;
	private ContractBar accuracyBar, reactionBar, braveBar;
	private BlueStar blueStar;
	
	private int index;
	private Team team;
	
	public Contract(Loader loader, Soldier s, Team team, int index, float x, float y, boolean removable){
		this.loader = loader;
		background = new Image(loader.getGui("back_contract"));
		setSize(background.getWidth(), background.getHeight());
		setPosition(x, y);
		
		iconBack = new Image(loader.getGui("iconBack_contract"));
		iconBack.setPosition(getWidth()/2 - iconBack.getOriginX(), 266);
		nameLab = new Label(loader, "boomber", Font.CONTRACT_NAME, Align.center, getWidth()/2, iconBack.getY() - 40, 0, 0);
		info = new Image(loader.getOther("contractInfo"));
		info.setPosition(21, 92);
		weapon = new Image(loader.getWeaponIcon(WeaponType.MP5.ordinal()), 1.4f);
		weapon.setPosition(getWidth()/2-weapon.getOriginX(), 15);
		accuracyBar = new ContractBar(loader, 150, 168);
		reactionBar = new ContractBar(loader, accuracyBar.getX(), accuracyBar.getY() - accuracyBar.getHeight() - 5);
		braveBar = new ContractBar(loader, accuracyBar.getX(), reactionBar.getY() - accuracyBar.getHeight() - 5);
		blueStar = new BlueStar(loader, 180, 355);
		face = new Image(loader.getFace(0), iconBack.getX(), iconBack.getY());
		
		addActor(background);
		if (removable){
			this.team = team;
			this.index = index;
			removeButton = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
			removeButton.setPosition(-10, getHeight() - removeButton.getHeight() + 10);
			removeButton.addListener(new RemoveEvent(this));
			addActor(removeButton);
		}
		addActor(iconBack);
		addActor(nameLab);
		addActor(info);
		addActor(weapon);
		addActor(accuracyBar);
		addActor(reactionBar);
		addActor(braveBar);
		addActor(face);
		addActor(blueStar);
		setFace(0);
		setSoldier(s);
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void removeSolider(){
		team.removeSoldier(index);
	}
	
	public Contract(Loader loader, Soldier s, int x, int y, boolean removable) {
		this(loader, s, null, 0, x, y, removable);
	}
	
	public Contract(Loader loader, Soldier s, Team team, int index, boolean removable){
		this(loader, s, team, index, 0, 0, removable);
	}

	public void setSoldier(Soldier s){
		nameLab.setCaption(s.getName());
		setFace(s.getFace());
		accuracyBar.setValue(s.getAccuracy());
		reactionBar.setValue(s.getReaction());
		braveBar.setValue(s.getBrave());
		blueStar.setValue(s.getLevel());
		weapon.setTexture(loader.getWeaponIcon(s.getWeapon().ordinal()));
		weapon.setPosition(getWidth()/2-weapon.getOriginX(), 15);
	}
	
	private void setFace(int index){
		face.setTexture(loader.getFace(index));
		face.setPosition(iconBack.getX() + iconBack.getOriginX() - face.getOriginX(), iconBack.getY());
	}

}

class RemoveEvent extends ClickListener{
	private Contract parent;
	
	public RemoveEvent(Contract parent){
		this.parent = parent;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		parent.removeSolider();
	}
}
