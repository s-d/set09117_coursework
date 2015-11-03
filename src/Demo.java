
public class Demo {

	public static void main(String[] args) throws Exception {
		String problem = "rand00040prob";

		VRProblem p = new VRProblem("test_data/" + problem + ".csv");
		VRSolution s = new VRSolution(p);

		s.oneRoutePerCustomerSolution();
		System.out.println("Cost = " + s.solnCost());

		double startTime = System.currentTimeMillis();

		s.betterSolution();

		double endTime = System.currentTimeMillis();

		System.out.println("Cost = " + s.solnCost());

		System.out.println(s.verify());

		System.out.println("time taken =" + (endTime - startTime));

		s.writeOut("solutions/" + problem + "Solution.csv");
		s.writeSVG("solutions/" + problem + ".svg", "solutions/" + problem + "PictureSolution.svg");
	}
}