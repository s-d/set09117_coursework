import java.util.ArrayList;

//class for storage of customers in the same route, and calculating cost/saving
public class Route implements Comparable<Route> {

	// variable declaration
	private ArrayList<Customer> _customerList;
	private static Customer _depot;
	private double _saving;
	private int _requirment;

	// constructor
	public Route() {
		_customerList = new ArrayList<Customer>();
		_saving = 0;
		_requirment = 0;
	}

	// calculate distance saving of a route
	public void calcSaving() {
		double depotCost = 0, routeCost = 0;

		// reset saving each time calculation is performed
		_saving = 0;
		// loop through each customer in the route
		for (Customer c : this._customerList) {
			// calculate distance travelled if only that customer is visited
			depotCost += _depot.distance(c);
		}
		// loop through each customer
		for (int i = 0; i < this._customerList.size() - 1; i++) {
			// calculate distance between each customer and its neighbour
			routeCost += _customerList.get(i).distance(_customerList.get(i + 1));
		}
		// saving is difference between single customer cost and route cost
		_saving = depotCost - routeCost;
	}

	// customer comparable used for sorting collections of routes by saving
	@Override
	public int compareTo(Route compareRoute) {
		if (this._saving < compareRoute._saving) {
			return 1;
		} else {
			return -1;
		}
	}

	// append a customer to either end of the array
	public void addToEnd(Customer cust) {
		_customerList.add(cust);
		_requirment += cust.c;
	}

	public void addToStart(Customer cust) {
		_customerList.add(0, cust);
		_requirment += cust.c;
	}

	// setters and getters
	public static void set_depot(Customer _depot) {
		Route._depot = _depot;
	}

	public Customer getLastCustomer() {
		return _customerList.get(_customerList.size() - 1);
	}

	public Customer getFirstCustomer() {
		return _customerList.get(0);
	}

	public ArrayList<Customer> get_customerList() {
		return _customerList;
	}

	public double get_saving() {
		return _saving;
	}

	public int get_requirment() {
		return _requirment;
	}

}