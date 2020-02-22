package engine;

public class GameLogic {
	
	public static int needToNextLevel(int level){
		return (int) (Style.EXPERIENCE_COF * level * level);
	}
	
	public static int expToLevel(int exp){
		return ((int) Math.sqrt(exp / Style.EXPERIENCE_COF)) + 1;
	}
}