package com.specialforces.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import engine.Loader;
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

		config.addIcon(Loader.DESKTOP_DIRECTORY + "gfx/desktop_icons/128x128.png", Files.FileType.Internal);
		config.addIcon(Loader.DESKTOP_DIRECTORY + "gfx/desktop_icons/64x64.png", Files.FileType.Internal);
		config.addIcon(Loader.DESKTOP_DIRECTORY + "gfx/desktop_icons/32x32.png", Files.FileType.Internal);
		config.addIcon(Loader.DESKTOP_DIRECTORY + "gfx/desktop_icons/16x16.png", Files.FileType.Internal);

		new LwjglApplication(SpecialForces.getInstance(), config);
	}
}
