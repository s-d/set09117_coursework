
public class Demo {

	public static void main(String[] args) throws Exception {

		VRProblem p = new VRProblem("src/test_data/rand00010prob.csv");
		VRSolution s = new VRSolution(p);

		s.betterSolution();

		// try {
		// VRProblem p = new VRProblem("src/test_data/rand00010prob.csv");
		//
		// VRSolution s = new VRSolution(p);
		//
		// double startTime = System.currentTimeMillis();
		//
		// s.oneRoutePerCustomerSolution();
		//
		// double endTime = System.currentTimeMillis();
		//
		// System.out.println("Cost =" + s.solnCost());
		//
		// System.out.println("time taken =" + (endTime - startTime));
		// try {
		// s.writeOut("MySolution.csv");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// try {
		// s.writeSVG("rand00010prob.svg", "MyPictureSolution.svg");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}