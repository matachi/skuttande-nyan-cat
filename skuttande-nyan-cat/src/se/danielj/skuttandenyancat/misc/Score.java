package se.danielj.skuttandenyancat.misc;

public class Score {

	private static double totalScore;
	
	private static double score;
	
	public static void init() {
		totalScore = 0;
		score = 0;
	}
	
	public static int getTotalScore() {
		return (int) totalScore;
	}
	
	public static int getScore() {
		return (int) score;
	}
	
	public static void addScore(double score) {
		Score.score += score;
	}
	
	public static void addScoreToTotal() {
		totalScore += score;
		score = 0;
	}
}
