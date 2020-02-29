package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import view.Font;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;

public class Help extends Group{
	
	private Image background, text;
	private Button closeBtn;
	private Label caption;
	
	public Help(Loader loader){
		background = new Image(loader.getBackground("contracts"), 0, 0);
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		closeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		closeBtn.setPosition(getWidth() - closeBtn.getOriginX()*1.5f, getHeight() - closeBtn.getOriginY()*1.5f);
		closeBtn.addListener(new CloseEvent(this));
		
		text = new Image(SpecialForces.getInstance().isAndroid() ? loader.getOther("gameHelp_android") : loader.getOther("gameHelp_pc"));
		final float cof = text.getWidth() / text.getHeight();
		text.setSize(getHeight()*cof, getHeight());
		text.setPosition(getOriginX(), getOriginY(), Align.center);
		
		caption = new Label(loader, "controls", Font.DEFAULT, Align.center, getOriginX(), 570, 0, 0);
		//addActor(caption);
		
		addActor(background);
		addActor(closeBtn);
		addActor(caption);
		addActor(text);
		setPosition(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2, Align.center);
	}
	
	class CloseEvent extends ClickListener{
		private Help help;
		
		public CloseEvent(Help help){
			this.help = help;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			help.setVisible(false);
		}
	}
}
