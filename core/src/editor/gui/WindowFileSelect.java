package editor.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import stages.Editor;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;

public class WindowFileSelect extends Group{
	private Editor editor;
	private Image background;
	private Label captionLab;
	private Button closeBtn;
	private Button cells[];
	
	private FileSelectAction action;
	
	public WindowFileSelect(Editor editor, Loader loader){
		this.editor = editor;
		background = new Image(loader.getBackground("contracts"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		captionLab = new Label(loader, "select slot", Font.DEFAULT, Align.left, 0, 0, getWidth(), 30);
		captionLab.setPosition(20, getHeight() - captionLab.getHeight() - 20);
		closeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
		closeBtn.setPosition(getWidth() - closeBtn.getWidth() - 10, getHeight() - closeBtn.getHeight() - 10);
		closeBtn.addListener(new CloseEvent(this));
		addActor(background);
		addActor(captionLab);
		addActor(closeBtn);
		cells = new Button[20];
		for (int y = 0, i = 0; y < 5; y++){
			for (int x = 0; x < 4; x++){
				final Button btn = new Button(loader, ButtonType.BUY, "slot ".concat(String.valueOf(i+1)), null);
				btn.addListener(new SlotEvent(this, i));
				btn.setPosition(20 + (btn.getWidth() + 38) * x, getHeight() - (50 + (btn.getHeight() + 25) * (y+1)));
				btn.setTouchable(Touchable.enabled);
				cells[i] = btn;
				addActor(btn);
				i++;
			}
		}
		setVisible(false);
	}
	
	public void slotSelected(int index){
		switch (action) {
		case SAVE:
			editor.manager().save(index);
			break;
		case OPEN:
			editor.manager().open(index);
			break;
		}
		setVisible(false);
	}
	
	public void show(FileSelectAction action){
		this.action = action;
		setVisible(true);
		editor.clearControllers();
	}
	
	class CloseEvent extends ClickListener{
		private WindowFileSelect parent;
		
		public CloseEvent(WindowFileSelect parent){
			this.parent = parent;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.setVisible(false);
		}
	}
	
	class SlotEvent extends ClickListener{
		private int index;
		private WindowFileSelect parent;
		
		public SlotEvent(WindowFileSelect parent, int index){
			this.index = index;
			this.parent = parent;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			parent.slotSelected(index);
		}
	}
}