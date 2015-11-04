import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClarkeWrightAlg {
	private List<Route> _pairs;
	private VRProblem _prob;
	private List<Route> _routeList;
	List<List<Customer>> soln = new ArrayList<List<Customer>>();

	public List<List<Customer>> betterSolution(VRProblem prob) {
		this._prob = prob;
		this._pairs = new ArrayList<Route>();
		Route.set_depot(prob.depot);
		System.out.println("creating pairs...");
		for (int i = 0; i < prob.customers.size(); i++) {
			for (int j = 0; j < prob.customers.size(); j++) {
				Customer ci = prob.customers.get(i);
				Customer cj = prob.customers.get(j);
				if (i != j) {
					Route route = new Route();
					route.addToEnd(ci);
					route.addToEnd(cj);
					_pairs.add(route);
				}
			}
		}
		System.out.println("pairs created.");
		System.out.println("calculating savings for each pair...");
		for (Route r : _pairs) {
			r.calcSaving();
		}
		System.out.println("savings calcualted.");
		System.out.println("removing duplicates...");
		removeMirrors();
		System.out.println("duplicates removed.");
		System.out.println("sorting pairs...");
		sortPairs();
		System.out.println("sorting complete.");
		System.out.println("building routes...");
		buildRoutes();
		System.out.println("routes complete.");
		return soln;
	}

	private void createPairs() {
		Route.set_depot(_prob.depot);
		System.out.println("creating pairs...");
		for (int i = 0; i < _prob.customers.size(); i++) {
			for (int j = 0; j < _prob.customers.size(); j++) {
				Customer ci = _prob.customers.get(i);
				Customer cj = _prob.customers.get(j);
				if (i != j) {
					Route route = new Route();
					route.addToEnd(ci);
					route.addToEnd(cj);
					_pairs.add(route);
				}
			}
		}
	}

	private void buildRoutes() {
		// ArrayList for routes whilst being built
		this._routeList = new ArrayList<Route>();
		// loop through every pair of customers
		for (Route r : _pairs) {
			// get first and second customers
			Customer p1 = r.getFirstCustomer();
			Customer p2 = r.getLastCustomer();
			boolean cust0 = false, cust1 = false;
			// check if either customer from pair is already route
			for (Route route : _routeList) {
				if (route.get_customerList().contains(p1)) {
					cust0 = true;
				}
				if (route.get_customerList().contains(p2)) {
					cust1 = true;
				}
			}
			// if neither customer is in route
			if (cust0 == false && cust1 == false) {
				// check that pair does not go over capacity
				if (p1.c + p2.c <= _prob.depot.c) {
					// makes new route out of pair
					Route newR = new Route();
					newR.addToEnd(p1);
					newR.addToEnd(p2);
					_routeList.add(newR);
				}
				// if first customer not in route
			} else if (cust0 == false) {
				for (Route route : _routeList) {
					// check if customer can be added to end of route
					if (route.getLastCustomer() == p2) {
						if ((route.get_requirment() + p1.c) <= _prob.depot.c) {
							route.addToEnd(p1);
						}
						// check if customer can be added to front of route
					} else if (route.getFirstCustomer() == p2) {
						if ((route.get_requirment() + p1.c) <= _prob.depot.c) {
							route.addToStart(p1);
						}
					}
				}
				// if second customer is not in a route
			} else if (cust1 == false) {
				for (Route route : _routeList) {
					// check if it can be added to end of existing route
					if (route.getLastCustomer() == p1) {
						if ((route.get_requirment() + p2.c) <= _prob.depot.c) {
							route.addToEnd(p2);
						}
						// check if it can be added to front of route
					} else if (route.getFirstCustomer() == p1) {
						if ((route.get_requirment() + p2.c) <= _prob.depot.c) {
							route.addToStart(p2);
						}
					}
				}
			}
		}
		for (Route r : _routeList) {
			soln.add(r.get_customerList());

		}

	}

	private void sortPairs() {
		Collections.sort(_pairs, new Comparator<Route>() {
			@Override
			public int compare(Route r1, Route r2) {
				return Double.compare(r2.get_saving(), r1.get_saving());
			}
		});
	}

	private void removeMirrors() {
		for (int i = 0; i < _pairs.size(); i++) {
			for (int j = i; j < _pairs.size(); j++) {
				if (i != j) {
					Route r1 = _pairs.get(i);
					Route r2 = _pairs.get(j);
					if (r1.get_customerList().get(0).equals(r2.get_customerList().get(1))
							&& (r1.get_customerList().get(1).equals(r2.get_customerList().get(0)))) {
						_pairs.remove(j);
						j--;
					}
				}
			}
		}
	}

}
