package heuristic;

import java.util.ArrayList;

import frame.DeltaEvaluation;
import frame.Instance;
import frame.Solution;

/***
 * RandomMutation class implements random bit mutation heuristic
 * @author Cheng Qin
 */
public class RandomMutation extends Heuristic {
	
	DeltaEvaluation delta;
	
	public RandomMutation(DeltaEvaluation delta) {
		this.delta = delta;
	}

	/***
	 * Select a bit randomly, and then flip it
	 */
	@Override
	public void applyHeuristic(Instance instance, int index) {
		Solution solution = instance.getPopulation(index);
		int length = instance.getNumberOfItems();
		int[] representation = solution.clone(solution.getRepresentation());
			
		ArrayList<Integer> changeBits = new ArrayList<Integer>();
		for (int j = 0; j < instance.getConfig().getIOM(); j++) {
			// flip IOM's bits randomly
			int i = instance.getRandom().nextInt(length);
			solution.bitFilp(representation, i);
			changeBits.add(i);
		}
		
		// accept all results
		double[] array = delta.deltaEvaluate2(solution.getRepresentation(),solution.getArray(),changeBits);
		//double[] array = solution.calculateObjectiveValue(representation);
		solution.updateRepresentation(representation, array);
	}
}
