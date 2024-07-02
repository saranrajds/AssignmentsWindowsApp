package ticketreservation;

import ticketreservation.schedule.ScheduleView;

public class Reservation {

	private static Reservation reservation;
	
	private static Reservation getInstance() {
		
		if(reservation == null)
			reservation = new Reservation();
		
		return reservation;
	}
	
	private void init() {
		new ScheduleView().initSchedule();
	}
	
	public static void main(String[] args) {

		Reservation.reservation.getInstance().init();
	}

}
