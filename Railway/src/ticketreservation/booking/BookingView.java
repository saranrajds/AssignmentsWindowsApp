package ticketreservation.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ticketreservation.model.Passenger;
import ticketreservation.model.TicketDetail;
import ticketreservation.model.Train;

public class BookingView {

	private BookingModel bookingModel;
	
	public BookingView() {
		bookingModel = new BookingModel(this);
	}
	
	public void bookingInit() {
		showBookingOptions();
	}

	private void showBookingOptions() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("\n\n1 -> Booking");
		System.out.println("2 -> Get PNR Status");
		System.out.println("3 -> Booked Ticket");
		System.out.println("4 -> Cancel Ticket");
		System.out.println("5 -> Search Ticket");
		System.out.println("6 -> Change Ticket Status");
		System.out.println("7 -> List Train Route");
//		System.out.println("8 -> Add Train Route");
		System.out.println("9 -> Back");
		
		System.out.print("\nEnter Your Choice : ");
		char choice = s.next().charAt(0);
		
		switch (choice) {
		case '1':
			System.out.println("\n## Plan My Journey ##\n");
			bookTicket();
			break;
		case '2':
			getPNRStatus();
			break;
		case '3':
			getBookedDetails();
			break;
		case '4':
			onCancelTicket();
			break;
		case '9':
			return;
		default:
			System.out.println("Please Enter Correct Choice\n");
			break;
		}
		showBookingOptions();
	}

	private void onCancelTicket() {
		
		Scanner s = new Scanner(System.in);
		System.out.print("\nEnter PNR Number : ");
		int pnrNumber = s.nextInt();
		
		if(confirmation()) {
			bookingModel.onCancelTicket(pnrNumber);
		}
	}

	private boolean confirmation() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("\nDo you want to cancel TICKET..");
		System.out.println("Enter Yes or No");
		String choice = s.next();
		
		if(choice.equalsIgnoreCase("yes"))
			return true;
		else 
			return false;		
	}

	private void getBookedDetails() {
		
		bookingModel.getBookedDetails();
	}

	private void getPNRStatus() {
		
		Scanner s = new Scanner(System.in);
		System.out.print("Enter PNR Number : ");
		int pnrNumber = s.nextInt();
		bookingModel.getPNRStatus(pnrNumber);
	}

	private void bookTicket() {
		
		Scanner s = new Scanner(System.in);
		System.out.print("From Station : ");
		String fromStation = s.next();
		System.out.print("To Station : ");
		String toStation = s.next();
		showAvaiableTrains(fromStation, toStation);
	}

	private void showAvaiableTrains(String fromStation, String toStation) {
		
		if(bookingModel.getAvalableTrains(fromStation, toStation)) {
			getPassengerInfo();
		}
	}

	private void getPassengerInfo() {
		
		List<Passenger> passengers = new ArrayList<Passenger>();
		
		Scanner s = new Scanner(System.in);
		System.out.print("Enter Train Number : ");
		int trainNo = s.nextInt();
		
		int trainAmount = bookingModel.getTrainFare(trainNo);
		
		System.out.print("\nEnter the Number of Passenger : ");
		int numberOfPassenger = s.nextInt();
		System.out.println("\nEnter the Passeger Details");		

		for (int i = 0; i < numberOfPassenger; i++) {
			Passenger passenger = new Passenger();
			System.out.print("Enter Passenger Name : ");
			passenger.setName(s.next());
			System.out.print("Enter Gender : ");
			passenger.setGender(s.next());
//			System.out.print("Enter ID : ");
//			passenger.setID(s.nextInt());
			System.out.print("Enter Age : ");
			passenger.setAge(s.nextInt());
			passengers.add(passenger);
		}
		
		int totalAmount = trainAmount*numberOfPassenger;
		System.out.println("Total Fate : "+ totalAmount);
		
		System.out.println("\n\nPay:");
		System.out.println("1 -> Pay\n2 -> Cancel\n 3 ->Reschedule\n\n");
		System.out.print("Enter Choice : ");
		if(s.next().equals("1")) {
			
			if(bookingModel.addPassenger(passengers, trainNo, trainAmount)) {
				
			}			
		}		
	}

	public void showAvaiableTrains(List<Train> trains) {		
		System.out.println("\n\nTrain Name \t Train Number \t Ticket Amount");
		System.out.println("---------------------------------");
		for(Train train:trains) {
			System.out.println(train.getName()+"\t\t"+train.getNumber()+"\t\t"+train.getFare());
		}	
	}

	public void showMessage(String msg) {
		System.out.println(msg);
	}

	public void showTicketStatus(List<TicketDetail> ticketStatus) {
		
		System.out.println("Ticket Status");
		System.out.println("-------------");
		
		System.out.println("PNR Number\tTicketId\tStatus");
		for(TicketDetail ticketDetail: ticketStatus) {
			System.out.println(ticketDetail.getPNR()+"\t\t"+ ticketDetail.getTicketId()+"\t"+ ticketDetail.getTicketStatus());
		}
	}
}
