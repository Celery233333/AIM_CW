package heuristic;

import frame.Instance;

/***
 * CrossoverHeuristic class is the parent class of all low level crossover heuristics
 * @author Cheng Qin
 */
public abstract class CrossoverHeuristic {

	/***
	 * Call this method to apply current heuristic to the input instance
	 * @param instance an Instance variable inherits from CWRunner class
	 * @param parentIndex1 an int indicates index of first parent
	 * @param parentIndex2 an int indicates index of second parent
	 * @param childIndex1 an int indicates index of first child
	 * @param childIndex2 an int indicates index of second child
	 */
	public abstract void applyHeuristic(Instance instance, int parentIndex1, 
			int parentIndex2, int childIndex1, int childIndex2);
}