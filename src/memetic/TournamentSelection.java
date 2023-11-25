package memetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import frame.Instance;

/***
 * TournamentSelection class provides a way to choose parent from instance to do crossover
 * @author Cheng Qin
 */
public class TournamentSelection {
	
	/***
	 * Shuffle the solutions first, then choose the best one from first tournaments size's solutions
	 * @param instance an Instance variable inherits from CWRunner class
	 * @param rng a random variable inherits from TestFrameConfig class
	 * @return an int indicates which solution to be chosen
	 */
	public int tournamentSelect(Instance instance, Random rng) {
		int populationSize = instance.getConfig().getPopSize();
		int tournamentSize = instance.getConfig().getTournamentSize();
		
		// shuffle the index
		List<Integer> perm = new ArrayList<Integer>();
		for (int i = 0; i < populationSize ; i++) {
			perm.add(i);
		}
        Collections.shuffle(perm,rng);
        
        int bestIndex = perm.get(0);
		double bestValue = instance.getPopulation(bestIndex).getObjectiveValue();
		
        // choose bestindex using tournament
        for(int i = 1; i < tournamentSize; i++) {
			int sol = perm.get(i);
			double fitness = instance.getPopulation(sol).getObjectiveValue();

			if(fitness > bestValue) {
				bestValue = fitness;
				bestIndex = sol;
			}
		}
        
		return bestIndex;
	}
}
