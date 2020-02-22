package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Label;
import view.gui.Button;
import view.gui.ButtonType;
import world.gameplay.Soldier;

public class BuyContract extends Group{
	private Contract contract;
	private Label costLab;
	private Button buyBtn;
	private Soldier soldier;
	private AddToTeam parent;
	
	public BuyContract(Loader loader, Soldier soldier, AddToTeam parent, float x, float y){
		this.soldier = soldier;
		this.parent = parent;
		contract = new Contract(loader, soldier, 0, 200, false);
		setSize(contract.getWidth(), contract.getHeight() + 185);
		setOrigin(Align.center);
		costLab = new Label(loader, "", Font.CONTRACT_COST, Align.center, getOriginX(), 115, 0, 0);
		buyBtn = new Button(loader, ButtonType.BUY, "add to team", null);
		buyBtn.setPosition(getOriginX() - buyBtn.getOriginX(), 0);
		buyBtn.addListener(new BuyEvent(this));
		
		addActor(contract);
		addActor(costLab);
		addActor(buyBtn);
		setPosition(x, y);
		costLab.setCaption(soldier.getCost() + " $");
	}
	
	public void buy(){
		parent.buy(soldier);
	}
	
	public void setSoldier(Soldier soldier){
		contract.setSoldier(soldier);
		costLab.setCaption(soldier.getCost() + " $");
		this.soldier = soldier;
	}
	
	class BuyEvent extends ClickListener{
		
		private BuyContract buyContract;
		
		public BuyEvent(BuyContract buyContract){
			this.buyContract = buyContract;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			buyContract.buy();
		}
	}
}
