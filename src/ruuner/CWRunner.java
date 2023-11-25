package ruuner;

import frame.Instance;
import frame.InstanceReader;
import frame.SolutionOutput;
import hyperheuristic.RLMMA_HH;

/***
 * CWRunner class acts as an entry to the whole program
 * @author Cheng Qin
 */
public class CWRunner {
	
	/***
	 * Initialize reader to read file and create instance, hyper-heuristic
	 * to solve problem, and printer to output results
	 */
	public void run() {
		InstanceReader reader = new InstanceReader();
		TestFrameConfig config = new TestFrameConfig();
		SolutionOutput output = new SolutionOutput();
		output.destroeyFile();
		
		// run times are based on the number of tirals
		for (int i = 0; i < config.getTotalTrials(); i++) {
			Instance instance = reader.readInstance(config);
			
			RLMMA_HH hyper = new RLMMA_HH();
			hyper.solve(instance);
			output.outputSolution(instance, i);
		}
	}
	
	public static void main(String [] args) {
		CWRunner runner = new CWRunner();
		runner.run();
	}
}
