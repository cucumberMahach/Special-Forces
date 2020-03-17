package world.credits;

import stages.World;

public class CreditsCameraController {

    private World world;
    private boolean started = false;
    private float elapsed;

    public CreditsCameraController(World world){
        this.world = world;
    }

    public void act(float delta){
        if (!started){
            started = true;
            start();
        }
        if (elapsed > 0.1f) {
            elapsed = 0;
            world.moveBy(0, 0.3f);
        }
        elapsed += delta;
    }

    public void start(){

    }

    public void clearAndPrepare(){
        started = false;
        elapsed = 0;
    }
}
