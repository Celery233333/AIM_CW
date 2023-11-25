package hyperheuristic;

import heuristic.Heuristic;
import heuristic.CrossoverHeuristic;

/***
 * HeuristicSet class stores a set of crossover, muatation and local search heuristic
 * @author Cheng Qin
 */
public class HeuristicSet {

    private final CrossoverHeuristic h1;
    
    private final Heuristic h2;
    
    private final Heuristic h3;

    public HeuristicSet(CrossoverHeuristic h1, Heuristic h2, Heuristic h3) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
    }

    public CrossoverHeuristic geth1() {
        return h1;
    }

    public Heuristic geth2() {
        return h2;
    }
    
    public Heuristic geth3() {
        return h3;
    }
}