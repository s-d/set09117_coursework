import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClarkeWrightAlg {
	// variables
	private ArrayList<Route> _pairs;
	private VRProblem _prob;
	private ArrayList<Route> _routeList;
	private ArrayList<ArrayList<Customer>> _soln;

	// create routes using Clarke Wright Algorithm
	public ArrayList<ArrayList<Customer>> solution(VRProblem prob) {
		this._prob = prob;
		this._soln = new ArrayList<ArrayList<Customer>>();
		createPairs();

		Route.set_depot(_prob.depot);
		for (Route r : _pairs) {
			r.calcSaving();
		}
		removeMirrors();
		sortPairs();
		buildRoutes();
		return _soln;
	}

	// creates routes for every possible pair of customers
	private void createPairs() {
		// set the depot of all routes so costs can be calculated later
		Route.set_depot(_prob.depot);
		// declare ArrayList for storage of pairs
		this._pairs = new ArrayList<Route>();
		// loop through every customer
		for (int i = 0; i < _prob.customers.size(); i++) {
			// loop through every customer
			for (int j = 0; j < _prob.customers.size(); j++) {
				// if the pair is not the same customer twice
				if (i != j) {
					// create a new route
					Route route = new Route();
					// add each customer to route
					route.addToEnd(_prob.customers.get(i));
					route.addToEnd(_prob.customers.get(j));
					// add route to ArrayList
					_pairs.add(route);
				}
			}
		}
	}

	// remove any redundant pairs
	private void removeMirrors() {
		// loop through every pair
		for (int i = 0; i < _pairs.size(); i++) {
			// loop through every pair after i
			for (int j = i; j < _pairs.size(); j++) {
				// if both pairs are not the same
				if (i != j) {
					// assign temporary objects
					Route r1 = _pairs.get(i);
					Route r2 = _pairs.get(j);
					// if routes mirror each other
					if (r1.get_customerList().get(0).equals(r2.get_customerList().get(1))
							&& (r1.get_customerList().get(1).equals(r2.get_customerList().get(0)))) {
						// remove redundant pair
						_pairs.remove(j);
						// decrement j to ensure position is kept
						j--;
					}
				}
			}
		}
	}

	// sort pairs so highest savings are first
	private void sortPairs() {
		Collections.sort(_pairs, new Comparator<Route>() {
			@Override
			public int compare(Route r1, Route r2) {
				return Double.compare(r2.get_saving(), r1.get_saving());
			}
		});
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
			_soln.add(r.get_customerList());
		}
	}

}
