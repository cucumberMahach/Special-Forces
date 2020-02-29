package engine;

import com.badlogic.gdx.graphics.Color;

public class Style {
	public static final String			MAPS_DIRECTORY				= "maps/";
	public static final String			GRAPHICS_DIRECTORY			= "gfx/";
	public static final String			SOUNDS_DIRECTORY			= "sounds/";
	public static final String			MUSIC_DIRECTORY				= "music/";

	public static final int				MAPTILES_COUNT				= 21;
	
	public static final float			CAMERA_SPEED_MOVE			= 0.05f;
	public static final float			CAMERA_SPEED_ZOOM			= 0.05f;
	public static final float			CAMERA_ROOM_ZOOM			= 400 * (SpecialForces.getInstance().isAndroid() ? 2 : 1);
	public static final float			CAMERA_PLAYER_OFFSET        = 50*2.5f*2;
	
	public static final int				DYNAMIC_PARTICLES			= 10;
	public static final int				DYN_PARTICLE_MAX_WIDTH		= 32;
	public static final int				DYN_PARTICLE_MAX_HEIGHT		= 32;
	
	public static final float			ROOM_RAYCASTING_FREQUENCY	= 0.1f;
	public static final float			MAP_REFRESH_FREQUENCY		= 0.3f;
	public static final float			BOT_RAYCAST_FREQUENCY		= 0.5f;
	public static final int				PHYSICS_RAY_ACCURACY		= 1000;
	
	public static final float			EDITOR_ICON_WIDTH			= 48;
	public static final float			EDITOR_ICON_HEIGHT			= 48;
	public static final float			EDITOR_OBJECT_ICONS_LINE	= 10;
	public static final float			EDITOR_TILES_ICONS_LINE		= 9;
																		//teammate
	public static final float			BOT_MAX_REACTION_DELAY		= 1; //after visible and before shoot
	public static final float			BOT_MAX_STUCK_DELAY			= 3; //if position isn't changing (teleport to player)
	public static final float			BOT_MAX_SHOOTING_DELAY		= 1; //if target isn't died (navigate to target)
	public static final float			BOT_MAX_NAVIGATE_DELAY		= 1; //path finder delay
	public static final float			BOT_MAX_WAIT_DELAY			= 1; //after kill and before navigate to player
	
	public static final int				EXPERIENCE_COF				= 25;
	public static final int				MAX_WEAPON_LEVEL			= 10;
	public static final float			SORT_DELAY					= 5;
	
	public static final int				TILE_SIZE					= 32;
	public static final int				LOAD_TILE_SIZE				= 64;
	public static final float			CHARACTER_SIZECOF			= 0.2f;
	
	public static final int				BULLET_PATH_COUNT			= 10;
	public static final int				BULLET_PATH_STEP			= 100;
	public static final int				BULLET_PATH_LEN				= 150;
	public static final Color			BULLET_PATH_COLOR1			= Color.ORANGE;
	public static final Color			BULLET_PATH_COLOR2			= Color.YELLOW;
	
	public static final float			CHARACTER_HAND_X			= 125;
	public static final float			CHARACTER_HAND_Y			= 150;
	public static final float			CHARACTER_ARM_X				= 105;
	public static final float			CHARACTER_ARM_Y				= 125;
	
	public static final int				PARTICLES_DROP_COUNT		= 300;
	public static final int				PARTICLES_DEFAULT_COUNT		= 100;
	public static final float			PARTICLES_GRAVITY			= 0.9f;
	public static final float			PARTICLE_LIFETIME			= 5f;
	public static final float			PARTICLE_MIN_VELOCITY		= 0.01f;
	public static final float			PARTICLE_FADE_COF			= 0.01f;
	public static final float			EXPLODE_DROP_SCL			= 3;
	
	public static final float			SMOKE_LIFETIME				= 3;
	public static final float			SMOKE_FADE_COF				= 0.01f;
	
	public static final float			ROCKET_SPEED				= 7f;
	public static final float			ROCKET_SMOKE_FREQ			= 0.03f;
	public static final float			GRENADE_SPEED				= 3f;
	public static final float			GRENADE_DELAY				= 2f;
	public static final float			EXPLOSION_RADIUS			= 100;
	
	public static final int				SHOTGUN_BULLETS				= 4;
	public static final float			SHOTGUN_RANGE				= 5;
	public static final int				MEDKIT_INCREASE				= 50;
	public static final int				ENERGY_INCREASE				= 40;
																			//enemy
	public static final int				ARRIVE_DISTANCE				= 2;
	public static final float			VISIBLE_DISTANCE			= 400;
	public static final float			MAX_VISIBLE_DISTANCE		= 1500;
	public static final int				MAX_VISIBLE_DISTANCE_INT	= (int) (MAX_VISIBLE_DISTANCE / TILE_SIZE);
	public static final float			REACTION_DELAY				= 0.6f;
	public static final float			SEARCH_TIME					= 15;
	public static final float			NEAREST_DISTANCE			= 100;
	public static final float			FARTHEST_DISTANCE			= 150;
	public static final float			STEP_FREQ					= 0.3f;

	public static final String			MAPSENDER_IP				= "188.134.94.112";
	public static final int				MAPSENDER_PORT				= 2040;
	public static final int				MAPSENDER_TIMEOUT			= 3000;
	public static final long			MAPSENDER_RESEND_TIME		= 5 * 60 * 1000;
}
