# AIM-CW-2022
UoN AI Methods 2022 Coursework

## Author
Cheng Qin
20216424

## Java Version
JavaSE-17 from Eclipse
jdk-17.0.1

## Compile
run "CWRunner.main()" to start the application.

## Parameter Turning
all user's parameters can be changed in "TestFrameConfig" class.

## File Selector
you can change INSTANCE_ID in "TestFrameConfig" class to control which file to read. <br>
1 -> "test1_4_20.txt" <br>
2 -> "test2_10_269.txt" <br>
3 -> "test3_20_879.txt" <br>
4 -> "hidden1_5_80.txt" <br>
5 -> "hidden2_7_50.txt" <br>
6 -> "hidden3_10_60.txt" <br>
7 -> "hidden4_15_375.txt" <br>
8 -> "hidden5_23_10000.txt" <br>

## Algorithm Design
Population based hyper-heuristic: <br>
1) Initialize population, the first solution will directly set to greedy search's result, other solutions will randomly flip some 1 from greedy's result to generate non-repeat solutions (only try 10 times to find non-repeat, randomly generate a solution if fails). <br>
2) Roulette Wheel Selection was used to choose which heuristic set to use in next generation based on the propability of current_score/total_socres. <br>
3) Each heuristic set contained a crossover, mutation and hill climbing heuristic, which followed the idea of memetic algorithm. <br>
4) Each generation runs POP_SIZE/2 times memetic algorithm (the exact number of runs of mutation and hill climbing also depends on IOM and DOS), and tournament selection was used to choose parents for crossover here. <br>
5) Eliminate all parents, and replace the worst child with the best parent only if the parent has the overall best solution. <br>
6) Update the score of the current heuristic set, +1 for best solution in population has improved, and -1 for non-imrpoving. <br>
7) Back to Roulette Wheel Selection to select the heuristic set used by the next generation memetic algorithm, and continue the above steps until the number of generation exceeds TOTAL_RUNS set in "TestFrameConfig" class.