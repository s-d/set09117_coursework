
public class Demo {

	public static void main(String[] args) {

		try {
			VRProblem p = new VRProblem("src/test_data/rand00040prob.csv");

			VRSolution s = new VRSolution(p);

			double startTime = System.currentTimeMillis();

			s.oneRoutePerCustomerSolution();

			double endTime = System.currentTimeMillis();

			System.out.println("Cost =" + s.solnCost());

			System.out.println("time taken =" + (endTime - startTime));
			try {
				s.writeOut("src/solutions/MySolution.csv");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				s.writeSVG("rand00040prob.svg", "MyPictureSolution.svg");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
