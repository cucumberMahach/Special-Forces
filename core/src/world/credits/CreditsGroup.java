package world.credits;

import com.badlogic.gdx.graphics.g2d.Batch;

import engine.Loader;
import engine.SpecialForces;
import stages.World;
import view.Group;

public class CreditsGroup extends Group {

    private World world;
    private CreditsText text;
    private CreditsCameraController cameraController;
    private boolean enabled = false;

    public CreditsGroup(World world, Loader loader){
        this.world = world;
        text = new CreditsText(world, loader);
        cameraController = new CreditsCameraController(world);
        addActor(text);
    }

    @Override
    public void act(float delta) {
        if (!enabled)
            return;
        super.act(delta);
        cameraController.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!enabled)
            return;
        super.draw(batch, parentAlpha);
    }

    public void enable(boolean enable){
        this.enabled = enable;
        if (enable) {
            cameraController.clearAndPrepare();
            SpecialForces.getInstance().sounds().playMusic("credits", false);
        }
    }
}
