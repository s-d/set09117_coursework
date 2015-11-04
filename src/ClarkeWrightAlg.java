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
		_pairs.sort(null);

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
						// decrement j to ensure position is kept relative
						j--;
					}
				}
			}
		}
	}

	// combine pairs to create full routes
	private void buildRoutes() {
		// ArrayList for routes whilst being built
		this._routeList = new ArrayList<Route>();
		// loop through every pair of customers
		for (Route r : _pairs) {
			// get customer from pair
			Customer c1 = r.getFirstCustomer();
			Customer c2 = r.getLastCustomer();
			// create booleans for each customer
			boolean cust1 = false, cust2 = false;
			// check if either customer from pair is already route
			for (Route route : _routeList) {
				if (route.get_customerList().contains(c1)) {
					cust1 = true;
				}
				if (route.get_customerList().contains(c2)) {
					cust2 = true;
				}
			}
			// if neither customer is route
			if (cust1 == false && cust2 == false) {
				// check that pair does not go over capacity
				if (c1.c + c2.c <= _prob.depot.c) {
					// makes new route out of pair
					Route newR = new Route();
					newR.addToEnd(c1);
					newR.addToEnd(c2);
					_routeList.add(newR);
				}
				// if first customer not in route
			} else if (cust1 == false) {
				for (Route route : _routeList) {
					// check if any route ends in customer 2
					if (route.getLastCustomer() == c2) {
						// check if merging would go over capacity
						if ((route.get_requirment() + c1.c) <= _prob.depot.c) {
							// append customer to end of route
							route.addToEnd(c1);
						}
						// check if route starts with customer 2
					} else if (route.getFirstCustomer() == c2) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + c1.c) <= _prob.depot.c) {
							// add customer to start
							route.addToStart(c1);
						}
					}
				}
				// if second customer is not in a route
			} else if (cust2 == false) {
				for (Route route : _routeList) {
					// check if any routes end with customer 1
					if (route.getLastCustomer() == c1) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + c2.c) <= _prob.depot.c) {
							// append customer to end of route
							route.addToEnd(c2);
						}
						// check if any routes begin with customer 1
					} else if (route.getFirstCustomer() == c1) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + c2.c) <= _prob.depot.c) {
							// append customer to start of route
							route.addToStart(c2);
						}
					}
				}
			}
		}
		sortRoutes();
		for (int i = 0; i < _routeList.size(); i++) {
			for (int j = 0; j < _routeList.size(); j++) {
				Route r1 = _routeList.get(i);
				Route r2 = _routeList.get(j);

				if (r1.equals(r2) != true) {
					int mergeCap = r1.get_requirment() + r2.get_requirment();
					if (mergeCap <= _prob.depot.c) {
						r1.merge(r2);
						_routeList.remove(j);
					}
				}
			}

		}
		// move routes to solution array
		for (Route r : _routeList) {
			_soln.add(r.get_customerList());
		}
	}

	private void sortRoutes() {
		Collections.sort(_routeList, new Comparator<Route>() {
			public int compare(Route r1, Route r2) {
				return r2.get_requirment() - r1.get_requirment();
			}
		});
	}

}
// end of class