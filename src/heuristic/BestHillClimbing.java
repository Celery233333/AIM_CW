package heuristic;

import frame.DeltaEvaluation;
import frame.Instance;
import frame.Solution;

/***
 * BestHillClimbing class implements best hill climbing heuristic
 * @author Cheng Qin
 */
public class BestHillClimbing extends Heuristic {
	
	DeltaEvaluation delta;
	
	public BestHillClimbing(DeltaEvaluation delta) {
		this.delta = delta;
	}
	
	/***
	 * Check the solution to flip the bit which can give the most improvement
	 */
	@Override
	public void applyHeuristic(Instance instance, int index) {
		Solution solution = instance.getPopulation(index);
		int length = instance.getNumberOfItems();
			
		for (int j = 0; j < instance.getConfig().getDOS(); j++) {
			double currentObjectiveValue = solution.getObjectiveValue();
			int improveBit = 0;
			double bestEval = 0;
			boolean improved = false;
			
	        int[] representation = solution.clone(solution.getRepresentation());
	        for (int i = 0; i < length; i++) {
	        	solution.bitFilp(representation, i);
	   
	        	// if the objective value of the new solution is better or equal, then record it
	        	double[] array = delta.deltaEvaluate1(solution.getRepresentation(), solution.getArray(), i);
	        	//double[] array = solution.calculateObjectiveValue(representation);      
	        	
	        	// accept non-worse and record the best bit
	        	if (array[0] >= currentObjectiveValue) {
	        		if (bestEval < array[0]) {
	        			bestEval = array[0];
	        			improveBit = i;
	        		}
	        		improved = true;
	        	}
	        	
	        	// flip back to original solution
	        	solution.bitFilp(representation, i);
	        }
	        
	        // update solution if improved, else return
	        if (improved) {
		        solution.bitFilp(representation, improveBit);
		        //double[] update = solution.calculateObjectiveValue(representation);
		        double[] update = delta.deltaEvaluate1(solution.getRepresentation(), solution.getArray(), improveBit);
		        solution.updateRepresentation(representation, update);
	        } else {
	        	return;
	        }
		}
	}
}
