import java.util.ArrayList;
import java.util.List;

public class Execute {

	public static void main(String[] args) throws Exception {
		// single("rand00010prob");
		benchmark(1);
	}

	// calculates a solution for the provided problem
	private static void single(String prob) throws Exception {
		VRProblem p = new VRProblem("test_data/" + prob + ".csv");
		VRSolution s = new VRSolution(p);

		// start time
		double startTime = System.currentTimeMillis();
		// execute algorithm
		s.simpleRouteSolution();
		// stop time
		double endTime = System.currentTimeMillis();

		// creates a .csv of the solution
		s.writeOut("solutions/" + prob + "Solution.csv");
		// create .svg of the problem and the solution
		s.writeSVG("solutions/" + prob + ".svg", "solutions/" + prob + "Solution.svg");

		// name of problem
		System.out.println(prob);
		// verify solution is valid
		System.out.println(s.verify() + " solution");
		// returns number of routes needed to complete the problem
		System.out.println("routes: " + s.numberOfRoutes());
		// cost of problem
		System.out.println("cost: " + s.solnCost());
		// time to complete calculation
		System.out.println("time: " + (endTime - startTime));
	}

	// run each problem n times to create averages
	private static void benchmark(int n) throws Exception {
		// list of all problems
		String[] problems = new String[] { "rand00010prob", "rand00020prob", "rand00030prob", "rand00040prob",
				"rand00050prob", "rand00060prob", "rand00070prob", "rand00080prob", "rand00090prob", "rand00100prob",
				"rand00200prob", "rand00300prob", "rand00400prob", "rand00500prob", "rand00600prob", "rand00700prob",
				"rand00800prob", "rand00900prob", "rand01000prob" };

		// loop through every problem
		for (String prob : problems) {
			List<Double> times = new ArrayList<>();
			VRProblem p = new VRProblem("test_data/" + prob + ".csv");
			VRSolution s = new VRSolution(p);

			// run each problem n times
			for (int i = 0; i < n; i++) {
				double startTime = System.currentTimeMillis();
				s.simpleRouteSolution();
				double endTime = System.currentTimeMillis();

				// creates a .csv of the solution
				s.writeOut("solutions/" + prob + "Solution.csv");
				// create .svg of the problem and the solution
				s.writeSVG("solutions/" + prob + ".svg", "solutions/" + prob + "Solution.svg");

				// add time to list
				times.add(endTime - startTime);
			}
			// name of problem
			System.out.println(prob);
			// verify solution is valid
			System.out.println(s.verify() + " solution");
			// returns number of routes needed to complete the problem
			System.out.println("routes: " + s.numberOfRoutes());
			// cost of problem
			System.out.println("cost: " + s.solnCost());
			System.out.println(times);
			System.out.println();
		}
	}

}