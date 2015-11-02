import java.util.*;

import java.io.*;

public class VRSolution {
	public VRProblem prob;
	public List<List<Customer>> soln;
	public List<Route> pairs;
	public List<Route> routeList;

	public VRSolution(VRProblem problem) {
		this.prob = problem;
	}

	// The dumb solver adds one route per customer
	public void oneRoutePerCustomerSolution() {
		this.soln = new ArrayList<List<Customer>>();
		for (Customer c : prob.customers) {
			ArrayList<Customer> route = new ArrayList<Customer>();
			route.add(c);
			soln.add(route);
		}
	}

	// Students should implement another solution
	public void betterSolution() {
		this.pairs = new ArrayList<Route>();
		for (int i = 0; i < prob.customers.size(); i++) {
			for (int j = 0; j < prob.customers.size(); j++) {
				Customer ci = prob.customers.get(i);
				Customer cj = prob.customers.get(j);
				Customer d = prob.depot;
				if (i != j) {
					Route route = new Route(prob.depot);
					route.addCustomer(ci);
					route.addCustomer(cj);
					pairs.add(route);
				}
			}
		}

		// System.out.println(savings.size());
		for (Route r : pairs) {
			r.calcSaving();
			// System.out.println(r.get_saving());
		}
		sortRoutes();
		// System.out.println(savings.size());
		// for (Route r : savings) {
		// System.out.println(r.get_saving());
		// }
		buildRoutes();
	}

	private void buildRoutes() {
		this.soln = new ArrayList<List<Customer>>();
		this.routeList = new ArrayList<Route>();
		for (Route r : pairs) {
			Customer c0 = r.get_customerList().get(0);
			Customer c1 = r.get_customerList().get(1);
			boolean cust0 = false, cust1 = false;
			// check if either customer from pair is already route
			for (Route route : routeList) {
				if (route.get_customerList().contains(c0)) {
					cust0 = true;
				}
				if (route.get_customerList().contains(c1)) {
					cust1 = true;
				}
			}
			// if neither customer is in route
			if (cust0 == false && cust1 == false) {
				if (c0.c + c1.c <= prob.depot.c) {
					Route newR = new Route();
					newR.addCustomer(c0);
					newR.addCustomer(c1);
					routeList.add(newR);
				}
				// if first customer not in route
			} else if (cust0 == false) {
				for (Route route : routeList) {
					if (route.get_lastDelivery() == c1) {
						if ((route.get_requirment() + c0.c) <= prob.depot.c) {
							route.addCustomer(c0);
						}
					} else if (route.get_customerList().get(0) == c1) {
						if ((route.get_requirment() + c0.c) <= prob.depot.c) {
							route.addToStart(c0);
						}
					}
				}
			} else if (cust1 == false) {
				for (Route route : routeList) {
					if (route.get_lastDelivery() == c0) {
						if ((route.get_requirment() + c1.c) <= prob.depot.c) {
							route.addCustomer(c1);
						}
					} else if (route.get_customerList().get(0) == c0) {
						if ((route.get_requirment() + c1.c) <= prob.depot.c) {
							route.addToStart(c1);
						}
					}
				}
			}
		}
		for (Route r : routeList) {
			soln.add(r.get_customerList());
		}
	}

	private void sortRoutes() {
		for (int i = 0; i < pairs.size(); i++) {
			for (int j = i; j < pairs.size(); j++) {
				Route ri = pairs.get(i);
				Route rj = pairs.get(j);
				if (i != j) {
					Route r1 = pairs.get(i);
					Route r2 = pairs.get(j);
					if (r1.get_customerList().get(0).equals(r2.get_customerList().get(1))
							&& (r1.get_customerList().get(1).equals(r2.get_customerList().get(0)))) {
						pairs.remove(j);
						j--;
					}
				}
			}
		}
		Collections.sort(pairs, new Comparator<Route>() {
			@Override
			public int compare(Route r1, Route r2) {
				return Double.compare(r2.get_saving(), r1.get_saving());
			}
		});
	}

	// Calculate the total journey
	public double solnCost() {
		double cost = 0;
		for (List<Customer> route : soln) {
			Customer prev = this.prob.depot;
			for (Customer c : route) {
				cost += prev.distance(c);
				prev = c;
			}
			// Add the cost of returning to the depot
			cost += prev.distance(this.prob.depot);
		}
		return cost;
	}

	public Boolean verify() {
		// Check that no route exceeds capacity
		Boolean okSoFar = true;
		for (List<Customer> route : soln) {
			// Start the spare capacity at
			int total = 0;
			for (Customer c : route)
				total += c.c;
			if (total > prob.depot.c) {
				System.out.printf("********FAIL Route starting %s is over capacity %d\n", route.get(0), total);
				okSoFar = false;
			}
		}

		// Check that we keep the customer satisfied
		// Check that every customer is visited and the correct amount is picked
		// up
		Map<String, Integer> reqd = new HashMap<String, Integer>();
		for (Customer c : this.prob.customers) {
			String address = String.format("%fx%f", c.x, c.y);
			reqd.put(address, c.c);
		}
		for (List<Customer> route : this.soln) {
			for (Customer c : route) {
				String address = String.format("%fx%f", c.x, c.y);
				if (reqd.containsKey(address))
					reqd.put(address, reqd.get(address) - c.c);
				else
					System.out.printf("********FAIL no customer at %s\n", address);
			}
		}
		for (String address : reqd.keySet())
			if (reqd.get(address) != 0) {
				System.out.printf("********FAIL Customer at %s has %d left over\n", address, reqd.get(address));
				okSoFar = false;
			}
		return okSoFar;
	}

	public void readIn(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s;
		this.soln = new ArrayList<List<Customer>>();
		while ((s = br.readLine()) != null) {
			ArrayList<Customer> route = new ArrayList<Customer>();
			String[] xycTriple = s.split(",");
			for (int i = 0; i < xycTriple.length; i += 3)
				route.add(new Customer((int) Double.parseDouble(xycTriple[i]),
						(int) Double.parseDouble(xycTriple[i + 1]), (int) Double.parseDouble(xycTriple[i + 2])));
			soln.add(route);
		}
		br.close();
	}

	public void writeSVG(String probFilename, String solnFilename) throws Exception {
		String[] colors = "chocolate cornflowerblue crimson cyan darkblue darkcyan darkgoldenrod".split(" ");
		int colIndex = 0;
		String hdr = "<?xml version='1.0'?>\n"
				+ "<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' '../../svg11-flat.dtd'>\n"
				+ "<svg width='8cm' height='8cm' viewBox='0 0 500 500' xmlns='http://www.w3.org/2000/svg' version='1.1'>\n";
		String ftr = "</svg>";
		StringBuffer psb = new StringBuffer();
		StringBuffer ssb = new StringBuffer();
		psb.append(hdr);
		ssb.append(hdr);
		for (List<Customer> route : this.soln) {
			ssb.append(String.format("<path d='M%s %s ", this.prob.depot.x, this.prob.depot.y));
			for (Customer c : route)
				ssb.append(String.format("L%s %s", c.x, c.y));
			ssb.append(String.format("z' stroke='%s' fill='none' stroke-width='2'/>\n",
					colors[colIndex++ % colors.length]));
		}
		for (Customer c : this.prob.customers) {
			String disk = String.format("<g transform='translate(%.0f,%.0f)'>"
					+ "<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>"
					+ "<text text-anchor='middle' y='5'>%d</text>" + "</g>\n", c.x, c.y, 10, c.c);
			psb.append(disk);
			ssb.append(disk);
		}
		String disk = String.format(
				"<g transform='translate(%.0f,%.0f)'>"
						+ "<circle cx='0' cy='0' r='%d' fill='pink' stroke='black' stroke-width='1'/>"
						+ "<text text-anchor='middle' y='5'>%s</text>" + "</g>\n",
				this.prob.depot.x, this.prob.depot.y, 20, "D");
		psb.append(disk);
		ssb.append(disk);
		psb.append(ftr);
		ssb.append(ftr);
		PrintStream ppw = new PrintStream(new FileOutputStream(probFilename));
		PrintStream spw = new PrintStream(new FileOutputStream(solnFilename));
		ppw.append(psb);
		spw.append(ssb);
		ppw.close();
		spw.close();
	}

	public void writeOut(String filename) throws Exception {
		PrintStream ps = new PrintStream(filename);
		for (List<Customer> route : this.soln) {
			boolean firstOne = true;
			for (Customer c : route) {
				if (!firstOne)
					ps.print(",");
				firstOne = false;
				ps.printf("%f,%f,%d", c.x, c.y, c.c);
			}
			ps.println();
		}
		ps.close();
	}
}