import java.util.ArrayList;
import java.util.List;

public class Run {

	public static void main(String[] args) throws Exception {

		single("rand00010prob");
		benchmark(100);

	}

	private static void single(String prob) throws Exception {
		VRProblem p = new VRProblem("test_data/" + prob + ".csv");
		VRSolution s = new VRSolution(p);

		double startTime = System.currentTimeMillis();
		s.cw();
		double endTime = System.currentTimeMillis();

		s.writeOut("solutions/" + prob + "Solution.csv");
		s.writeSVG("solutions/" + prob + ".svg", "solutions/" + prob + "Solution.svg");

		System.out.println(prob);
		System.out.println(s.verify() + " solution");
		System.out.println("cost: " + s.solnCost());

		System.out.println("time: " + (endTime - startTime));
	}

	private static void benchmark(int loop) throws Exception {
		String[] problems = new String[] { "rand00010prob", "rand00020prob", "rand00030prob", "rand00040prob",
				"rand00050prob", "rand00060prob", "rand00070prob", "rand00080prob", "rand00090prob", "rand00100prob",
				"rand00200prob", "rand00300prob", "rand00400prob", "rand00500prob", "rand00600prob", "rand00700prob",
				"rand00800prob", "rand00900prob", "rand01000prob" };

		for (String prob : problems) {
			List<Double> times = new ArrayList<>();
			VRProblem p = new VRProblem("test_data/" + prob + ".csv");
			VRSolution s = new VRSolution(p);

			for (int i = 0; i < loop; i++) {
				double startTime = System.currentTimeMillis();
				s.cw();
				double endTime = System.currentTimeMillis();

				s.writeOut("solutions/" + prob + "Solution.csv");
				s.writeSVG("solutions/" + prob + ".svg", "solutions/" + prob + "Solution.svg");

				times.add(endTime - startTime);
			}
			System.out.println(prob);
			System.out.println(s.verify() + " solution");
			System.out.println("cost: " + s.solnCost());
			System.out.println(times + "\n");
		}
	}
}