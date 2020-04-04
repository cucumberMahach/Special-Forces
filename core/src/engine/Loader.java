package engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

import view.Font;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;
import world.objects.character.Pose;

import static engine.Style.MAPTILES_COUNT;

public class Loader {

	public static String DESKTOP_DIRECTORY = ""; // if debug change to android/assets/ if build change to "" (desktop build to jar - desktop:dist)
	public static String DIRECTORY;

	private final String FONT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
	private AssetManager assetManager;

	private HashMap<String, TextureRegion> backgrounds;
	private HashMap<String, TextureRegion> captions;
	private HashMap<String, TextureRegion> others;
	private EnumMap<Font, BitmapFont> fonts;
	private HashMap<String, TextureRegion[]> characters;
	private HashMap<String, TextureRegion> hud;
	private TextureRegion weaponsIcons[];
	private TextureRegion weaponsTiles[];
	private TextureRegion items[];
	private HashMap<String, TextureRegion> objects;
	private HashMap<String, TextureRegion[]> dynamicParticles;
	private HashMap<String, Sound> sounds;
	private HashMap<String, TextureRegion> particles;
	private HashMap<String, TextureRegion[]> animations;
	private HashMap<String, TextureRegion> gui;
	private HashMap<String, TextureRegion> icons;
	private HashMap<String, Music> music;
	private ArrayList<TextureRegion> faces;
	private ArrayList<String> names;
	private TextureRegion mapTiles[];
	private TextureRegion black;
	private Weapon weapons[];
	private HashMap<String, Cursor> cursors;

	public Loader() {
		DIRECTORY = Gdx.app.getType() == Application.ApplicationType.Desktop ? DESKTOP_DIRECTORY : "";
		assetManager = new AssetManager();
		backgrounds = new HashMap<String, TextureRegion>();
		captions = new HashMap<String, TextureRegion>();
		others = new HashMap<String, TextureRegion>();
		faces = new ArrayList<TextureRegion>();
		names = new ArrayList<String>();
		music = new HashMap<String, Music>();
		dynamicParticles = new HashMap<String, TextureRegion[]>();
		cursors = new HashMap<>();

		fonts = new EnumMap<Font, BitmapFont>(Font.class);
		characters = new HashMap<String, TextureRegion[]>();
		hud = new HashMap<String, TextureRegion>();
		objects = new HashMap<String, TextureRegion>();
		sounds = new HashMap<String, Sound>();
		weaponsIcons = new TextureRegion[8];
		weaponsTiles = new TextureRegion[8];
		weapons = new Weapon[8];
		particles = new HashMap<String, TextureRegion>();
		animations = new HashMap<String, TextureRegion[]>();
		items = new TextureRegion[2];
		gui = new HashMap<String, TextureRegion>();
		icons = new HashMap<String, TextureRegion>();
		addFiles();
	}

	private void addRecursionFiles(String directory, ArrayList<String> list) {
		String[] files = Gdx.files.internal(directory).file().list();
		String newDir;
		for (String name : files) {
			newDir = directory + name;
			if (Gdx.files.internal(newDir).isDirectory()) {
				addRecursionFiles(newDir + "/", list);
			} else {
				if (newDir.endsWith("mp3") || newDir.endsWith("jpg") || newDir.endsWith("png"))
					list.add(newDir);
			}
		}
	}

	@SuppressWarnings("unused")
	private ArrayList<String> getAllFiles(String directory) {
		ArrayList<String> list = new ArrayList<String>();
		addRecursionFiles(directory, list);
		return list;
	}

	private void addFiles() {
		/*
		 * ArrayList<String> files = getAllFiles(DIRECTORY); for (String file :
		 * files) assetManager.load(file, file.endsWith("mp3") ? Sound.class :
		 * Texture.class);
		 */
		assetManager.load(DIRECTORY + "gfx/cursor_aim.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/cursor_arrow.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/fire.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/fx.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/feets.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/window.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/areaComplete.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/missionComplete.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/contracts.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/dynamic.jpg", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/load.jpg", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/map0.jpg", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/map1.jpg", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/map2.jpg", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/ready.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/backgrounds/weaponStat.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/captions.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/contractInfo.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/faces.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/gui.png", Texture.class);

		assetManager.load(DIRECTORY + "gfx/gui/editorHelp_android.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/editorHelp_pc.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/gameHelp_android.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/gameHelp_pc.png", Texture.class);

		assetManager.load(DIRECTORY + "gfx/gui/icons.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/shadow.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/soldier1.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/soldier2.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/soldier3.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/soldier4.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/gui/weaponInfo.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/hud.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/icons.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/items.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/mapTiles.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/objects.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/particles.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/player.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin1.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin2.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin3.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin4.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin5.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin6.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/skin7.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/weapons.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/weaponsTiles.png", Texture.class);

		assetManager.load(DIRECTORY + "gfx/aim.png", Texture.class);
		assetManager.load(DIRECTORY + "gfx/black.png", Texture.class);

		assetManager.load(DIRECTORY + "sounds/character/damage1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/damage2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/damage3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death5.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/death6.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/exploded1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/character/exploded2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/click.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/flip.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/items/ammo.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/items/energy.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/items/medkit.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/impact1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/impact2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/impact3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/impact4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/wood1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/wood2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/materials/wood3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/dirt1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/dirt2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/dirt3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/dirt4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/grass1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/grass2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/grass3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/grass4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/step1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/step2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/step3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/step4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/tile1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/tile2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/tile3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/tile4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/wood1.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/wood2.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/wood3.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/steps/wood4.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/ak47.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/ak47_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/bazooka.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/bazooka_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/beretta.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/beretta_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/bounce.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/colt.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/colt_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/empty.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/explode.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/grenade.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/grenade_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/mp5.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/mp5_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/shotgun.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/shotgun_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/sniper.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/sniper_reload.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/complete.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/end.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/cash.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/exp.mp3", Sound.class);
		assetManager.load(DIRECTORY + "sounds/weapons/sniper_change.mp3", Sound.class);
		assetManager.load(DIRECTORY + "music/menu.mp3", Music.class);
		assetManager.load(DIRECTORY + "music/credits.mp3", Music.class);
	}

	public void loadPrev() {
		assetManager.finishLoadingAsset(DIRECTORY + Style.GRAPHICS_DIRECTORY + "gui/backgrounds/load.jpg");
		assetManager.finishLoadingAsset(DIRECTORY + Style.GRAPHICS_DIRECTORY + "gui/gui.png");
		backgrounds.put("load", new TextureRegion(getTexture("gui/backgrounds/load.jpg")));
		Texture map = getTexture("gui/gui.png");
		gui.put("bar_load", new TextureRegion(map, 1040, 403, 542, 41));
		gui.put("bar_load_back", new TextureRegion(map, 1107, 444, 565, 61));
	}

	public void load() {
		Texture map;
		TextureRegion[] regions;

		// backgrounds
		backgrounds.put("areaComplete", new TextureRegion(getTexture("gui/backgrounds/areaComplete.png")));
		backgrounds.put("missionComplete", new TextureRegion(getTexture("gui/backgrounds/missionComplete.png")));
		backgrounds.put("contracts", new TextureRegion(getTexture("gui/backgrounds/contracts.png")));
		backgrounds.put("dynamic", new TextureRegion(getTexture("gui/backgrounds/dynamic.jpg")));
		backgrounds.put("map0", new TextureRegion(getTexture("gui/backgrounds/map0.jpg")));
		backgrounds.put("map1", new TextureRegion(getTexture("gui/backgrounds/map1.jpg")));
		backgrounds.put("map2", new TextureRegion(getTexture("gui/backgrounds/map2.jpg")));
		backgrounds.put("weaponStat", new TextureRegion(getTexture("gui/backgrounds/weaponStat.png")));
		backgrounds.put("ready", new TextureRegion(getTexture("gui/backgrounds/ready.png")));
		backgrounds.put("window", new TextureRegion(getTexture("gui/backgrounds/window.png")));

		// captions
		map = getTexture("gui/captions.png");
		captions.put("title", new TextureRegion(map, 0, 0, 604, 215));
		captions.put("help", new TextureRegion(map, 604, 0, 376, 97));

		// gui
		map = getTexture("gui/gui.png");
		gui.put("button_icon_menu", new TextureRegion(map, 0, 0, 100, 92));
		gui.put("button_text_menu", new TextureRegion(map, 100, 0, 394, 92));
		gui.put("button_manager", new TextureRegion(map, 0, 92, 153, 140));
		gui.put("button_small_yellow", new TextureRegion(map, 153, 92, 69, 63));
		gui.put("button_small_green", new TextureRegion(map, 222, 92, 69, 63));
		gui.put("button_small_red", new TextureRegion(map, 153, 155, 69, 63));
		gui.put("button_weapon", new TextureRegion(map, 0, 232, 214, 111));
		gui.put("button_contract", new TextureRegion(map, 215, 232, 242, 84));
		gui.put("button_location", new TextureRegion(map, 0, 343, 256, 73));
		gui.put("button_dialog", new TextureRegion(map, 256, 316, 283, 92));
		gui.put("bar_experience", new TextureRegion(map, 0, 416, 414, 28));
		gui.put("bar_experience_back", new TextureRegion(map, 0, 444, 433, 41));
		gui.put("bar_energy", new TextureRegion(map, 414, 416, 173, 28));
		gui.put("bar_energy_back", new TextureRegion(map, 433, 444, 189, 41));
		gui.put("bar_contractStat", new TextureRegion(map, 587, 416, 131, 28));
		gui.put("bar_contractStat_back", new TextureRegion(map, 622, 445, 146, 40));
		gui.put("bar_weaponLevel", new TextureRegion(map, 718, 416, 321, 28));
		gui.put("bar_weaponLevel_back", new TextureRegion(map, 768, 444, 338, 41));
		gui.put("back_contract", new TextureRegion(map, 0, 485, 306, 408));
		gui.put("iconBack_contract", new TextureRegion(map, 306, 485, 147, 129));

		// icons
		map = getTexture("gui/icons.png");

		icons.put("black_sounds", new TextureRegion(map, 1, 0, 72, 62));
		icons.put("black_sounds_off", new TextureRegion(map, 0, 4, 34, 54));
		icons.put("black_help", new TextureRegion(map, 74, 0, 44, 66));
		icons.put("black_team", new TextureRegion(map, 119, 0, 93, 68));
		icons.put("black_inventory", new TextureRegion(map, 212, 1, 98, 54));
		icons.put("black_missions", new TextureRegion(map, 310, 0, 67, 68));
		icons.put("black_music", new TextureRegion(map, 377, 0, 29, 37));
		icons.put("black_close", new TextureRegion(map, 407, 1, 31, 31));
		icons.put("black_location", new TextureRegion(map, 438, 0, 34, 49));
		icons.put("black_plus", new TextureRegion(map, 472, 0, 36, 36));
		icons.put("black_music_off", new TextureRegion(map, 472, 36, 36, 39));
		icons.put("goldBar", new TextureRegion(map, 0, 78, 98, 79));
		icons.put("dollars", new TextureRegion(map, 99, 76, 101, 81));
		icons.put("star", new TextureRegion(map, 200, 83, 77, 74));
		icons.put("lightning", new TextureRegion(map, 277, 92, 46, 65));
		icons.put("threeStars", new TextureRegion(map, 323, 85, 74, 72));
		icons.put("addSoldier", new TextureRegion(map, 0, 157, 131, 131));
		icons.put("markLocked", new TextureRegion(map, 0, 288, 76, 151));
		icons.put("markAvailable", new TextureRegion(map, 76, 288, 76, 151));
		icons.put("markCompleted", new TextureRegion(map, 152, 288, 76, 151));
		icons.put("medal0", new TextureRegion(map, 0, 439, 307, 351));
		icons.put("medal1", new TextureRegion(map, 307, 439, 307, 351));
		icons.put("medal2", new TextureRegion(map, 614, 439, 307, 351));
		icons.put("fullscreen", new TextureRegion(map, 857, 0, 64, 64));
		icons.put("code", new TextureRegion(map, 508, 0, 44, 37));

		// others
		others.put("editorHelp_android", new TextureRegion(getTexture("gui/editorHelp_android.png")));
		others.put("editorHelp_pc", new TextureRegion(getTexture("gui/editorHelp_pc.png")));
		others.put("gameHelp_android", new TextureRegion(getTexture("gui/gameHelp_android.png")));
		others.put("gameHelp_pc", new TextureRegion(getTexture("gui/gameHelp_pc.png")));

		others.put("shadow", new TextureRegion(getTexture("gui/shadow.png")));
		others.put("soldier1", new TextureRegion(getTexture("gui/soldier1.png")));
		others.put("soldier2", new TextureRegion(getTexture("gui/soldier2.png")));
		others.put("soldier3", new TextureRegion(getTexture("gui/soldier3.png")));
		others.put("soldier4", new TextureRegion(getTexture("gui/soldier4.png")));
		others.put("weaponInfo", new TextureRegion(getTexture("gui/weaponInfo.png")));
		others.put("contractInfo", new TextureRegion(getTexture("gui/contractInfo.png")));

		// faces
		map = getTexture("gui/faces.png");

		faces.add(new TextureRegion(map, 0, 0, 132, 116));
		faces.add(new TextureRegion(map, 135, 0, 136, 117));
		faces.add(new TextureRegion(map, 271, 0, 140, 120));
		faces.add(new TextureRegion(map, 411, 0, 135, 115));
		faces.add(new TextureRegion(map, 0, 115, 127, 116));
		faces.add(new TextureRegion(map, 127, 117, 125, 115));
		faces.add(new TextureRegion(map, 252, 120, 132, 115));
		faces.add(new TextureRegion(map, 384, 120, 131, 116));
		faces.add(new TextureRegion(map, 0, 232, 131, 116));
		faces.add(new TextureRegion(map, 127, 236, 123, 114));
		faces.add(new TextureRegion(map, 250, 236, 133, 116));
		faces.add(new TextureRegion(map, 383, 236, 125, 117));
		faces.add(new TextureRegion(map, 0, 348, 126, 117));

		// characters
		map = getTexture("player.png");
		regions = new TextureRegion[3];
		regions[0] = new TextureRegion(map, 0, 0, 154, 176);
		regions[1] = new TextureRegion(map, 154, 0, 153, 199);
		regions[2] = new TextureRegion(map, 307, 0, 157, 139);
		characters.put("player", regions);

		for (int i = 1; i < 8; i++) {
			String name = "skin".concat(String.valueOf(i));
			map = getTexture(name.concat(".png"));
			regions = new TextureRegion[3];
			regions[0] = new TextureRegion(map, 0, 0, 154, 176);
			regions[1] = new TextureRegion(map, 154, 0, 153, 199);
			regions[2] = new TextureRegion(map, 307, 0, 157, 139);
			characters.put(name, regions);
		}

		// mapTiles
		map = getTexture("mapTiles.png");
		int widthCount = map.getWidth() / Style.LOAD_TILE_SIZE;
		int heightCount = map.getHeight() / Style.LOAD_TILE_SIZE;
		int i = 0;
		mapTiles = new TextureRegion[MAPTILES_COUNT];
		ex1: for (int y = 0; y < heightCount; y++) {
			for (int x = 0; x < widthCount; x++) {
				mapTiles[i] = new TextureRegion(map, x * Style.LOAD_TILE_SIZE, y * Style.LOAD_TILE_SIZE,
						Style.LOAD_TILE_SIZE, Style.LOAD_TILE_SIZE);
				if (i == MAPTILES_COUNT - 1)
					break ex1;
				i++;
			}
		}

		black = new TextureRegion(getTexture("black.png"));

		// objects
		map = getTexture("objects.png");
		objects.put("box", new TextureRegion(map, 0, 0, 32, 32));
		objects.put("barrelStand", new TextureRegion(map, 32, 0, 32, 32));
		objects.put("barrelFall", new TextureRegion(map, 64, 0, 42, 32));
		objects.put("boxTnt", new TextureRegion(map, 106, 0, 32, 32));
		objects.put("bed", new TextureRegion(map, 0, 32, 32, 63));
		objects.put("ammoBox", new TextureRegion(map, 138, 0, 32, 32));
		objects.put("tree1", new TextureRegion(map, 0, 347, 113, 104));
		objects.put("tree2", new TextureRegion(map, 113, 341, 110, 110));
		objects.put("palm1", new TextureRegion(map, 223, 323, 127, 128));
		objects.put("palm2", new TextureRegion(map, 350, 332, 120, 119));
		objects.put("deadtree", new TextureRegion(map, 470, 345, 121, 106));
		objects.put("pallet", new TextureRegion(map, 32, 32, 64, 64));
		objects.put("metalBox", new TextureRegion(map, 96, 32, 32, 32));
		objects.put("woodbarrel", new TextureRegion(map, 415, 0, 36, 35));
		objects.put("stones", new TextureRegion(map, 451, 0, 140, 114));

		// items
		map = getTexture("items.png");
		items[0] = new TextureRegion(map, 0, 0, 44, 32);
		items[1] = new TextureRegion(map, 44, 0, 44, 32);

		// hud
		map = getTexture("hud.png");
		hud.put("weapons", new TextureRegion(map, 0, 0, 665, 184));
		hud.put("stats", new TextureRegion(map, 0, 184, 276, 66));
		hud.put("ammo", new TextureRegion(map, 276, 184, 227, 102));
		hud.put("selector", new TextureRegion(map, 503, 184, 97, 63));

		hud.put("socket", new TextureRegion(map, 0, 250, 229, 230));
		hud.put("btnArrow", new TextureRegion(map, 230, 287, 163, 163));
		hud.put("btnReload", new TextureRegion(map, 393, 287, 113, 114));
		hud.put("btnShoot", new TextureRegion(map, 506, 287, 114, 114));
		hud.put("btnMove", new TextureRegion(map, 393, 401, 83, 83));

		hud.put("aim", new TextureRegion(getTexture("aim.png")));

		// weaponsIcons
		map = getTexture("weapons.png");
		weaponsIcons[0] = new TextureRegion(map, 0, 0, 70, 39);
		weaponsIcons[1] = new TextureRegion(map, 0, 39, 68, 41);
		weaponsIcons[2] = new TextureRegion(map, 0, 80, 68, 40);
		weaponsIcons[3] = new TextureRegion(map, 0, 120, 132, 43);
		weaponsIcons[4] = new TextureRegion(map, 0, 163, 131, 42);
		weaponsIcons[5] = new TextureRegion(map, 0, 205, 138, 35);
		weaponsIcons[6] = new TextureRegion(map, 0, 240, 131, 32);
		weaponsIcons[7] = new TextureRegion(map, 0, 272, 28, 45);

		// weaponsTiles
		map = getTexture("weaponsTiles.png");
		weaponsTiles[0] = new TextureRegion(map, 0, 0, 16, 73);
		weaponsTiles[1] = new TextureRegion(map, 16, 0, 17, 66);
		weaponsTiles[2] = new TextureRegion(map, 33, 0, 20, 120);
		weaponsTiles[3] = new TextureRegion(map, 53, 0, 20, 226);
		weaponsTiles[4] = new TextureRegion(map, 73, 0, 24, 170);
		weaponsTiles[5] = new TextureRegion(map, 97, 0, 25, 235);
		weaponsTiles[6] = new TextureRegion(map, 122, 0, 54, 301);
		weaponsTiles[7] = new TextureRegion(map, 176, 0, 44, 40);

		// particles
		map = getTexture("particles.png");
		particles.put("case0", new TextureRegion(map, 0, 0, 28, 57));
		particles.put("case1", new TextureRegion(map, 27, 0, 28, 57));
		particles.put("case2", new TextureRegion(map, 56, 0, 59, 75));
		particles.put("case3", new TextureRegion(map, 115, 0, 69, 89));
		particles.put("case4", new TextureRegion(map, 184, 0, 74, 69));
		particles.put("case5", new TextureRegion(map, 258, 0, 39, 37));
		particles.put("bullet0", new TextureRegion(map, 0, 57, 14, 26));
		particles.put("bullet1", new TextureRegion(map, 14, 57, 11, 49));
		particles.put("bullet2", new TextureRegion(map, 25, 57, 16, 39));
		particles.put("bullet3", new TextureRegion(map, 41, 57, 11, 77));
		particles.put("bullet4", new TextureRegion(map, 52, 75, 17, 43));
		particles.put("bullet5", new TextureRegion(map, 69, 75, 27, 97));
		particles.put("wood", new TextureRegion(map, 96, 89, 28, 31));
		particles.put("blood0", new TextureRegion(map, 0, 83, 9, 12));
		particles.put("blood1", new TextureRegion(map, 0, 96, 12, 10));
		particles.put("blood2", new TextureRegion(map, 0, 108, 33, 33));
		particles.put("blood3", new TextureRegion(map, 0, 143, 25, 21));
		particles.put("blood4", new TextureRegion(map, 25, 143, 9, 24));
		particles.put("body0", new TextureRegion(map, 34, 134, 21, 33));
		particles.put("body1", new TextureRegion(map, 0, 167, 33, 41));
		particles.put("body2", new TextureRegion(map, 33, 167, 19, 41));

		// fx
		map = getTexture("fx.png");
		particles.put("smoke", new TextureRegion(map, 0, 0, 300, 279));
		particles.put("fire", new TextureRegion(map, 300, 0, 300, 294));

		// animations
		map = getTexture("fire.png");
		TextureRegion[] fire;
		int width = 300, height = 225;
		widthCount = map.getWidth() / width;
		heightCount = map.getHeight() / height;
		i = 0;
		fire = new TextureRegion[widthCount * heightCount];
		for (int y = 0; y < heightCount; y++) {
			for (int x = 0; x < widthCount; x++) {
				fire[i] = new TextureRegion(map, x * width, y * height, width, height);
				i++;
			}
		}
		animations.put("explosion", fire);

		map = getTexture("feets.png");
		TextureRegion[] feets;
		width = 204;
		height = 124;
		widthCount = map.getWidth() / width;
		heightCount = map.getHeight() / height;
		i = 0;
		feets = new TextureRegion[widthCount * heightCount];
		for (int y = 0; y < heightCount; y++) {
			for (int x = 0; x < widthCount; x++) {
				feets[i] = new TextureRegion(map, x * width, y * height, width, height);
				i++;
			}
		}
		animations.put("feets", feets);

		// sounds
		sounds.put("flip", getSound("flip.mp3"));
		sounds.put("click", getSound("click.mp3"));
		sounds.put("shoot0", getSound("weapons/beretta.mp3"));
		sounds.put("shoot1", getSound("weapons/colt.mp3"));
		sounds.put("shoot2", getSound("weapons/mp5.mp3"));
		sounds.put("shoot3", getSound("weapons/ak47.mp3"));
		sounds.put("shoot4", getSound("weapons/shotgun.mp3"));
		sounds.put("shoot5", getSound("weapons/sniper.mp3"));
		sounds.put("shoot6", getSound("weapons/bazooka.mp3"));
		sounds.put("shoot7", getSound("weapons/grenade.mp3"));
		sounds.put("reload0", getSound("weapons/beretta_reload.mp3"));
		sounds.put("reload1", getSound("weapons/colt_reload.mp3"));
		sounds.put("reload2", getSound("weapons/mp5_reload.mp3"));
		sounds.put("reload3", getSound("weapons/ak47_reload.mp3"));
		sounds.put("reload4", getSound("weapons/shotgun_reload.mp3"));
		sounds.put("reload5", getSound("weapons/sniper_reload.mp3"));
		sounds.put("reload6", getSound("weapons/bazooka_reload.mp3"));
		sounds.put("reload7", getSound("weapons/grenade_reload.mp3"));
		sounds.put("empty", getSound("weapons/empty.mp3"));
		sounds.put("explode", getSound("weapons/explode.mp3"));
		sounds.put("bounce", getSound("weapons/bounce.mp3"));
		sounds.put("dirt1", getSound("steps/dirt1.mp3"));
		sounds.put("dirt2", getSound("steps/dirt2.mp3"));
		sounds.put("dirt3", getSound("steps/dirt3.mp3"));
		sounds.put("dirt4", getSound("steps/dirt4.mp3"));
		sounds.put("grass1", getSound("steps/grass1.mp3"));
		sounds.put("grass2", getSound("steps/grass2.mp3"));
		sounds.put("grass3", getSound("steps/grass3.mp3"));
		sounds.put("grass4", getSound("steps/grass4.mp3"));
		sounds.put("step1", getSound("steps/step1.mp3"));
		sounds.put("step2", getSound("steps/step2.mp3"));
		sounds.put("step3", getSound("steps/step3.mp3"));
		sounds.put("step4", getSound("steps/step4.mp3"));
		sounds.put("tile1", getSound("steps/tile1.mp3"));
		sounds.put("tile2", getSound("steps/tile2.mp3"));
		sounds.put("tile3", getSound("steps/tile3.mp3"));
		sounds.put("tile4", getSound("steps/tile4.mp3"));
		sounds.put("wood1", getSound("steps/wood1.mp3"));
		sounds.put("wood2", getSound("steps/wood2.mp3"));
		sounds.put("wood3", getSound("steps/wood3.mp3"));
		sounds.put("wood4", getSound("steps/wood4.mp3"));
		sounds.put("m_wood1", getSound("materials/wood1.mp3"));
		sounds.put("m_wood2", getSound("materials/wood2.mp3"));
		sounds.put("m_wood3", getSound("materials/wood3.mp3"));
		sounds.put("impact1", getSound("materials/impact1.mp3"));
		sounds.put("impact2", getSound("materials/impact2.mp3"));
		sounds.put("impact3", getSound("materials/impact3.mp3"));
		sounds.put("impact4", getSound("materials/impact4.mp3"));
		sounds.put("ammo", getSound("items/ammo.mp3"));
		sounds.put("medkit", getSound("items/medkit.mp3"));
		sounds.put("energy", getSound("items/energy.mp3"));
		sounds.put("damage1", getSound("character/damage1.mp3"));
		sounds.put("damage2", getSound("character/damage2.mp3"));
		sounds.put("damage3", getSound("character/damage3.mp3"));
		sounds.put("death1", getSound("character/death1.mp3"));
		sounds.put("death2", getSound("character/death2.mp3"));
		sounds.put("death3", getSound("character/death3.mp3"));
		sounds.put("death4", getSound("character/death4.mp3"));
		sounds.put("death5", getSound("character/death5.mp3"));
		sounds.put("death6", getSound("character/death6.mp3"));
		sounds.put("exploded1", getSound("character/exploded1.mp3"));
		sounds.put("exploded2", getSound("character/exploded2.mp3"));
		sounds.put("exp", getSound("exp.mp3"));
		sounds.put("complete", getSound("complete.mp3"));
		sounds.put("end", getSound("end.mp3"));
		sounds.put("cash", getSound("cash.mp3"));
		sounds.put("change5", getSound("weapons/sniper_change.mp3"));

		// music
		music.put("menu", getMusic("menu.mp3"));
		music.put("credits", getMusic("credits.mp3"));

		// icons
		map = getTexture("icons.png");

		icons.put("zone", new TextureRegion(map, 0, 0, 32, 32));
		icons.put("arrows", new TextureRegion(map, 32, 0, 57, 57));
		icons.put("plus", new TextureRegion(map, 89, 0, 36, 36));
		icons.put("minus", new TextureRegion(map, 125, 0, 36, 10));

		// fonts
		loadFonts();

		// weaponsConfig
		loadWeaponsConfig();

		// cursor
		loadCursors();

		// names
		loadNames();

		// dynamic particles
		createDynamicParticles();
	}

	private void createDynamicParticles() {
		Set<String> keys = objects.keySet();
		for (String key : keys) {
			TextureRegion[] particles = new TextureRegion[Style.DYNAMIC_PARTICLES];
			for (int i = 0; i < Style.DYNAMIC_PARTICLES; i++) {
				final TextureRegion obj = objects.get(key);
				final int w = obj.getRegionWidth();
				final int h = obj.getRegionHeight();
				final int pw = (int) (MathUtils.random(8, Style.DYN_PARTICLE_MAX_WIDTH
						/ 2)/* * ((float) w / Style.TILE_SIZE) */);
				final int ph = (int) (MathUtils.random(8, Style.DYN_PARTICLE_MAX_HEIGHT
						/ 2)/* * ((float) h / Style.TILE_SIZE) */);
				final int x = MathUtils.random(w - pw);
				final int y = MathUtils.random(h - ph);
				final TextureRegion particle = new TextureRegion(obj, x, y, pw, ph);
				particles[i] = particle;
			}
			dynamicParticles.put(key, particles);
		}
	}

	private void loadNames() {
		names.add("mark");
		names.add("james");
		names.add("alex");
		names.add("michael");
		names.add("john");
		names.add("jason");
		names.add("richard");
		names.add("patrick");
		names.add("steve");
		names.add("eric");
		names.add("samuel");
		names.add("connor");
		names.add("thomas");
		names.add("vadim");
		names.add("adam");
		names.add("bryan");
		names.add("petya");
		names.add("robert");
		names.add("anthony");
		names.add("tony");
		names.add("joseph");
		names.add("ryan");
		names.add("jake");
	}

	private void loadCursors() {
		cursors.put("aim", generateCursor(getTexture("cursor_aim.png"), 32, 32, 16, 16));
		cursors.put("arrow", generateCursor(getTexture("cursor_arrow.png"), 26, 38, 0, 0));
	}

	private Cursor generateCursor(Texture texture, int width, int height, int xHotspot, int yHotspot){
		Pixmap pix;

		texture.getTextureData().prepare();
		pix = texture.getTextureData().consumePixmap();

		Cursor cursor = Gdx.graphics.newCursor(pix, xHotspot, yHotspot);
		pix.dispose();

		return cursor;
	}

	private void loadWeaponsConfig() {
		// maxCaseAmmo maxAmmo shootDelay reloadTime damage weapPrice ammoPrice
		// handleX handleY shotX shotY Texture Pose
		weapons[0] = new Weapon(WeaponType.Beretta, 13, 80, 0.35f, 1, 8, 800, 50, -8, -8, 10, 70, weaponsTiles[0],
				Pose.HAND);
		weapons[1] = new Weapon(WeaponType.Colt, 7, 80, 0.40f, 1, 12, 1400, 80, -8, -8, 10, 60, weaponsTiles[1],
				Pose.HAND);
		weapons[2] = new Weapon(WeaponType.MP5, 30, 210, 0.09f, 1, 10, 4900, 120, -5, -25, 10, 115, weaponsTiles[2],
				Pose.ARM);
		weapons[3] = new Weapon(WeaponType.AK47, 30, 210, 0.12f, 1, 20, 6200, 200, -5, -90, 10, 220, weaponsTiles[3],
				Pose.ARM);
		weapons[4] = new Weapon(WeaponType.Shotgun, 7, 76, 0.5f, 1, 13, 3100, 180, -5, -70, 12, 165, weaponsTiles[4],
				Pose.ARM);
		weapons[5] = new Weapon(WeaponType.Sniper, 10, 40, 1.5f, 1, 50, 5000, 300, -10, -105, 14, 230, weaponsTiles[5],
				Pose.ARM);
		weapons[6] = new Weapon(WeaponType.Bazooka, 1, 10, 0.5f, 1, 200, 7000, 1000, -10, -170, 15, 250,
				weaponsTiles[6], Pose.ARM);
		weapons[7] = new Weapon(WeaponType.Grenade, 1, 10, 1f, 1, 200, 1000, 800, -20, -15, 20, 22, weaponsTiles[7],
				Pose.HAND);
	}

	private void loadFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(openFile("fonts/bigNoodle.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = FONT_CHARS;
		parameter.size = 60;
		parameter.color = Color.BLACK;
		fonts.put(Font.BIG_BUTTON, generator.generateFont(parameter));
		parameter.size = 40;
		fonts.put(Font.SMALL_BUTTON, generator.generateFont(parameter));
		parameter.size = 35;
		parameter.color = Color.WHITE;
		fonts.put(Font.SMALL, generator.generateFont(parameter));
		generator.dispose();

		generator = new FreeTypeFontGenerator(openFile("fonts/bebasKai.otf"));
		parameter = new FreeTypeFontParameter();
		parameter.characters = FONT_CHARS;
		parameter.size = 40;
		parameter.color = Color.WHITE;
		fonts.put(Font.CONTRACT_NAME, generator.generateFont(parameter));
		parameter.size = 60;
		fonts.put(Font.DEFAULT, generator.generateFont(parameter));
		parameter.color = new Color(0.45f, 0.84f, 1, 1);
		fonts.put(Font.EXPERIENCE_BIG, generator.generateFont(parameter));
		parameter.color = Color.WHITE;
		parameter.size = 40;
		fonts.put(Font.MARK, generator.generateFont(parameter));
		parameter.size = 75;
		parameter.color = Color.LIGHT_GRAY;
		parameter.borderWidth = 4;
		parameter.borderColor = Color.BLACK;
		fonts.put(Font.CONTRACT_COST, generator.generateFont(parameter));
		parameter.size = 40;
		parameter.characters = "0123456789/";
		parameter.borderWidth = 3;
		fonts.put(Font.NUMBERS, generator.generateFont(parameter));
		parameter.size = 40;
		parameter.color = new Color(0.85f, 0.78f, 0, 1);
		fonts.put(Font.NUMBERS_YELLOW, generator.generateFont(parameter));
		parameter.size = 30;
		fonts.put(Font.NUMBERS_WEAPON_STAT, generator.generateFont(parameter));
		parameter.size = 26;
		fonts.put(Font.CONTRACT_STAT, generator.generateFont(parameter));
		parameter.size = 26;
		parameter.color = new Color(0.09f, 0.69f, 0.74f, 1);
		parameter.borderWidth = 2;
		fonts.put(Font.EXPERIENCE, generator.generateFont(parameter));
		generator.dispose();

		generator = new FreeTypeFontGenerator(openFile("fonts/raaviBold.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.characters = "0123456789/";
		parameter.size = 35;
		parameter.color = new Color(0.45f, 0.84f, 1, 1);
		parameter.borderWidth = 2;
		parameter.borderColor = Color.BLACK;
		fonts.put(Font.LEVEL, generator.generateFont(parameter));
		generator.dispose();

		generator = new FreeTypeFontGenerator(openFile("fonts/fontNumbers.otf"));
		parameter = new FreeTypeFontParameter();
		parameter.characters = "0123456789";
		parameter.size = 46;
		fonts.put(Font.NUMERIC46, generator.generateFont(parameter));
		parameter.size = 32;
		fonts.put(Font.NUMERIC32, generator.generateFont(parameter));
		generator.dispose();
	}

	public TextureRegion[] getDynamicParticles(String key) {
		return dynamicParticles.get(key);
	}

	public FileHandle getFileHandle(String filename) {
		return Gdx.files.internal(Loader.DIRECTORY + filename);
	}

	public String getRandomName() {
		return names.get(MathUtils.random(names.size() - 1));
	}

	public int getFacesCount() {
		return faces.size();
	}

	public TextureRegion getFace(int index) {
		return faces.get(index);
	}

	public TextureRegion getCaption(String key) {
		return captions.get(key);
	}

	public TextureRegion getOther(String key) {
		return others.get(key);
	}

	public void saveToFile(String filename, String data) {
		FileHandle file = Gdx.files.local(filename);
		file.writeString(data, false);
	}

	public String loadFromFile(String filename) throws GdxRuntimeException {
		FileHandle file = Gdx.files.local(filename);
		return file.readString();
	}

	public TextureRegion getIcon(String key) {
		return icons.get(key);
	}

	public HashMap<String, Sound> getSounds() {
		return sounds;
	}

	public HashMap<String, Music> getMusic() {
		return music;
	}

	public TextureRegion getGui(String key) {
		return gui.get(key);
	}

	public Cursor getCursor(String name) {
		return cursors.get(name);
	}

	public int getItemsCount() {
		return items.length;
	}

	public TextureRegion getItemIcon(int index) {
		return items[index];
	}

	public TextureRegion[] getAnimation(String key) {
		return animations.get(key);
	}

	public HashMap<String, TextureRegion> getObject() {
		return objects;
	}

	public TextureRegion getObject(String key) {
		return objects.get(key);
	}

	private Music getMusic(String fileName) {
		return assetManager.get(DIRECTORY + Style.MUSIC_DIRECTORY + fileName);
	}

	private Sound getSound(String fileName) {
		return assetManager.get(DIRECTORY + Style.SOUNDS_DIRECTORY + fileName);
	}

	private Texture getTexture(String fileName) {
		return assetManager.get(DIRECTORY + Style.GRAPHICS_DIRECTORY + fileName);
	}

	public TextureRegion getParticle(String key) {
		return particles.get(key);
	}

	public Weapon getWeaponConfig(WeaponType type) {
		return weapons[type.ordinal()];
	}

	public Weapon[] createWeapons(boolean halfDamage) {
		Weapon[] clone = new Weapon[weapons.length];
		for (int i = 0; i < clone.length; i++) {
			Weapon w = new Weapon(weapons[i]);
			if (halfDamage)
				w.setHalfDamage();
			clone[i] = w;

		}
		return clone;
	}

	public TextureRegion getWeaponTile(int index) {
		return weaponsTiles[index];
	}

	public int getWeaponsCount() {
		return weaponsIcons.length;
	}

	public TextureRegion getWeaponIcon(int index) {
		return weaponsIcons[index];
	}

	public TextureRegion getHud(String key) {
		return hud.get(key);
	}

	public TextureRegion[] getMapTiles() {
		return mapTiles;
	}

	public TextureRegion getMapTile(int index) {
		return mapTiles[index];
	}

	public TextureRegion getBlack(){
		return black;
	}

	public TextureRegion[] getCharacter(String key) {
		return characters.get(key);
	}

	public BitmapFont getFont(Font fontType) {
		return fonts.get(fontType);
	}

	public TextureRegion getBackground(String key) {
		return backgrounds.get(key);
	}

	private FileHandle openFile(String path) {
		return Gdx.files.internal(DIRECTORY + path);
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
