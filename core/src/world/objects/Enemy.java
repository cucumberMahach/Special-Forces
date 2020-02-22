package world.objects;

import com.badlogic.gdx.math.MathUtils;

import engine.Loader;
import world.objects.bot.Bot;
import world.objects.bot.AI.behavior.EnemyBehavior;

public class Enemy extends Bot{

	public Enemy(Loader loader, float x, float y, float visibleDistance) {
		super(loader, "skin".concat(String.valueOf(MathUtils.random(1, 6))), true, x, y);
		setType(ObjectType.ENEMY);
		setEnergy(0);
		ai().addBehavior(new EnemyBehavior(visibleDistance));
	}

}
