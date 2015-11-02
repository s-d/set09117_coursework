
public class Demo {

	public static void main(String[] args) throws Exception {

		VRProblem p = new VRProblem("src/test_data/rand00030prob.csv");
		VRSolution s = new VRSolution(p);

		double startTime = System.currentTimeMillis();

		s.betterSolution();

		double endTime = System.currentTimeMillis();

		System.out.println("Cost =" + s.solnCost());

		System.out.println("time taken =" + (endTime - startTime));

		s.writeOut("MySolution.csv");
		s.writeSVG("rand00030prob.svg", "MyPictureSolution.svg");
	}
}