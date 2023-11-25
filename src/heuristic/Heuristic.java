package heuristic;

import frame.Instance;

/***
 * Heuristic class is the parent class of all low level heuristics except all crossover heuristics
 * @author Cheng Qin
 */
public abstract class Heuristic {
	
	/***
	 * Call this method to apply current heuristic to the input instance
	 * @param instance an Instance variable inherits from CWRunner class
	 * @param index an index indicates which solution will be used to apply current heuristic
	 */
	public abstract void applyHeuristic(Instance instance, int index);
}
