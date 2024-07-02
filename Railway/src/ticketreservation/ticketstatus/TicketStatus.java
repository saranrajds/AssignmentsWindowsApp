package ticketreservation.ticketstatus;

public enum TicketStatus {

	CONFIRM(1),
	WAITING(2),
	RAC(3);
	
	private int ticketStatus;
	
    TicketStatus(int ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
	
	public int getTicketStatus() {
		return ticketStatus;
	}
}
