package world;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.collision.Ray;

import controller.ControllerType;
import controller.PlayerController;
import engine.Loader;
import engine.SpecialForces;
import engine.utils.Maths;
import stages.World;
import world.gameplay.ItemType;
import world.gameplay.Soldier;
import world.gameplay.WeaponType;
import world.objects.Barrel;
import world.objects.Box;
import world.objects.Decoration;
import world.objects.Enemy;
import world.objects.Item;
import world.objects.MapObject;
import world.objects.Teammate;
import world.objects.bot.Bot;
import world.objects.explosive.Grenade;
import world.objects.explosive.Rocket;
import world.objects.player.Player;

public class Spawner{
	
	private World world;
	private Loader loader;
	private InputMultiplexer multiplexer;
	private ControllerType controllerType;
	private Player player;
	
	public Spawner(World world, Loader loader, InputMultiplexer multiplexer){
		this.world = world;
		this.loader = loader;
		this.multiplexer = multiplexer;
		controllerType = SpecialForces.getInstance().getControllerType();
	}
	
	public void spawnPlayer(float x, float y){
		player = new Player(loader, world.manager().getPlayerInventory(), x, y);
		if (controllerType == ControllerType.KEYS){
			PlayerController playerController = new PlayerController(player);
			player.setController(playerController);
			multiplexer.addProcessor(playerController);
		}
		world.map().registerObject(player);
		create(player);
	}
	
	public void spawnItem(float x, float y, ItemType type, int ammo){
		Item item = new Item(loader, type, ammo, x, y);
		world.objects().addItem(item);
	}
	
	public void spawnBarrel(float x, float y, int type){
		Barrel barrel = new Barrel(loader, x, y, type);
		create(barrel);
	}
	
	public void spawnEnemy(float x, float y, float rotation, float visibleDistance, int health, WeaponType type, int ammo){
		Bot bot = new Enemy(loader, x, y, visibleDistance);
		bot.setRotation(rotation);
		bot.giveAmmo(type, ammo);
		bot.setHealth(health);
		create(bot);
	}
	
	public Teammate spawnTeammate(float x, float y, Soldier soldier){
		Teammate mate = new Teammate(loader, x, y, soldier);
		mate.setRotation(Maths.calcDegrees(player.getCenterX() - mate.getCenterX(), player.getCenterY() - mate.getCenterY()));
		mate.setHealth(50);
		create(mate);
		return mate;
	}
	
	public void spawnDecoration(float x, float y, String name, int health){
		Decoration decoration = new Decoration(loader, x, y, name, health);
		create(decoration);
	}
	
	public void spawnBox(float x, float y){
		Box box = new Box(loader, x, y);
		create(box);
	}
	
	public void spawnRocket(Ray ray, MapObject exception){
		Rocket rocket = new Rocket(loader, ray.origin.x, ray.origin.y, ray.direction.x, ray.direction.y, exception);
		create(rocket);
		spawn();
	}
	
	public void spawnGrenade(Ray ray, MapObject exception){
		Grenade grenade = new Grenade(loader, ray.origin.x, ray.origin.y, ray.direction.x, ray.direction.y, exception);
		create(grenade);
		spawn();
	}
	
	private void create(MapObject obj){
		world.objects().add(obj);
	}
	
	public void spawn(){
		world.objects().spawn();
	}
	
	public Player getPlayer(){
		return player;
	}
}
