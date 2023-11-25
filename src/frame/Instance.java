package frame;

import java.util.Arrays;
import java.util.Random;

import heuristic.GreedyHeuristic;
import ruuner.TestFrameConfig;

/***
 * Instance class represents a instance of the knapsack problem
 * @author Cheng Qin
 */
public class Instance {
	
	private final String name;
	
	private final int numberOfItems;
	
	private final double capacity;
	
	private final ItemPair[] itemPair;
	
	private final TestFrameConfig config;
	
	private Solution[] population;
	
	// uses to store best-worst pair for printing and writing
	private double[][] bestWorstPair;
	
	public Instance(String name, int numberOfItems, double capacity, ItemPair[] pair, TestFrameConfig config) {
		this.name = name;
		this.numberOfItems = numberOfItems;
		this.capacity = capacity;
		this.itemPair = pair;
		this.config = config;
		// the second half is for children only
		this.population = new Solution[config.getPopSize()*2];
		this.bestWorstPair = new double[config.getNumberOf()+1][2];
		this.createSolution();
		// record the first generation
		setBestWorstPair(0, getBest().getObjectiveValue(), 
				getWorst().getObjectiveValue());
	}
	
	/***
	 * Initialize first solution based on greedy search's result, and randomly flip some 1 to 
	 * generate other solutions. Ensure that answers will not repeat, if can't find non-repeat
	 * solution in ten times, just randomly generate a new solution
	 */
	public void createSolution() {
		GreedyHeuristic greedy = new GreedyHeuristic();
		int[] representation = greedy.applyHeuristic(this);
		population[0] = new Solution(representation,capacity,itemPair);
		
		// create a set of copys of greedy's result
		int[][] rep = new int[config.getPopSize()][representation.length];
		for (int j = 1; j < config.getPopSize(); j++) {
			for (int i = 0; i < representation.length; i++) {
	        	rep[j][i] = representation[i];
	        }
			
			int counter = 0;
			boolean repeat = true;
			while (repeat & counter++ < 10) {
				repeat = false;
				for (int i = 0; i < numberOfItems; i++) {
					// randomly remove some items from greedy's result
					if (getRandom().nextDouble() > 0.8 & rep[j][i] == 1) {
						rep[j][i] = 0;
					}
				}
		
				// check if population have repeat representation
				for (int i = 0; i < j; i++) {
					if (Arrays.equals(getPopulation(i).getRepresentation(), rep[j])) {
						repeat = true;
					}
				}
				
				// create new solution if it dose not repeat
				if (!repeat) {
					population[j] = new Solution(rep[j],capacity,itemPair);
					counter = 0;
				}
			}
			
			// if ten rounds can not find a non-repeat solution, then randomly generate
			if (counter >= 10) {
				for (int i = 0; i < numberOfItems; i++) {
					if (getRandom().nextDouble() > 0.5) {
						rep[j][i] = 0;
					}
				}
				population[j] = new Solution(rep[j],capacity,itemPair);
			}
		}
		
		// initialize childern's solutions to 0
		for (int i = config.getPopSize(); i < config.getPopSize() * 2; i++) {
			int[] childRep = new int[numberOfItems];
			population[i] = new Solution(childRep,capacity,itemPair);
		}
	}
	
	/***
	 * Initialize solutions randomly
	 */
	public void createSolution1() {
		for (int j = 0; j < config.getPopSize(); j++) {
			int[] representation = new int[numberOfItems];
			for (int i = 0; i < numberOfItems; i++) {
				if (getRandom().nextDouble() > 0.5) {
					representation[i] = 1;
				}
			}
			population[j] = new Solution(representation,capacity,itemPair);
		}
		
		for (int i = config.getPopSize(); i < config.getPopSize() * 2; i++) {
			int[] childRep = new int[numberOfItems];
			population[i] = new Solution(childRep,capacity,itemPair);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumberOfItems() {
		return numberOfItems;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public ItemPair[] getItemPair() {
		return itemPair;
	}
	
	public TestFrameConfig getConfig() {
		return config;
	}
	
	public Solution getPopulation(int i) {
		return population[i];
	}
	
	public double[][] getBestWorstPair() {
		return bestWorstPair;
	}
	
	public void setBestWorstPair(int index, double best, double worst) {
		bestWorstPair[index][0] = best;
		bestWorstPair[index][1] = worst;
	}
	
	public Random getRandom() {
		return config.getRandom();
	}
	
	/***
	 * Get worst solution from current population
	 * @return a solution indicates current worst solution
	 */
	public Solution getWorst() {
		int index = 0;
		double min = getPopulation(0).getObjectiveValue();
		for (int i = 1; i < config.getPopSize(); i++) {
			if (min > getPopulation(i).getObjectiveValue()) {
				min = getPopulation(i).getObjectiveValue();
				index = i;
			}
		}
		return getPopulation(index);
	}
	
	/***
	 * Get best solution from current population
	 * @return a solution indicates current best solution
	 */
	public Solution getBest() {
		int index = 0;
		double max = getPopulation(0).getObjectiveValue();
		for (int i = 1; i < config.getPopSize(); i++) {
			if (max < getPopulation(i).getObjectiveValue()) {
				max = getPopulation(i).getObjectiveValue();
				index = i;
			}
		}
		return getPopulation(index);
	}
}
