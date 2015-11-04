import java.util.ArrayList;
import java.util.List;

public class Run {

	public static void main(String[] args) throws Exception {
		String problem = "rand00050prob";
		double cwCost;
		List<Double> times = new ArrayList<>();
		int loop = 10;
		for (int i = 0; i < loop; i++) {
			System.out.println(problem);
			VRProblem p = new VRProblem(
					"C:/Users/Sam Dixon/Documents/set09117_coursework/test_data/" + problem + ".csv");
			VRSolution s = new VRSolution(p);

			double startTime = System.currentTimeMillis();
			s.betterSolution();
			double endTime = System.currentTimeMillis();

			cwCost = s.solnCost();

			if (s.verify()) {
				System.out.println("valid solution");
			}
			System.out.println("time taken =" + (endTime - startTime));

			s.writeOut("C:/Users/Sam Dixon/Documents/set09117_coursework/solutions/" + problem + "Solution.csv");
			s.writeSVG("C:/Users/Sam Dixon/Documents/set09117_coursework/solutions/" + problem + ".svg",
					"solutions/" + problem + "PictureSolution.svg");
			System.out.println("clarke wright cost = " + cwCost);
			double time = endTime - startTime;
			times.add(time);
		}
		System.out.println(times);
	}
}