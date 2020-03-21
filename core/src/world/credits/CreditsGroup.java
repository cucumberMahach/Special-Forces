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
    private boolean musicPlayed = false;

    private float time = 0;

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
        time += delta;
        if (time >= 46){
            time = 0;
            stop();
        }
        if (!musicPlayed && time > 1f){
            musicPlayed = true;
            SpecialForces.getInstance().sounds().playMusic("credits", false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!enabled)
            return;
        super.draw(batch, parentAlpha);
    }

    public void enable(boolean enable){
        time = 0;
        musicPlayed = false;
        this.enabled = enable;
        text.enable(enable);
        cameraController.enable(enable);
        if (enable) {
            cameraController.clearAndPrepare();
        }
    }

    public void stop(){
        this.enabled = false;
        text.enable(false);
        cameraController.stop();
        world.manager().missionComplete();
    }
}
