package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Image;

public class ContractAdd extends Group{
	private Image background, plusImg;
	
	public ContractAdd(Loader loader, float x, float y){
		background = new Image(loader.getGui("back_contract"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		setPosition(x, y);
		
		plusImg = new Image(loader.getIcon("addSoldier"));
		plusImg.setPosition(getOriginX() - plusImg.getOriginX(), getOriginY() - plusImg.getOriginY());
		
		addActor(background);
		addActor(plusImg);
	}

}
