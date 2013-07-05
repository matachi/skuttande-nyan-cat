package se.danielj.skuttandenyancat.misc;

/**
 * There are two scores. The total score and the score that you can build up
 * when you run on a pole. The "temp" score is added to the total score when you
 * jump from the pole.
 * 
 * @author Daniel Jonsson
 * @license GNU GPLv3
 * 
 */
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
		if (score > 0) {
			totalScore += score;
			score = 0;
		}
	}
}
