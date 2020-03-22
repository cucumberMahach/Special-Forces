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
    private boolean enabled;

    private float shiftY = 0;

    private String text = "Special Forces\n" +
            "\n" +
            "Developed by Middle Games\n" +
            "in\n" +
            "2016 - 2020\n" +
            "\n" +
            "Alex Makhotkin\n" +
            "\n" +
            "LibGDX framework\n" +
            "\n" +
            "Cheat code:\n" +
            "money99999";

    public CreditsText(World world, Loader loader){
        this.world = world;
        label = new Label(loader, text, Font.CONTRACT_COST, Align.center, 0, 0, 0, 0);
        addActor(label);
    }

    @Override
    public void act(float delta) {
        if (!enabled)
            return;
        label.setPosition(world.getCamera().position.x + 3, world.getCamera().position.y - 400 + shiftY);
        shiftY += delta * 43;
        super.act(delta);
    }

    public void enable(boolean enabled){
        this.enabled = enabled;
        shiftY = 0;
    }
}
