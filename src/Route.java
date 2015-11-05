import java.util.ArrayList;

public class Route implements Comparable<Route> {

	private ArrayList<Customer> _customerList;
	private static Customer _depot;
	private double _saving;
	private int _requirment;
	private boolean _pair;

	public Route() {
		_customerList = new ArrayList<Customer>();
		_saving = 0;
		_requirment = 0;
		_pair = false;
	}

	public void calcSaving() {
		_saving = 0;
		// if (_pair) {
		// Customer d, c1, c2;
		// d = _depot;
		// c1 = getFirstCustomer();
		// c2 = getLastCustomer();
		// _saving = (d.distance(c1) + d.distance(c2)) - c1.distance(c2);
		// } else {
		double depotCost = 0, routeCost = 0;
		for (Customer c : this._customerList) {
			depotCost += _depot.distance(c);
		}

		for (int i = 0; i < this._customerList.size() - 1; i++) {
			routeCost += _customerList.get(i).distance(_customerList.get(i + 1));
			_saving = depotCost - routeCost;
		}
	}

	// }

	public void merge(Route r) {
		for (Customer c : r.get_customerList()) {
			if (this._customerList.contains(c) == false) {
				addToEnd(c);
			}
		}
	}

	public void addToEnd(Customer cust) {
		_customerList.add(cust);
		_requirment += cust.c;
		if (this._customerList.size() == 2) {
			_pair = true;
		}
	}

	public void addToStart(Customer cust) {
		_customerList.add(0, cust);
		_requirment += cust.c;
		if (this._customerList.size() == 2) {
			_pair = true;
		}
	}

	public static void set_depot(Customer _depot) {
		Route._depot = _depot;
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

	public Customer getLastCustomer() {
		return _customerList.get(_customerList.size() - 1);
	}

	public Customer getFirstCustomer() {
		return _customerList.get(0);
	}

	@Override
	public int compareTo(Route compareRoute) {
		if (this._saving < compareRoute._saving) {
			return 1;
		} else {
			return -1;
		}
	}
}