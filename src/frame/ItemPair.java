package frame;

/***
 * ItemPair class stores pair of profit and weight
 * @author Cheng Qin
 */
public class ItemPair {

    private final double profit;
    
    private final double weight;

    public ItemPair(double profit, double weight) {
        this.profit = profit;
        this.weight = weight;
    }

    public double getProfit() {
        return profit;
    }

    public double getWeight() {
        return weight;
    }
}