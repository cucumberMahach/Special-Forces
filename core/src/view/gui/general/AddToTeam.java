package view.gui.general;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import engine.GameLogic;
import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import view.Image;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import world.gameplay.Soldier;
import world.gameplay.WeaponType;

public class AddToTeam extends Group{
	
	private Loader loader;
	private Image background;
	private Button closeBtn;
	private BuyContract[] contracts;
	private Team team;
	
	public AddToTeam(Loader loader, Team team, float x, float y){
		this.loader = loader;
		this.team = team;
		background = new Image(loader.getBackground("contracts"));
		setSize(background.getWidth(), background.getHeight());
		closeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		closeBtn.setPosition(getWidth() - closeBtn.getOriginX()*1.5f, getHeight() - closeBtn.getOriginY()*1.5f);
		closeBtn.addListener(new CloseEvent(team));
		
		setPosition(x, y);
		addActor(background);
		addActor(closeBtn);
		
		Soldier s = new Soldier(loader, MathUtils.random(19)+1, MathUtils.random(), MathUtils.random(), MathUtils.random(), WeaponType.MP5);
		contracts = new BuyContract[3];
		BuyContract c = null;
		for (int i = 0; i < contracts.length; i++){
			c = new BuyContract(loader, s, this, 23 + i * ((c != null ? c.getWidth() : 0) + 100), 23);
			addActor(c);
			contracts[i] = c;
		}
		generate();
	}
	
	public void buy(Soldier soldier){
		final PlayerData pd = SpecialForces.getInstance().playerData();
		final int cost = soldier.getCost();
		if (cost > pd.getDollars())
			return;
		pd.changeDollars(-cost);
		team.addSoldier(soldier);
		team.setVisibleAddToTeam(false);
		pd.save();
		generate();
	}
	
	public void generate(){
		final WeaponType[] types = WeaponType.values();
		final float level = GameLogic.expToLevel( SpecialForces.getInstance().playerData().getExperience() );
		for (int i = 0; i < contracts.length; i++){
			final Soldier s = new Soldier(loader, (int) (MathUtils.random(level)+1), MathUtils.random(), MathUtils.random(), MathUtils.random(), types[MathUtils.random(types.length-1)]);
			contracts[i].setSoldier(s);
		}
	}
	
	class CloseEvent extends ClickListener{
		private Team team;
		
		public CloseEvent(Team team){
			this.team = team;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			team.setVisibleAddToTeam(false);
		}
	}
}
