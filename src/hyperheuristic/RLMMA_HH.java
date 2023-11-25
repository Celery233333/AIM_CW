package hyperheuristic;

import java.util.Random;

import frame.DeltaEvaluation;
import frame.Instance;
import heuristic.DavissHillClimbing;
import heuristic.Heuristic;
import heuristic.CrossoverHeuristic;
import heuristic.OnePointXO;
import heuristic.RandomMutation;
import heuristic.RuinRecreate;
import heuristic.BestHillClimbing;
import heuristic.UniformXO;
import memetic.TournamentSelection;
import memetic.TransGenerationalReplacementWithElitistReplacement;

/***
 * RLMMA_HH class creates a population based hyper-heuristic using 
 * RouletteWheelSelection and mematic algorithm in each generation
 * @author Cheng Qin
 */
public class RLMMA_HH {
	
	/***
	 * Call this hyper heuristic to solve the input instance
	 * @param instance an Instance variable inherits from CWRunner class
	 */
	public void solve(Instance instance) {
		Random rng = instance.getRandom();
		int defaultScore = instance.getConfig().getDefaultScore();
		int lowerBound = instance.getConfig().getLowerBound();
		int upperBound = instance.getConfig().getUpperBound();
		int iterations = instance.getConfig().getTotalRuns();
		int population = instance.getConfig().getPopSize();
		int maximum = instance.getConfig().getNumberOf();
		
		// initialize all low level heuristics
		DeltaEvaluation delta = new DeltaEvaluation(instance.getItemPair(),instance.getCapacity());
		RandomMutation mutation = new RandomMutation(delta);
		RuinRecreate rr = new RuinRecreate();
		DavissHillClimbing davis = new DavissHillClimbing(delta);
		OnePointXO oxo = new OnePointXO(delta);
		UniformXO uxo = new UniformXO(delta);
		BestHillClimbing best = new BestHillClimbing(delta);
		
		CrossoverHeuristic[] cos = {oxo,uxo};
		Heuristic[] mtns = {rr,mutation};
		Heuristic[] lss = {best,davis};
		
		// combination of all heuristics
		HeuristicSet[] hs = new HeuristicSet[cos.length * mtns.length * lss.length];
		for (int i = 0; i < cos.length; i++) {
			for (int j = 0; j < mtns.length; j++) {
				for (int z = 0; z < lss.length; z++) {
					hs[i*mtns.length*lss.length + j*lss.length + z] = new HeuristicSet(cos[i],mtns[j],lss[z]); 
				}
			}
		}
		
		TournamentSelection selection = new TournamentSelection();
		TransGenerationalReplacementWithElitistReplacement replacement = new TransGenerationalReplacementWithElitistReplacement();
		RouletteWheelSelection rws = new RouletteWheelSelection(hs, defaultScore, 
				lowerBound, upperBound, rng);
		
		for (int i = 0; i < iterations; i++) {
			double currentValue = instance.getBest().getObjectiveValue();
			HeuristicSet h = rws.performRouletteWheelSelection();
			
			for (int j = 0; j < population / 2; j++) {
				// select parents using tournament selection
				int parentIndex1 = selection.tournamentSelect(instance, rng);
				int parentIndex2 = selection.tournamentSelect(instance, rng);
				while (parentIndex1 == parentIndex2) {
					parentIndex2 = selection.tournamentSelect(instance, rng);
				}
				
				int childIndex1 = population + j*2;
				int childIndex2 = population + j*2 + 1;
				
				h.geth1().applyHeuristic(instance, parentIndex1, parentIndex2,
						childIndex1, childIndex2);
				h.geth2().applyHeuristic(instance, childIndex1);
				h.geth2().applyHeuristic(instance, childIndex2);
				h.geth3().applyHeuristic(instance, childIndex1);
				h.geth3().applyHeuristic(instance, childIndex2);
			}
			
			// replace old population
			replacement.doReplacement(instance);
			
			if (i < maximum) {
				// store best-worst pair
				instance.setBestWorstPair(i+1, instance.getBest().getObjectiveValue(), 
						instance.getWorst().getObjectiveValue());
			}
			
			// check to update socre for current heuristic pair
			double candidateValue = instance.getBest().getObjectiveValue();
			if (candidateValue > currentValue) {
				rws.incrementScore(h);
			} else {
				rws.decrementScore(h);
			}
		}
	}
}
