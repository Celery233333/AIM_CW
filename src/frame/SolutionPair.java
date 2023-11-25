package frame;

/***
 * SolutionPair class stores pair of representation and objective value
 * @author Cheng Qin
 */
public class SolutionPair {

    private int[] representation;
    
    private double objectiveValue;

    public SolutionPair(int[] representation, double objectiveValue) {
        this.representation = representation;
        this.objectiveValue = objectiveValue;
    }
    
    public void setRepresentation(int[] representation) {
    	this.representation = representation;
    }
    
    public void setObjectiveValue(double objectiveValue) {
    	this.objectiveValue = objectiveValue;
    }

    public int[] getRepresentation() {
        return representation;
    }

    public double getObjectiveValue() {
        return objectiveValue;
    }
}
