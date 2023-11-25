package frame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/***
 * SolutionOutput class provides printer and file writer for output 
 * @author Cheng Qin
 */
public class SolutionOutput {

	/***
	 * print and write this trial's result according to CW's requirement
	 * @param instance an Instance variable inherits from CWRunner class
	 * @param trial an int indicates which trial to manipulate
	 */
	public void outputSolution (Instance instance, int trial) {
		Solution bestSolution = instance.getBest();
		double[][] bestWorstPair = instance.getBestWorstPair();
		String string = new String();
		try {
			// print best solution and its objectivevalue
			BufferedWriter out = new BufferedWriter(new FileWriter("output.txt",true));
			string += "Trial #" + trial + "\n" + bestSolution.getObjectiveValue() + "\n";
			for (int i : bestSolution.getRepresentation()) {
				string += i;
			}
			string += "\n";
			
			// print the best-worst pair
			if (instance.getConfig().getNumberOf() <= instance.getConfig().getTotalRuns()) {
				for (int i = 0; i < instance.getConfig().getNumberOf(); i++) {
					string += bestWorstPair[i][0] + "\t" + bestWorstPair[i][1] + "\n";
				}
			} else {
				for (int i = 0; i < instance.getConfig().getTotalRuns(); i++) {
					string += bestWorstPair[i][0] + "\t" + bestWorstPair[i][1] + "\n";
				}
			}
			
			out.write(string+"\n");
			System.out.println(string);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Destroey file if it exists
	 */
	public void destroeyFile() {
		File file = new File("output.txt");
		if (file.exists()) {
			file.delete();
		}
	}
}
