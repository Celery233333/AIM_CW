package heuristic;

import java.util.ArrayList;

import frame.DeltaEvaluation;
import frame.Instance;
import frame.Solution;

/***
 * UniformXO class implements uniform crossover heuristic
 * @author Cheng Qin
 */
public class UniformXO extends CrossoverHeuristic{
	
	DeltaEvaluation delta;
	
	public UniformXO(DeltaEvaluation delta) {
		this.delta = delta;
	}

	/***
	 * Exchange bits from two parents randomly to generate children solutions
	 */
	@Override
	public void applyHeuristic(Instance instance, int parentIndex1, 
			int parentIndex2, int childIndex1, int childIndex2) {
		Solution solution1 = instance.getPopulation(parentIndex1);
		Solution solution2 = instance.getPopulation(parentIndex2);
		Solution solution3 = instance.getPopulation(childIndex1);
		Solution solution4 = instance.getPopulation(childIndex2);
		int length = instance.getNumberOfItems();
		
		int[] newRep1 = solution1.clone(solution1.getRepresentation());
		int[] newRep2 = solution2.clone(solution2.getRepresentation());
		
		// randomly change some bits between two parents
		ArrayList<Integer> changeBits = new ArrayList<Integer>();
		for (int i = 0; i < length; i++) {
			if (instance.getRandom().nextDouble() > 0.5 & newRep1[i] != newRep2[i]) {
				int temp = newRep1[i];
				newRep1[i] = newRep2[i];
				newRep2[i] = temp;
				
				// record the change bit for delta evaluation
				changeBits.add(i);
			}
		}
		
		double[] array1 = delta.deltaEvaluate2(solution1.getRepresentation(), solution1.getArray(), changeBits);
		double[] array2 = delta.deltaEvaluate2(solution2.getRepresentation(), solution2.getArray(), changeBits);
		//double[] array1 = solution1.calculateObjectiveValue(newRep1);
		//double[] array2 = solution2.calculateObjectiveValue(newRep2);
		
		solution3.updateRepresentation(newRep1, array1);
		solution4.updateRepresentation(newRep2, array2);
	}
}
