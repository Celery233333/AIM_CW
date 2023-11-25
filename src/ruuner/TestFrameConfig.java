package ruuner;

import java.util.Random;

/***
 * TestFrameConfig class uses for parameter turning, users can change parameters 
 * for reader, printer, heuristic, hyper-heuristic and mematic algorithm
 * @author Cheng Qin
 */
public class TestFrameConfig {
	
	// INSTANCE_ID = 1,2,3,4,5,6,7,8
	private final int INSTANCE_ID = 7;
	
	// 0.0 <= IOM <= 1.0
	private final double IOM = 0;
	
	// 0.0 <= DOS <= 1.0
	private final double DOS = 1;
	
	// NUMBER_OF indicates numbers of best-worst solution pairs to store
	private final int NUMBER_OF = 100;
	
	// TOTAL_TRIALS indicates trials
	private final int TOTAL_TRIALS = 6;
	
	// TOTAL_RUNS indicates iterations
	private final int TOTAL_RUNS = 150;
	
	// POP_SIZE = 2*n (e.g. 2,4,6,8,10...)
	private final int POP_SIZE = 32;
	
	// TOURNAMENT_SIZE << POP_SIZE
	private final int TOURNAMENT_SIZE = 8;
	
	// following three parameters are for hyper-heuristic (RouletteWheelSelection)
	private final int DEFAULT_SCORE = 8;
	
	// 1 <= LOWER_BOUND < DEFAULT_SCORE
	private final int LOWER_BOUND = 1;
	
	// DEFAULT_SCORE < UPPER_BOUND
	private final int UPPER_BOUND = 15;
	
	private static final long Seed = 10062001l;
	
	private final Random random;
	
	public TestFrameConfig() {
		this.random = new Random(Seed);
	}
	
	public int getInstance() {
		return INSTANCE_ID;
	}
	
	/***
	 * Get the number of times to run mutation based on map below:
	 *	1. 0.0 ¡Ü IOM < 0.2 -> times = 1
		2. 0.2 ¡Ü IOM < 0.4 -> times = 2
		3. 0.4 ¡Ü IOM < 0.6 -> times = 3
		4. 0.6 ¡Ü IOM < 0.8 -> times = 4
		5. 0.8 ¡Ü IOM < 1.0 -> times = 5
		6. IOM > 1.0 -> times = 6
	 * @return an int indicates number of times to run mutation
	 */
	public int getIOM() {
		int times = (int) ((IOM * 10)/2) + 1;
		if (times > 6) {
			times = 6;
		}
		
		return times;
	}
	
	/***
	 * Get the number of times to run hill climbing based on map below:
	 *	1. 0.0 ¡Ü DOS < 0.2 -> times = 1 * 2
		2. 0.2 ¡Ü DOS < 0.4 -> times = 2 * 2
		3. 0.4 ¡Ü DOS < 0.6 -> times = 3 * 2
		4. 0.6 ¡Ü DOS < 0.8 -> times = 4 * 2
		5. 0.8 ¡Ü DOS < 1.0 -> times = 5 * 2
		6. IOM > 1.0 -> times = 6 * 2
	 * @return an int indicates number of times to run hill climbing
	 */
	public int getDOS() {
		int times = (int) (((DOS * 10)/2) + 1) * 2;
		if (times > 12) {
			times = 12;
		}
		
		return times;
	}
	
	/***
	 * Get the number of best-worst pair to store, subtract 1 since first pair 
	 * is used to sotore initial generation
	 * @return an int that represents how many pairs to store
	 */
	public int getNumberOf() {
		return NUMBER_OF;
	}
	
	public int getTotalTrials() {
		return TOTAL_TRIALS;
	}
	
	public int getTotalRuns() {
		return TOTAL_RUNS;
	}
	
	public int getPopSize() {
		return POP_SIZE;
	}
	
	public int getTournamentSize() {
		return TOURNAMENT_SIZE;
	}
	
	public int getDefaultScore() {
		return DEFAULT_SCORE;
	}
	
	public int getLowerBound() {
		return LOWER_BOUND;
	}
	
	public int getUpperBound() {
		return UPPER_BOUND;
	}
	
	public Random getRandom() {
		return random;
	}
}
