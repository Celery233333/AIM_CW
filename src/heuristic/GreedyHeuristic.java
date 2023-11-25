package heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frame.Instance;

/***
 * GreedyHeuristic class implements a constructive greedy heuristic
 * @author Cheng Qin
 */
public class GreedyHeuristic {
	
	/***
	 * Choose the item has the best greedy value(profit/weight) if its weight does not exceeds the 
	 * capacity, if it exceeds then check the second best
	 * @param instance an Instance variable inherits from CWRunner class
	 * @return an int array indicates greedy heuristic's search result
	 */
	public int[] applyHeuristic(Instance instance) {
		int length = instance.getNumberOfItems();
		double capacity = instance.getCapacity();
		Map<Integer,Double> map = new HashMap<Integer,Double>();
		
		// calculate the greedy value using profit/weight
		for (int i = 0; i < length; i++) {
			map.put(i, instance.getItemPair()[i].getProfit() / instance.getItemPair()[i].getWeight());
		}
		List<Map.Entry<Integer,Double>> list = new ArrayList<>(map.entrySet());
		
		// sort items using profit
		Collections.sort(list, new Comparator<Map.Entry<Integer,Double>>() {
			@Override
			public int compare(Map.Entry<Integer,Double> o1, Map.Entry<Integer,Double> o2) { 
		        if ((o2.getValue() - o1.getValue())>0)
		            return 1;
		          else if((o2.getValue() - o1.getValue())==0)
		            return 0;
		          else 
		            return -1;
			}
		});
		
		int[] representation = new int[length];
		double weight = 0;
		for (int i = 0; i < list.size(); i++) {
			// get the index and weight of the item with max greedy value(first item in the list)
			int index = list.get(0).getKey();	
			weight += instance.getItemPair()[index].getWeight();

			if (weight > capacity) {
				// remove current item if it exceeds capacity
				weight -= instance.getItemPair()[index].getWeight();
				list.remove(0);
			} else {
				// flip the index bit if it does not exceed capacity, and remove current item
				list.remove(0);
				representation[index] = 1;
			}
		}

		return representation;
	}
}
