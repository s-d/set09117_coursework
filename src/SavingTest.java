// import java.util.ArrayList;
//
// public class SavingTest {
//
// ArrayList<Integer> number = new ArrayList<>();
// int _depot = 0;
// double _saving;
//
// public void calcSaving() {
// if (number.size()==2) {
// int d = _depot;
// int c1 = number.get(0);
// int c2 = number.get(1);
// _saving = ((d+c1) + (d +c2)) - c1.distance(c2);
// } else {
// double single = 0, route = 0;
// for (Customer c : this._customerList) {
// single += _depot.distance(c) * 2;
// }
// route += _depot.distance(getFirstCustomer());
// route += _depot.distance(getLastCustomer());
// for (int i = 1; i < this._customerList.size() - 1; i++) {
// route += _customerList.get(i).distance(_customerList.get(i));
// _saving = single - route;
// }
// }
// }
