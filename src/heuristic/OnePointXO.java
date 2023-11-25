package heuristic;

import java.util.ArrayList;

import frame.DeltaEvaluation;
import frame.Instance;
import frame.Solution;

/***
 * OnePointXO class implements one point crossover heuristic
 * @author Cheng Qin
 */
public class OnePointXO extends CrossoverHeuristic{
	
	DeltaEvaluation delta;
	
	public OnePointXO(DeltaEvaluation delta) {
		this.delta = delta;
	}
	
	/***
	 * Select a same point in two parents, then exchange all bits before selected bit
	 */
	@Override
	public void applyHeuristic(Instance instance, int parentIndex1, 
			int parentIndex2, int childIndex1, int childIndex2) {
		Solution solution1 = instance.getPopulation(parentIndex1);
		Solution solution2 = instance.getPopulation(parentIndex2);
		Solution solution3 = instance.getPopulation(childIndex1);
		Solution solution4 = instance.getPopulation(childIndex2);
		int length = instance.getNumberOfItems();
		
		// cross over does not occur if the first bit is selected
		int point = instance.getRandom().nextInt(length-1) + 1;
		int[] newRep1 = solution1.clone(solution1.getRepresentation());
		int[] newRep2 = solution2.clone(solution2.getRepresentation());
		
		ArrayList<Integer> changeBits = new ArrayList<Integer>();
		for (int i = 0; i < point; i++) {
			if (newRep1[i] != newRep2[i]) {
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
