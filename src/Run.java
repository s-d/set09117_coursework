import java.util.ArrayList;
import java.util.List;

public class Run {

	public static void main(String[] args) throws Exception {
		String problem = "rand00010prob";
		List<Double> times = new ArrayList<>();
		int loop = 1;
		for (int i = 0; i < loop; i++) {
			VRProblem p = new VRProblem("test_data/" + problem + ".csv");
			VRSolution s = new VRSolution(p);

			double startTime = System.currentTimeMillis();
			s.betterSolution();
			double endTime = System.currentTimeMillis();

			s.writeOut("solutions/" + problem + "Solution.csv");
			s.writeSVG("solutions/" + problem + ".svg", "solutions/" + problem + "Solution.svg");

			System.out.println(problem);
			System.out.println(s.verify() + " solution");
			System.out.println("clarke wright cost = " + s.solnCost());
			times.add(endTime - startTime);
		}
		System.out.println(times);
	}
}