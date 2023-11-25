package heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frame.DeltaEvaluation;
import frame.Instance;
import frame.Solution;

/***
 * DavissHillClimbing class implements a davis hill climbing heuristic
 * @author Cheng Qin
 */
public class DavissHillClimbing extends Heuristic {
	
	DeltaEvaluation delta;
	
	public DavissHillClimbing(DeltaEvaluation delta) {
		this.delta = delta;
	}
	
	/***
	 * Flip the bits that can improve it according to the shuffled index arrays
	 */
	@Override
	public void applyHeuristic(Instance instance, int index) {
		Solution solution = instance.getPopulation(index);
		int length = instance.getNumberOfItems();
			
		for (int j = 0; j < instance.getConfig().getDOS(); j++) {
			double currentObjectiveValue = solution.getObjectiveValue();
			boolean improved = false;
			
			// create permutation for current instance randomly
			List<Integer> perm = new ArrayList<Integer>();
			for (int i = 0; i< length; i++) {
				perm.add(i);
			}
	        Collections.shuffle(perm,instance.getRandom());
	        
	        int[] representation = solution.clone(solution.getRepresentation());
	        for (int i = 0; i < length; i++) {
	        	// flip the bits in "permutation order" 
	        	solution.bitFilp(representation, perm.get(i));
	   
	        	// if the objective value of the new solution is better or equal, then keep the bit flipped
	        	double[] array = delta.deltaEvaluate1(solution.getRepresentation(), solution.getArray(), perm.get(i));
	        	//double[] array = solution.calculateObjectiveValue(representation);  
	        	if (array[0] >= currentObjectiveValue) {
	        		improved = true;
	        		solution.updateRepresentation(representation, array);
	        	} else {
	        		solution.bitFilp(representation, perm.get(i));
	        	}
	        }
	        
	        // return if no improvement
	        if (!improved) {
	        	return;
	        }
		}
	}
}
