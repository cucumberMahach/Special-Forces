package world.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

import javax.xml.bind.util.ValidationEventCollector;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Maths;
import stages.World;
import view.Group;
import view.Image;
import world.gameplay.Shoot;
import world.objects.ObjectType;
import world.objects.character.Pose;

public class Aim extends Group {

    private World world;
    private Loader loader;
    private Image aimImg;

    public Aim(World world, Loader loader){
        this.world = world;
        this.loader = loader;
        aimImg = new Image(loader.getHud("aim"), 0, 0, true);
        aimImg.setNaturalScale(0.35f);
        aimImg.setColor(1, 1, 1, 0.5f);
        addActor(aimImg);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (world.getPlayer().getWeapon().getPose() == Pose.STAND){
            aimImg.setVisible(false);
        }else{
            aimImg.setVisible(true);
        }
        Ray ray = world.getPlayer().getRay();
        Shoot s = world.physics().checkShoot(ray, world.getPlayer());

        if (!s.isWall()){
            switch(s.object.getType()){
                case ENEMY:
                    aimImg.setColor(1,0,0,0.5f);
                    break;
                case TEAMMATE:
                    aimImg.setColor(0,1,0,0.5f);
                    break;
                case DEFAULT:
                    aimImg.setColor(1,1,1,0.5f);
                    break;
            }
        }else{
            aimImg.setColor(1,1,1,0.5f);
        }
        aimImg.setScale(1f/world.getOrtCam().zoom);
        aimImg.setPosition((s.hitX - aimImg.getWidth()/4f - world.getCamera().position.x) / world.getOrtCam().zoom + SpecialForces.WIDTH/2f, (s.hitY - aimImg.getHeight()/4f - world.getCamera().position.y) / world.getOrtCam().zoom + SpecialForces.HEIGHT/2f);
        aimImg.rotateBy(1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
