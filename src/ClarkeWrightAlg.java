import java.util.ArrayList;

public class ClarkeWrightAlg {
	// variables
	private ArrayList<Route> _pairs;
	private VRProblem _prob;
	private ArrayList<ArrayList<Customer>> _soln;

	// create routes using Clarke & Wright Algorithm
	public ArrayList<ArrayList<Customer>> solution(VRProblem prob) {
		this._prob = prob;
		this._soln = new ArrayList<ArrayList<Customer>>();
		createPairs();
		_pairs.sort(null);
		for (Route route : simple()) {
			_soln.add(route.get_customerList());
		}
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
			for (int j = i; j < _prob.customers.size(); j++) {
				// if the pair is not the same customer twice
				if (i != j) {
					// create a new route
					Route route = new Route();
					// add each customer to route
					route.addToEnd(_prob.customers.get(i));
					route.addToEnd(_prob.customers.get(j));
					// Calculate the saving
					route.calcSaving();
					// add route to ArrayList
					_pairs.add(route);
				}
			}
		}
	}

	// combine pairs to create full routes
	private ArrayList<Route> simple() {
		// declare variables
		ArrayList<Route> routes = new ArrayList<Route>();
		Route currentPair;
		Customer cust1, cust2;
		boolean c1, c2;
		// loop through every pair of customers
		for (int i = 0; i < _pairs.size(); i++) {
			// get customer from pair
			currentPair = _pairs.get(i);
			cust1 = currentPair.getFirstCustomer();
			cust2 = currentPair.getLastCustomer();
			// create booleans for each customer
			c1 = false;
			c2 = false;
			// check if either customer from pair is already route
			for (int j = 0; j < routes.size(); j++) {
				if (routes.get(j).get_customerList().contains(cust1)) {
					c1 = true;
				}
				if (routes.get(j).get_customerList().contains(cust2)) {
					c2 = true;
				}
			}
			// if neither customer is route
			if (c1 == false && c2 == false) {
				// check that pair does not go over capacity
				if (cust1.c + cust2.c <= _prob.depot.c) {
					// makes new route out of pair
					Route newRoute = new Route();
					newRoute.addToEnd(cust1);
					newRoute.addToEnd(cust2);
					routes.add(newRoute);
					_pairs.remove(currentPair);
					i--;
				}
				// if first customer not in route
			} else if (c1 == false) {
				for (Route route : routes) {
					// check if any route ends in customer 2
					if (route.getLastCustomer() == cust2) {
						// check if merging would go over capacity
						if ((route.get_requirment() + cust1.c) <= _prob.depot.c) {
							// append customer to end of route
							route.addToEnd(cust1);
							_pairs.remove(currentPair);
							i--;
						}
						// check if route starts with customer 2
					} else if (route.getFirstCustomer() == cust2) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + cust1.c) <= _prob.depot.c) {
							// add customer to start
							route.addToStart(cust1);
							_pairs.remove(currentPair);
							i--;
						}
					}
				}
				// if second customer is not in a route
			} else if (c2 == false) {
				for (Route route : routes) {

					// check if any routes end with customer 1
					if (route.getLastCustomer() == cust1) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + cust2.c) <= _prob.depot.c) {
							// append customer to end of route
							route.addToEnd(cust2);
							_pairs.remove(currentPair);
							i--;
						}
						// check if any routes begin with customer 1
					} else if (route.getFirstCustomer() == cust1) {
						// check if merging will not go over capacity
						if ((route.get_requirment() + cust2.c) <= _prob.depot.c) {
							// append customer to start of route
							route.addToStart(cust2);
							_pairs.remove(currentPair);
							i--;
						}
					}
				}
			}
		}
		return routes;
	}

}