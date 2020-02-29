package editor.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import stages.Editor;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;

public class WindowHelp extends Group{
    private Editor editor;
    private Image background, content;
    private Label captionLab;
    private Button closeBtn;

    public WindowHelp(Editor editor, Loader loader){
        this.editor = editor;
        background = new Image(loader.getBackground("contracts"));
        setSize(background.getWidth(), background.getHeight());
        setOrigin(Align.center);
        captionLab = new Label(loader, "editor help", Font.DEFAULT, Align.left, 0, 0, getWidth(), 30);
        captionLab.setPosition(20, getHeight() - captionLab.getHeight() - 20);
        closeBtn = new Button(loader, ButtonType.SMALL_RED, "", ButtonIcon.CLOSE);
        closeBtn.setPosition(getWidth() - closeBtn.getWidth() - 10, getHeight() - closeBtn.getHeight() - 10);
        closeBtn.addListener(new CloseEvent(this));

        content = new Image(SpecialForces.getInstance().isAndroid() ? loader.getOther("editorHelp_android") : loader.getOther("editorHelp_pc"));
        content.setPosition(0,0);

        addActor(background);
        addActor(content);
        addActor(captionLab);
        addActor(closeBtn);

        setVisible(false);
    }

    public void show(){
        setVisible(true);
        editor.clearControllers();
    }

    class CloseEvent extends ClickListener{
        private WindowHelp parent;

        public CloseEvent(WindowHelp parent){
            this.parent = parent;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            parent.setVisible(false);
        }
    }
}