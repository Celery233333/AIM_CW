package frame;

import java.math.BigDecimal;

/***
 * Solution class represents solution of instance (population of problem)
 * @author Cheng Qin
 */
public class Solution {
	
	private int[] representation;
	
	private double capacity;
	
	private final ItemPair[] pair;
	
	private double profit;
	
	private double weight;
	
	private double objectiveValue;

	public Solution(int[] representation, double capacity, ItemPair[] pair) {
		this.representation = representation;
		this.capacity = capacity;
		this.pair = pair;
		this.initialize();
	}
	
	/***
	 * Calculate initial profit, weight and objective value according to the initial 
	 * representation, and then store it as best solution now
	 */
	public void initialize() {
		BigDecimal sumProfit = new BigDecimal(0);
		BigDecimal sumWeight = new BigDecimal(0);
		for (int i = 0; i < representation.length; i++) {
			if (representation[i] == 1) {
				// calculate the sum of profit and weight
				BigDecimal tempProfit = new BigDecimal(String.valueOf(pair[i].getProfit()));
				BigDecimal tempWeight = new BigDecimal(String.valueOf(pair[i].getWeight()));
				
				sumProfit = sumProfit.add(tempProfit);
				sumWeight = sumWeight.add(tempWeight);
			}
		}
		profit = sumProfit.doubleValue();
		weight = sumWeight.doubleValue();
		
		// use the negation of weight beyond capacity to represent objectiveValue when the solution is infeasible
		if (weight > capacity) {
			BigDecimal tempValue = new BigDecimal(0);
			tempValue = tempValue.add(new BigDecimal(String.valueOf(capacity)));
			tempValue = tempValue.subtract(sumWeight);
			objectiveValue = tempValue.doubleValue();
		} else {
			objectiveValue = profit;
		}
	}
	
	/***
	 * Calculate objective value, profit and weight use normal loop fro input solution
	 * @param representation an int array indicates the representation of solution
	 * @return a double array contains objective value, profit and weight of input representation
	 */
	public double[] calculateObjectiveValue(int[] representation) {
		BigDecimal deltaProfit = new BigDecimal(0);
		BigDecimal deltaWeight = new BigDecimal(0);
		
		// calculate the delta values
		for (int i = 0; i < representation.length; i++) {
			int differ = representation[i] - this.representation[i];
			if (differ != 0) {
				BigDecimal itemProfit = new BigDecimal(String.valueOf(differ * pair[i].getProfit()));
				BigDecimal itemWeight = new BigDecimal(String.valueOf(differ * pair[i].getWeight()));
				
				deltaProfit =  deltaProfit.add(itemProfit);
				deltaWeight = deltaWeight.add(itemWeight);
			}
		}
		
		BigDecimal tempProfit = new BigDecimal(String.valueOf(profit)).add(deltaProfit);
		BigDecimal tempWeight = new BigDecimal(String.valueOf(weight)).add(deltaWeight);
		
		// return an array consists of the objective value, profit and weight of input representation
		if (tempWeight.doubleValue() > capacity) {
			BigDecimal best = new BigDecimal(capacity).subtract(tempWeight);
			double[] array = {best.doubleValue(),tempProfit.doubleValue(),tempWeight.doubleValue()};
			return array;
		} else {
			double[] array = {tempProfit.doubleValue(),tempProfit.doubleValue(),tempWeight.doubleValue()};
			return array;
		}
	}
	
	/***
	 * Flip index bit of input representation
	 * @param representation an int array indicates representation of a solution
	 * @param index an int that index which bit to flip
	 */
	public void bitFilp(int[] representation, int index) {
		if (representation[index] == 1) {
			representation[index] = 0;
		} else {
			representation[index] = 1;
		}
	}
	
	public int[] getRepresentation() {
		return representation;
	}
	
	/***
	 * Update current solution based on the input representation and array
	 * @param representation an int array indicates representation of a solution
	 * @param array a double array contains objective value, profit and weight
	 */
	public void updateRepresentation(int[] representation, double array[]) {
		this.representation = clone(representation);
		this.objectiveValue = array[0];
		this.profit = array[1];
		this.weight = array[2];
	}
	
	public double getProfit() {
		return profit;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getObjectiveValue() {
		return objectiveValue;
	}
	
	public double[] getArray() {
		double[] array = {objectiveValue,profit,weight};
		return array;
	}
	
	// use to deep copy an int array
	public int[] clone(int[] representation) {
		int[] newRepresentation = new int[representation.length];
        for (int i = 0; i < representation.length; i++) {
        	newRepresentation[i] = representation[i];
        }
        return newRepresentation;
	}
}
