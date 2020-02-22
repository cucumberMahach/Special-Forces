package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import controller.SlideController;
import engine.Loader;
import engine.SpecialForces;
import world.gameplay.Soldier;

public class Team extends Group{
	private Loader loader;
	private Array<Contract> contracts;
	private Array<Soldier> team;
	private ContractAdd contractAdd;
	private AddToTeam addToTeam;
	
	private static final float offx = 84, offy = 170, space = 20;
	
	public Team(Loader loader, Array<Soldier> team){
		this.loader = loader;
		this.team = team;
		setOrigin(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2);
		
		contracts = new Array<Contract>();
		Contract c = null;
		for (int i = 0; i < team.size; i++){
			c = new Contract(loader, team.get(i), this, i, true);
			c.setY(offy);
			addActor(c);
			contracts.add(c);
		}
		
		contractAdd = new ContractAdd(loader, offx, offy);
		addToTeam = new AddToTeam(loader, this, 30, 41);
		addToTeam.setVisible(false);
		contractAdd.addListener(new ShowEvent(this));
		
		slideToStart();
		
		addActor(contractAdd);
		addActor(addToTeam);
		addListener(new SlideController(this));
	}
	
	public void addSoldier(Soldier soldier){
		Contract c = new Contract(loader, soldier, this, contracts.size, contractAdd.getX(), contractAdd.getY(), true);
		contractAdd.setX(c.getRight() + space);
		addActor(c);
		contracts.add(c);
		team.add(soldier);
		SpecialForces.getInstance().playerData().save();
		addToTeam.toFront();
	}
	
	public void removeSoldier(int index){
		contracts.removeIndex(index).remove();
		team.removeIndex(index);
		Contract c = null;
		for(int i = 0; i < contracts.size; i++){
			c = contracts.get(i);
			c.setPosition(offx + i * (c.getWidth() + space), offy);
			c.setIndex(i);
		}
		contractAdd.setX((c != null ? c.getRight() : offx) + space);
		SpecialForces.getInstance().playerData().save();
	}
	
	public void setVisibleAddToTeam(boolean visible){
		addToTeam.setVisible(visible);
		if (visible)
			clearListeners();
		else
			addListener(new SlideController(this));
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			slideToStart();
	}
	
	public void slideToStart(){
		if (contracts.size == 0)
			return;
		Contract c = null;
		for (int i = 0; i < contracts.size; i++){
			c = contracts.get(i);
			c.setX(offx + i * (c.getWidth() + space));
		}
		contractAdd.setX(c.getRight() + space);
	}
	
	public void slideContracts(float x){
		if (contracts.size == 0)
			return;
		final Contract first = contracts.first();
		if (contractAdd.getRight() + x < SpecialForces.WIDTH - offx)
			x = (SpecialForces.WIDTH - offx) - contractAdd.getRight();
		if (first.getX() + x > offx)
			x = offx - first.getX();
		for(Contract c: contracts){
			c.moveBy(x, 0);
		}
		contractAdd.moveBy(x, 0);
	}
	
	class ShowEvent extends ClickListener{
		private Team team;
		
		public ShowEvent(Team team) {
			this.team = team;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			team.setVisibleAddToTeam(true);
		}
	}
}
