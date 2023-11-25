package memetic;

import frame.Instance;

/***
 * TransGenerationalReplacement class provides a way to replace parents with children solutions
 * @author Cheng Qin
 */
public class TransGenerationalReplacementWithElitistReplacement {
	
	/***
	 * Copy children solutions(next generation) to cover the original parents
	 * @param instance an Instance variable inherits from CWRunner class
	 */
	public void doReplacement(Instance instance) {
		int[] newIndex = getNextGeneration(instance);

		// if the best objective was found from parents, then record the representation first
		int index = -1;
		int[] previousRep = null;
		for (int i = 0; i < newIndex.length; i++) {
			if (newIndex[i] < newIndex.length) {
				index = newIndex[i];
				previousRep = instance.getPopulation(newIndex[i]).getRepresentation();
			}
		}
		
		for (int i = 0; i < newIndex.length; i++) {
			// if the solution now is from parents, then update it using above data
			if (newIndex[i] == index) {
				double[] array = instance.getPopulation(i).calculateObjectiveValue(previousRep);
				instance.getPopulation(i).updateRepresentation(previousRep, array);
			} else {
				// else directly update parents using children
				int[] representation = instance.getPopulation(newIndex[i]).getRepresentation();
				double[] array = {instance.getPopulation(newIndex[i]).getObjectiveValue(),
						instance.getPopulation(newIndex[i]).getProfit(), 
						instance.getPopulation(newIndex[i]).getWeight()};
				
				instance.getPopulation(i).updateRepresentation(representation, array);
			}
		}
	}
	
	/***
	 * Check if the parents' solutions are better than the children's, if not directly return
	 * all children and if so replace the worst child with the best parent
	 * @param instance an Instance variable inherits from CWRunner class
	 * @return an int array indicates which solutions will be used as next generation
	 */
	private int[] getNextGeneration(Instance instance) {
		// get the best parent
		int previousBestIndex = 0;
		double previousMax = instance.getPopulation(0).getObjectiveValue();
		for (int i = 1; i < instance.getConfig().getPopSize(); i++) {
			if (previousMax < instance.getPopulation(i).getObjectiveValue()) {
				previousMax = instance.getPopulation(i).getObjectiveValue();
				previousBestIndex = i;
			}
		}
		
		// get the best child
		double currentMax = instance.getPopulation(instance.getConfig().getPopSize()).getObjectiveValue();
		for (int i = 1 + instance.getConfig().getPopSize(); i < instance.getConfig().getPopSize()*2; i++) {
			if (currentMax < instance.getPopulation(i).getObjectiveValue()) {
				currentMax = instance.getPopulation(i).getObjectiveValue();
			}
		}
		
		// get all children's index
		int[] newIndex = new int[instance.getConfig().getPopSize()];
		for (int i = 0; i < instance.getConfig().getPopSize(); i++) {
			newIndex[i] = i + instance.getConfig().getPopSize();
		}
		
		// replace the worst child with the best parent if that parent solution has the best value overall
		if (previousMax > currentMax) {
			int currentWorstIndex = instance.getConfig().getPopSize();
			double min = instance.getPopulation(currentWorstIndex).getObjectiveValue();
			// get the worst child
			for (int i = 1 + instance.getConfig().getPopSize(); i < instance.getConfig().getPopSize()*2; i++) {
				if (min > instance.getPopulation(i).getObjectiveValue()) {
					min = instance.getPopulation(i).getObjectiveValue();
					currentWorstIndex = i;
				}
			}
			
			for (int i = 0; i < newIndex.length; i++) {
				if (newIndex[i] == currentWorstIndex) {
					newIndex[i] = previousBestIndex;
				}
			}
		}
		return newIndex;
	}
}
