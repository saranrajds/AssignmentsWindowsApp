package ticketreservation.model;

import java.util.ArrayList;

public class Train {

	private int Number;
	private String Name;
	private int Fare;
	private String DepartureTime;
	private String ArrivalTime;
	private ArrayList<String> Routes;
	private String FromStation;
	private String ToStatison;
	
	public String getFromStation() {
		return FromStation;
	}
	public void setFromStation(String fromStation) {
		FromStation = fromStation;
	}
	public String getToStatison() {
		return ToStatison;
	}
	public void setToStatison(String toStatison) {
		ToStatison = toStatison;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getFare() {
		return Fare;
	}
	public void setFare(int fare) {
		Fare = fare;
	}
	public String getDepartureTime() {
		return DepartureTime;
	}
	public void setDepartureTime(String departureTime) {
		DepartureTime = departureTime;
	}
	public String getArrivalTime() {
		return ArrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		ArrivalTime = arrivalTime;
	}
	public ArrayList<String> getRoutes() {
		return Routes;
	}
	public void setRoutes(ArrayList<String> routes) {
		Routes = routes;
	}
		
}
