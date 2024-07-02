package ticketreservation.model;

import java.util.List;

import ticketreservation.ticketstatus.TicketStatus;

public class TicketDetail {

	private int passengerId;
	private String ticketStatus;
	private int PNR;
	private int ticketId;
	
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	
	public int getTicketId() {
		return ticketId;
	}
	
	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public int getPNR() {
		return PNR;
	}

	public void setPNR(int pNR) {
		PNR = pNR;
	}

}
