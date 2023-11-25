package frame;

import java.math.BigDecimal;
import java.util.ArrayList;

/***
 * DeltaEvaluation class provides delta evaluaton methods to speed up calculation
 * @author Cheng Qin
 */
public class DeltaEvaluation {
	
	private final ItemPair[] pair;
	
	private final BigDecimal capacity;
	
	public DeltaEvaluation(ItemPair[] pair, double capacity) {
		this.pair = pair;
		this.capacity = new BigDecimal(String.valueOf(capacity));
	}
	
	/***
	 * Delta evaluation for single bit changed, and calculate new solution based on the old solution
	 * @param oldRep an int array indicates the representaton of old solution
	 * @param oldArray a double array contains objective value, profit and weight of old solution
	 * @param index an int indicates index that which bit is changed in old representaton
	 * @return a new double array contains objective value, profit and weight of new solution
	 */
	public double[] deltaEvaluate1(int[] oldRep, double[] oldArray, int index) {
		double[] newArray = oldArray.clone();
		BigDecimal newProfit = new BigDecimal(String.valueOf(oldArray[1]));
		BigDecimal newWeight = new BigDecimal(String.valueOf(oldArray[2]));
		
		// check whether current item is added or removed
		BigDecimal differ;
		if (oldRep[index] == 1) {
			differ = new BigDecimal(-1);
		} else {
			differ = new BigDecimal(1);
		}
		
		// caculate new profit and weight
		BigDecimal itemProfit = new BigDecimal(String.valueOf(pair[index].getProfit())).multiply(differ);
		BigDecimal itemWeight = new BigDecimal(String.valueOf(pair[index].getWeight())).multiply(differ);
		
		newProfit = newProfit.add(itemProfit);
		newWeight = newWeight.add(itemWeight);
		
		newArray[1] = newProfit.doubleValue();
		newArray[2] = newWeight.doubleValue();
		
		// calculate new objective value
		if (newArray[2] <= capacity.doubleValue()) {
			newArray[0] = newArray[1];
		} else {
			BigDecimal newObjective = capacity.subtract(newWeight);
			newArray[0] = newObjective.doubleValue();
		}
		
		return newArray;
	}
	
	/***
	 * Delta evaluation for multiple bits changed, and calculate new solution based on the old solution
	 * @param oldRep an int array indicates the representaton of old solution
	 * @param oldArray a double array contains objective value, profit and weight of old solution
	 * @param index a set of index indicates index that which bits are changed in old representaton
	 * @return a new double array contains objective value, profit and weight of new solution
	 */
	public double[] deltaEvaluate2(int[] oldRep, double[] oldArray, ArrayList<Integer> index) {
		double[] newArray = oldArray.clone();
		BigDecimal newProfit = new BigDecimal(String.valueOf(oldArray[1]));
		BigDecimal newWeight = new BigDecimal(String.valueOf(oldArray[2]));
		
		for (int i : index) {
			// check whether current item is added or removed
			BigDecimal differ;
			if (oldRep[i] == 1) {
				differ = new BigDecimal(-1);
			} else {
				differ = new BigDecimal(1);
			}
			
			// caculate new profit and weight
			BigDecimal itemProfit = new BigDecimal(String.valueOf(pair[i].getProfit())).multiply(differ);
			BigDecimal itemWeight = new BigDecimal(String.valueOf(pair[i].getWeight())).multiply(differ);
			
			newProfit = newProfit.add(itemProfit);
			newWeight = newWeight.add(itemWeight);
		}
		
		newArray[1] = newProfit.doubleValue();
		newArray[2] = newWeight.doubleValue();
		
		// calculate new objective value
		if (newArray[2] <= capacity.doubleValue()) {
			newArray[0] = newArray[1];
		} else {
			BigDecimal newObjective = capacity.subtract(newWeight);
			newArray[0] = newObjective.doubleValue();
		}
		
		return newArray;
	}
}
