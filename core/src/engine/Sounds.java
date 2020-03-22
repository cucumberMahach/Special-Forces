package engine;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import world.gameplay.Weapon;
import world.gameplay.WeaponType;

public class Sounds {
	private HashMap<String, Sound> sounds;
	private HashMap<String, Music> music;
	private boolean soundsEnabled = true;
	private boolean musicEnabled = true;
	
	private HashMap<Integer, String> tileSounds;
	private HashMap<Music, Boolean> isPlayedMusic = new HashMap<>();
	
	public Sounds(Loader loader){
		sounds = loader.getSounds();
		music = loader.getMusic();
		tileSounds = new HashMap<Integer, String>();
		tileSounds.put(0, "dirt");
		tileSounds.put(1, "step");
		tileSounds.put(2, "grass");
		tileSounds.put(3, "step");
		tileSounds.put(5, "wood");
		tileSounds.put(6, "dirt");
		tileSounds.put(7, "step");
		tileSounds.put(9, "step");
		tileSounds.put(11, "step");
		tileSounds.put(12, "step");
		tileSounds.put(13, "tile");
		tileSounds.put(14, "grass");
		tileSounds.put(18, "step");
	}

	public void loadSettings(){
		if (!SpecialForces.getInstance().playerData().getSoundsEnableStatus()){
			toggleSounds();
		}
		if (!SpecialForces.getInstance().playerData().getMusicEnableStatus()){
			toggleMusic();
		}
	}
	
	public void playStep(int tile){
		String name = tileSounds.get(tile);
		if (name != null)
			playRandom(name, 4);
		else
			playRandom("step", 4);
	}
	
	public void playMusic(String name, boolean looping){
		final Music m = music.get(name);
		if (!m.isPlaying())
			m.play();
		m.setLooping(looping);
	}
	
	public void stopAllMusic(){
		isPlayedMusic.clear();
		for (Entry<String, Music> entry: music.entrySet())
			entry.getValue().stop();
	}
	
	public void setVolumeAllMusic(float value){
		for (Entry<String, Music> entry: music.entrySet())
			entry.getValue().setVolume(value);
	}

	public void pauseAllPlayedMusic(){
		isPlayedMusic.clear();
		for (Entry<String, Music> musicEntry: music.entrySet()){
			isPlayedMusic.put(musicEntry.getValue(), musicEntry.getValue().isPlaying());
			musicEntry.getValue().pause();
		}
	}

	public void restoreAllPlayedMusic(){
		for (Entry<Music, Boolean> entry: isPlayedMusic.entrySet()){
			if (entry.getValue())
				entry.getKey().play();
		}
	}
	
	public void complete(){
		play("complete");
	}
	
	public void end(){
		play("end");
	}
	
	public void buy(){
		play("cash");
	}
	
	public void experience(){
		play("exp");
	}
	
	public void click(){
		play("click");
	}
	
	public void flip(){
		play("flip");
	}
	
	public void damage(){
		playRandom("damage", 3);
	}
	
	public void death(){
		playRandom("death", 6);
	}
	
	public void exploded(){
		playRandom("exploded", 2);
	}
	
	public void ammo(){
		play("ammo");
	}
	
	public void medkit(){
		play("medkit");
	}
	
	public void energy(){
		play("energy");
	}
	
	public void impact(){
		playRandom("impact", 4);
	}
	
	public void dirt(){
		playRandom("dirt", 4);
	}
	
	public void wood(int index){
		play("m_wood".concat(String.valueOf(index)));
	}
	
	public void play(String soundName){
		if (soundsEnabled) {
			Sound s = sounds.get(soundName);
			if (s != null)
				s.play();
		}
	}
	
	public void playShoot(Weapon weapon){
		play("shoot".concat(String.valueOf(weapon.getType().ordinal())));
		if (weapon.isEmpty())
			play("empty");
	}

	public void playChange(Weapon weapon){
		play("change" + weapon.getType().ordinal());
	}
	
	public void playReload(WeaponType type){
		play("reload".concat(String.valueOf(type.ordinal())));
	}
	
	public void explode(){
		play("explode");
	}
	
	public void bounce(){
		play("bounce");
	}
	
	private void playRandom(String name, int max){
		play(name.concat(String.valueOf(MathUtils.random(1, max))));
	}
	
	public void toggleSounds(){
		soundsEnabled = !soundsEnabled;
		SpecialForces.getInstance().playerData().saveSoundsEnableStatus(soundsEnabled);
	}
	
	public void toggleMusic(){
		musicEnabled = !musicEnabled;
		if (!musicEnabled)
			setVolumeAllMusic(0);
		else
			setVolumeAllMusic(1);
		SpecialForces.getInstance().playerData().saveMusicEnableStatus(musicEnabled);
	}
	
	public boolean isSoundsEnabled(){
		return soundsEnabled;
	}
	
	public boolean isMusicEnabled(){
		return musicEnabled;
	}
	
}
