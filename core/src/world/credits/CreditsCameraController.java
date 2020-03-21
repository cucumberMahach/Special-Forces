package world.credits;

import stages.World;

public class CreditsCameraController {

    private World world;
    private boolean enabled = false;
    private float elapsed;
    private float movedByY = 0;

    public CreditsCameraController(World world){
        this.world = world;
    }

    public void act(float delta){
        if (!enabled)
            return;
        if (elapsed > 0.1f) {
            elapsed = 0;
            world.moveBy(0, 0.3f);
            movedByY += 0.3f;
        }
        elapsed += delta;
    }

    public void clearAndPrepare(){
        elapsed = 0;
    }

    public void enable(boolean enabled){
        this.enabled = enabled;
        world.moveBy(0, -movedByY);
        movedByY = 0;
    }

    public void stop(){
        this.enabled = false;
    }
}
