package frame;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import ruuner.TestFrameConfig;

/***
 * InstanceReader class creates a reader to handle text file
 * @author Cheng Qin
 */
public class InstanceReader {
	
	/***
	 * Choose a text file according to config, then create an instance of it
	 * @param config a configuration file indicates user's parameter turning
	 * @return an instance of current problem
	 */
	public Instance readInstance(TestFrameConfig config) {
		// initialize variable for creating instances
		String name = null;
		int numberOfItems = 0;
		double capacity = 0;
		ItemPair[] pair = null;
		BufferedReader reader;
		
		try {
			// choose a file to read according to the instance ID
			Path path = null;
			switch (config.getInstance()) {
				case 1:
					path = Paths.get("instances\\test1_4_20.txt");
					break;
				case 2:
					path = Paths.get("instances\\test2_10_269.txt");
					break;
				case 3:
					path = Paths.get("instances\\test3_20_879.txt");
					break;
				case 4:
					path = Paths.get("instances\\hidden1_5_80.txt");
					break;
				case 5:
					path = Paths.get("instances\\hidden2_7_50.txt");
					break;
				case 6:
					path = Paths.get("instances\\hidden3_10_60.txt");
					break;	
				case 7:
					path = Paths.get("instances\\hidden4_15_375.txt");
					break;
				case 8:
					path = Paths.get("instances\\hidden5_23_10000.txt");
					break;
				default:
					System.err.println("Do not have Instance "+config.getInstance());
			}
			
			// initialize variable to read files
			reader = Files.newBufferedReader(path);
			String thisLine;
			StringTokenizer st;
			int count = 0;
			
			// get the name of the file
			st = new StringTokenizer(path.toString(),"\\");
			st.nextToken();
			name = st.nextToken();
			st = new StringTokenizer(name,".");
			name = st.nextToken();
			
			while ((thisLine = reader.readLine()) != null) {
				st = new StringTokenizer(thisLine);
				
				// read the first line from the file
				if (numberOfItems == 0) {
					numberOfItems = Integer.parseInt(st.nextToken());
					capacity = Double.parseDouble(st.nextToken());
					pair = new ItemPair[numberOfItems];
				} else {
					// read next lines from the file
					double profit = Double.parseDouble(st.nextToken());
					double weight = Double.parseDouble(st.nextToken());
					pair[count++] = new ItemPair(profit,weight);
				}
			}
			
			return new Instance(name,numberOfItems,capacity,pair,config);
		} catch (IOException e) {
			e.printStackTrace();
        }
		
		return null;
	}
	
}
