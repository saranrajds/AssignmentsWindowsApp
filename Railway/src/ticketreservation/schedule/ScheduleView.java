package ticketreservation.schedule;

import java.util.ArrayList;
import java.util.Scanner;

import ticketreservation.booking.BookingView;
import ticketreservation.model.Train;

public class ScheduleView {

	public ScheduleModel scheduleModel;

	public ScheduleView() {
		this.scheduleModel = new ScheduleModel(this);
	}

	public void initSchedule() {
		scheduleModel.loadTrain();
		scheduleModel.loadPassengers();
		scheduleModel.loadRicketBooking();
		scheduleModel.loadTicketDetails();
		System.out.println("Welcome To Indian Railway");
		System.out.println("--------------------------\n");
		showStartingOption();
	}

	private void showStartingOption() {

		Scanner s = new Scanner(System.in);

		System.out.println("1 -> Make Schedule");
		System.out.println("2 -> Booking Option");
		System.out.println("3 -> Show Train Info");
		System.out.println("9 -> Exit");

		System.out.print("Enter Your Choice : ");
		char choice = s.next().charAt(0);

		switch (choice) {
		case '1':
			makeScheduleOption();
			break;
		case '2':
			new BookingView().bookingInit();
			break;
		case '3':
			getTrainFullInfo();
			break;
		case '9':
			System.out.println("\nThank You");
			return;

		default:
			System.out.println("Please Enter Correct Choice");
			break;
		}
		showStartingOption();
	}

	private void getTrainFullInfo() {
		scheduleModel.getTrainFullInfo();
	}

	private void makeScheduleOption() {

		Scanner s = new Scanner(System.in);
		System.out.println("1 -> Add Train");
		System.out.println("2 -> Add Train Schedule");
		System.out.println("8 -> Back");

		System.out.print("\nEnter Your Choice : ");
		char choice = s.next().charAt(0);

		switch (choice) {
		case '1':
			addTrain();
			break;
		case '2':
			scheduleTrain();
			break;
		case '8':
			return;
		default:
			System.out.println("Please Enter Correct Choice");
			break;
		}
		makeScheduleOption();
	}

	private void scheduleTrain() {

		Scanner s = new Scanner(System.in);

		System.out.print("Enter Train Number : ");
		int trarinId = s.nextInt();

		scheduleModel.isTrainAvaiable(trarinId);
	}

	private void addTrain() {

		Scanner s = new Scanner(System.in);
		Train train = new Train();

		System.out.print("Enter Train Number : ");
		train.setNumber(s.nextInt());
		System.out.print("Enter Train Name : ");
		train.setName(s.next());

		boolean isTrainAdded = scheduleModel.addTrain(train);
	}

	public void showMessage(String msg) {
		System.out.println("\n" + msg + "\n");
	}

	public void getTrainInputFromUser(int trarinId) {

		Scanner s = new Scanner(System.in);
		Train train = new Train();
		train.setNumber(trarinId);
		
		System.out.print("Ente From Station : ");
		train.setFromStation(s.next());
		System.out.print("Enter To Station : ");
		train.setToStatison(s.next());		
		System.out.print("Ente Train Departure Time : ");
		train.setDepartureTime(s.next());
		System.out.print("Enter Train Arrival Time : ");
		train.setArrivalTime(s.next());
		System.out.print("Enter Fare Amount : ");
		train.setFare(s.nextInt());
		System.out.print("Enter Number of Train Routing List : ");
		int route = s.nextInt();
		ArrayList<String> routringList = new ArrayList<String>();
		for (int i = 0; i < route; i++) {
			System.out.print("Enter " + (i + 1) + " Routing Name : ");
			routringList.add(s.next());
		}
		train.setRoutes(routringList);
		scheduleModel.addTrainSchedule(train);
	}

	public void displayTrainInfo(ArrayList<Train> trains) {

		System.out.println("\nTrain Details\n");
		System.out.println("------------------------------------------------------------");
		System.out.println("\nNumber \t Name \t Routes \t Departure Time \t Arrival Time \t Fare ");
		System.out.println("------------------------------------------------------------");
		for (Train train : trains) {
			System.out.println(train.getNumber() + "\t" + train.getName() + "\t" + train.getFromStation() + "\t" + train.getDepartureTime() + "\t\t"
					+ train.getArrivalTime() + "\t" + train.getFare());
			if (train.getRoutes() != null) {
				for (String routeName : train.getRoutes()) {
					System.out.println("\t\t\t" + routeName);
				}
			}
			System.out.println("\t\t\t"+train.getToStatison());
			System.out.println("---------------------------------------------------------------------\n");
		}
	}
	
}
