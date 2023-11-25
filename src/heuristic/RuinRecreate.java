package heuristic;

import frame.Instance;
import frame.Solution;

/***
 * RuinRecreate class implements ruin and recreate heuristic
 * @author Cheng Qin
 */
public class RuinRecreate extends Heuristic {
	
	/***
	 * Select a set of bits randomly and set all of them to 0, then randomly choose some bits from it to 1
	 */
	@Override
	public void applyHeuristic(Instance instance, int index) {
		Solution solution = instance.getPopulation(index);
		int[] representation = solution.clone(solution.getRepresentation());
		int length = instance.getNumberOfItems();
		
		for (int j = 0; j < instance.getConfig().getIOM(); j++) {
			int startPoint = instance.getRandom().nextInt(length-1);
			int endPoint = instance.getRandom().nextInt(length-startPoint) + startPoint;
			
			// flip all 1s to 0 from startPoint to endPoint
			for (int i = startPoint; i <= endPoint; i++) {
				if (representation[i] == 1) {
					representation[i] = 0;
				}
			}
			
			// randomly generate representation in this area
			for (int i = startPoint; i <= endPoint; i++) {
				if (instance.getRandom().nextDouble() > 0.5) {
					representation[i] = 1;
				}
			}
		}
		
		// accept all results
		double[] array = solution.calculateObjectiveValue(representation);
		solution.updateRepresentation(representation, array);
	}
}
