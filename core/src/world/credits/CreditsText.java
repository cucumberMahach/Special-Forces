package world.credits;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import stages.World;
import view.Actor;
import view.Font;
import view.Group;
import view.Label;

public class CreditsText extends Group {

    private World world;
    private Label label;

    private String text = "Special Forces\n" +
            "\n" +
            "Developed by Middle Games\n" +
            "2016 - 2020\n" +
            "\n" +
            "LibGDX framework";

    public CreditsText(World world, Loader loader){
        this.world = world;
        label = new Label(loader, text, Font.CONTRACT_COST, Align.center, 0, 0, 0, 0);
        addActor(label);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        label.setPosition(world.getCamera().position.x+20, world.getCamera().position.y + 200);
    }
}
