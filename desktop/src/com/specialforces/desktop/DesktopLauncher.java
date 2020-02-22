package com.specialforces.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import engine.SpecialForces;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = SpecialForces.TITLE;
		
		config.width = SpecialForces.WIDTH;
		config.height = SpecialForces.HEIGHT;
		config.resizable = SpecialForces.RESIZABLE;
		
		config.fullscreen = SpecialForces.FULLSCREEN;
		config.foregroundFPS = SpecialForces.FPS;
		config.vSyncEnabled = SpecialForces.VSYNC;
		
		config.samples = SpecialForces.SAMPLES;
		
		new LwjglApplication(SpecialForces.getInstance(), config);
	}
}
