
public class Run {

	public static void main(String[] args) throws Exception {
		String problem = "rand01000prob";
		double srCost, cwCost;

		System.out.println(problem);
		VRProblem p = new VRProblem("C:/Users/Sam Dixon/Documents/set09117_coursework/test_data/" + problem + ".csv");
		VRSolution s = new VRSolution(p);

		s.oneRoutePerCustomerSolution();
		// System.out.println("Cost = " + s.solnCost());
		srCost = s.solnCost();

		double startTime = System.currentTimeMillis();

		s.betterSolution();
		cwCost = s.solnCost();

		double endTime = System.currentTimeMillis();

		// System.out.println("Cost = " + s.solnCost());

		if (s.verify()) {
			System.out.println("valid solution");
		} else {
			System.out.println("bm.");
		}

		System.out.println("time taken =" + (endTime - startTime));

		s.writeOut("C:/Users/Sam Dixon/Documents/set09117_coursework/solutions/" + problem + "Solution.csv");
		s.writeSVG("C:/Users/Sam Dixon/Documents/set09117_coursework/solutions/" + problem + ".svg",
				"solutions/" + problem + "PictureSolution.svg");

		System.out.println("single route cost = " + srCost);
		System.out.println("clarke wright cost = " + cwCost);
		System.out.println("diffrence = " + (srCost - cwCost));
	}
}