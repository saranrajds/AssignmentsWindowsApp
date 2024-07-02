package ticketreservation.booking;

import java.util.List;

import ticketreservation.dbcontext.DBContext;
import ticketreservation.model.Passenger;
import ticketreservation.model.TicketDetail;
import ticketreservation.model.Train;
import ticketreservation.ticketstatus.TicketStatus;

public class BookingModel {

	private BookingView bookingView;
	
	public BookingModel(BookingView bookingView) {
		this.bookingView = bookingView;
	}

	public boolean getAvalableTrains(String fromStation, String toStation) {

		List<Train> trains = DBContext.getInstance().getAvalableTrains(fromStation, toStation);
		if(trains.size() > 0) {
			bookingView.showAvaiableTrains(trains);
			return true;
		}
		else {
			bookingView.showMessage("No Train Avaiable");
			return false;
		}
	}

	public int getTrainFare(int trainNo) {
		
		return DBContext.getInstance().getTrainFare(trainNo);
	}

	public boolean addPassenger(List<Passenger> passengers, int trainNo, int totalAmount) {

		if(DBContext.getInstance().addPassenger(passengers, trainNo, totalAmount)) {
//			DBContext.getInstance().getPassegerWithTrainInfo(passengers, trainNo);
			bookingView.showMessage("Ticket Booked Successfully...");
			return true;
		}
		else {
			bookingView.showMessage("Ticket not booked.. please try again...");
			return false;
		}
		
	}

	public void getPNRStatus(int pnrNumber) {
		
		List<TicketDetail> ticketStatus = DBContext.getInstance().getPNRStatus(pnrNumber);

		if(ticketStatus.size() > 0) {
			bookingView.showTicketStatus(ticketStatus);
//			bookingView.showMessage("Ticket Statuts "+ ticketStatus);
		}
		else {
			bookingView.showMessage("The PNR ("+ pnrNumber +") Not Found ");
		}
	}

	public void getBookedDetails() {
		
	}

	public void onCancelTicket(int pnrNumber) {
		
		int canceled = DBContext.getInstance().onCancelTicket(pnrNumber);
		
		if(canceled > 0) {
			bookingView.showMessage("Ticket Canceled Successfully..\n");
			bookingView.showMessage("Your Re-Fund "+ canceled +" will be processed soon...\n");
		}
		else {
			bookingView.showMessage("The PNR ("+ pnrNumber +") Not Found ");
		}
	}

}
