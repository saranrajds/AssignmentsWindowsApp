package ticketreservation.model;

import java.util.List;

public class TicketBooking {

	private int trainId;
	private int PNRNO;
	private int ticketAmount;
	private boolean isCanceled;
	
	public boolean isCanceled() {
		return isCanceled;
	}
	
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	
	public void setTicketAmount(int ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	
	public int getTicketAmount() {
		return ticketAmount;
	}
	
	public int getTrain() {
		return trainId;
	}

	public void setTrain(int trainId) {
		this.trainId = trainId;
	}

	public int getPNRNO() {
		return PNRNO;
	}

	public void setPNRNO(int pNRNO) {
		PNRNO = pNRNO;
	}
}
