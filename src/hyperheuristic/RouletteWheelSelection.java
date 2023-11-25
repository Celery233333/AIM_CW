package hyperheuristic;

import java.util.LinkedHashMap;
import java.util.Random;

/***
 * RouletteWheelSelection class provides a way to select which HeuristicSet to use in hyper-heuristic
 * @author Cheng Qin
 */
public class RouletteWheelSelection {
	public HeuristicSet[] heuristicSet;

	public LinkedHashMap<HeuristicSet, Integer> heuristicScores;

	public final int upperBound;

	public final int lowerBound;

	public final int defaultScore;

	public final Random rng;
	
	public RouletteWheelSelection(HeuristicSet[] hs, int default_score, int lower_bound, int upper_bound, Random rng) {
		this.heuristicSet = hs;
		this.heuristicScores = new LinkedHashMap<HeuristicSet, Integer>();
		this.defaultScore = default_score;
		this.lowerBound = lower_bound;
		this.upperBound = upper_bound;
		this.rng = rng;
		
		for (HeuristicSet h : hs) {
			this.heuristicScores.put(h, defaultScore);
		}
	}
	
	public int getScore(HeuristicSet h) {
		if (this.heuristicScores.get(h) == null) {
			return 0;
		}
		else {
			return this.heuristicScores.get(h);
		}
	}
	
	/***
	 * Increase score for current HeuristicSet h
	 * @param h a HeuristicSet indicates three low level heuristics
	 */
	public void incrementScore(HeuristicSet h) {
		if (getScore(h) < this.upperBound) {
			this.heuristicScores.put(h, getScore(h) + 1);
		}
	}
	
	/***
	 * Decrease score for current HeuristicSet h
	 * @param h a HeuristicSet indicates three low level heuristics
	 */
	public void decrementScore(HeuristicSet h) {
		if (getScore(h) > this.lowerBound) {
			this.heuristicScores.put(h, getScore(h) - 1);
		}
	}
	
	/***
	 * Calculate the total scores of all HeuristicSet
	 * @return an int indicates the total scores
	 */
	public int getTotalScore() {
		int sum = 0;
		for (HeuristicSet e : this.heuristicSet) {
			sum += getScore(e);
		}
		return sum;
	}
	
	/***
	 * Performs the RouletteWheelSelection to choose a HeuristicSet to use in next generation based on socres
	 * @return a HeuristicSet indicates three low level heuristics
	 */
	public HeuristicSet performRouletteWheelSelection() {
		int value = rng.nextInt(getTotalScore());
		int cumulative_score = 0;
		int count = 0;
		HeuristicSet h = heuristicSet[0];
		
		// probability based on the score
		while (cumulative_score < value) {
			h = this.heuristicSet[count++];
			cumulative_score += getScore(h);
		}
		return h;
	}
}
