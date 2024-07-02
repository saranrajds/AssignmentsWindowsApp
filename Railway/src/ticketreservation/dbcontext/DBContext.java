package ticketreservation.dbcontext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticketreservation.model.Passenger;
import ticketreservation.model.TicketDetail;
import ticketreservation.model.TicketBooking;
import ticketreservation.model.Train;
import ticketreservation.ticketstatus.TicketStatus;

public class DBContext {

	public final String TRAIN_FILE_NAME = "train", TICKET_BOOKING_FILE_NAME = "ticketBooking",
			PASSENGER_FILE_NAME = "passenger", TICKET_DETAIL_FILE_NAME = "ticketDetail";

	private final int CONFIRM_TICKET = 3, RAC_TICKET = 1, WAITING_TICKET = 1;

	ArrayList<Train> trainList = new ArrayList<Train>();
	ArrayList<TicketDetail> ticketDetails = new ArrayList<TicketDetail>();
	ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	ArrayList<TicketBooking> ticketBookings = new ArrayList<TicketBooking>();

	private static DBContext dbContext;

	public static DBContext getInstance() {

		if (dbContext == null)
			dbContext = new DBContext();

		return dbContext;
	}

	public boolean addTrain(Train train) {

		trainList.add(train);
		System.out.println(train);
		uploadData(TRAIN_FILE_NAME, trainList);
		return true;
	}

	public boolean isTrainAvaiable(int trarinId) {

		for (Train train_ : trainList) {

			if (train_.getNumber() == trarinId) {
				return true;
			}
		}

		return false;
	}

	public boolean addTrainSchedule(Train train) {

		for (Train train_ : trainList) {

			if (train_.getNumber() == train.getNumber()) {
//				train_ = train;
				train_.setFromStation(train.getFromStation());
				train_.setDepartureTime(train.getDepartureTime());
				train_.setToStatison(train.getToStatison());
				train_.setArrivalTime(train.getArrivalTime());
				train_.setDepartureTime(train.getDepartureTime());
				train_.setFare(train.getFare());
				train_.setRoutes(train.getRoutes());
				uploadData(TRAIN_FILE_NAME, trainList);
				return true;
			}
		}
		uploadData(TRAIN_FILE_NAME, trainList);
		return false;
	}

	public ArrayList<Train> getTrainFullInfo() {

		return trainList;
	}

	public List<Train> getAvalableTrains(String fromStation, String toStation) {

		List<Train> trains = new ArrayList<Train>();
		boolean isFromStation = false;
		for (Train train_ : trainList) {

			boolean isFromStationFromRoute = train_.getRoutes().stream().anyMatch(x -> x.equalsIgnoreCase(fromStation));
			if (isFromStation || train_.getFromStation().equalsIgnoreCase(fromStation) || isFromStationFromRoute) {
				isFromStation = true;

				for (String route : train_.getRoutes()) {

					if (train_.getToStatison().equals(toStation) || route.equalsIgnoreCase(toStation)) {
						trains.add(train_);
						break;
					}
				}

			}
		}

		return trains;
	}

	private <T> void uploadData(String pFilename, ArrayList<T> arrayList) {

		String filename = pFilename + ".json";
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(new File(filename), arrayList);
//			System.out.println("File Created...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void retriveDataFromFile(String pFileName) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			String filename = pFileName + ".json";
			File file = new File(filename);

			if (file.exists() && file.length() > 0 && pFileName.equals(TICKET_DETAIL_FILE_NAME)) {

				ArrayList<TicketDetail> tempTickets = objectMapper.readValue(new File(filename),
						new TypeReference<ArrayList<TicketDetail>>() {
						});

				ticketDetails = tempTickets;
			} else if (file.exists() && file.length() > 0 && pFileName.equals(TRAIN_FILE_NAME)) {

				ArrayList<Train> tempTrainList = objectMapper.readValue(new File(filename),
						new TypeReference<ArrayList<Train>>() {
						});

				trainList = tempTrainList;
			} else if (file.exists() && file.length() > 0 && pFileName.equals(PASSENGER_FILE_NAME)) {

				ArrayList<Passenger> tempPassengers = objectMapper.readValue(new File(filename),
						new TypeReference<ArrayList<Passenger>>() {
						});

				passengers = tempPassengers;
			} else if (file.exists() && file.length() > 0 && pFileName.equals(TICKET_BOOKING_FILE_NAME)) {

				ArrayList<TicketBooking> tempTicketDetails = objectMapper.readValue(new File(filename),
						new TypeReference<ArrayList<TicketBooking>>() {
						});

				ticketBookings = tempTicketDetails;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTrainFare(int trainNo) {

		for (Train train : trainList) {

			if (train.getNumber() == trainNo) {
				return train.getFare();
			}
		}
		return 0;
	}

	public boolean addPassenger(List<Passenger> pPassengers, int trainNo, int totalAmount) {

		TicketBooking ticketBooking = new TicketBooking();
		ticketBooking.setTrain(trainNo);
		int pnrNumber = getPNRNumber();
		ticketBooking.setPNRNO(pnrNumber);
		ticketBooking.setTicketAmount(totalAmount * pPassengers.size());
//		ticketBooking.setCanceled(true);
		
//		List<Integer> ticketIds = new ArrayList<Integer>();

		List<Passenger> tempPassengers = new ArrayList<Passenger>();
		List<TicketDetail> tempicketDetails = new ArrayList<TicketDetail>();

		int confirmedTicket = getTicketCountBasedOnStatus(trainNo, TicketStatus.CONFIRM);
		int watingTicket = getTicketCountBasedOnStatus(trainNo, TicketStatus.WAITING);
		int racTicket = getTicketCountBasedOnStatus(trainNo, TicketStatus.RAC);
		
		int passengerCount = passengers.size() + 1;
		for (Passenger passenger : pPassengers) {

			TicketDetail ticketDetail = new TicketDetail();
			int ticketId = getTicketId();
//			ticketIds.add(ticketId);

			ticketDetail.setPNR(pnrNumber);
			ticketDetail.setPassengerId(passengers.size() + 1);
			ticketDetail.setTicketId(ticketId);
			ticketDetail.setTicketStatus(TicketStatus.WAITING.toString());

//			System.out.println(passenger.getName());
			passenger.setID(passengerCount++);

			if (confirmedTicket < CONFIRM_TICKET) {
				ticketDetail.setTicketStatus(TicketStatus.CONFIRM.toString());
				confirmedTicket++;
			} else if (watingTicket < WAITING_TICKET) {
				ticketDetail.setTicketStatus(TicketStatus.WAITING.toString());
				watingTicket++;
			} else if (racTicket < RAC_TICKET) {
				ticketDetail.setTicketStatus(TicketStatus.RAC.toString());
				racTicket++;
			} else {
				return false;
			}

			tempicketDetails.add(ticketDetail);
			tempPassengers.add(passenger);
		}

		passengers.addAll(tempPassengers);
		ticketDetails.addAll(tempicketDetails);
		ticketBookings.add(ticketBooking);

		uploadData(PASSENGER_FILE_NAME, passengers);
		uploadData(TICKET_DETAIL_FILE_NAME, ticketDetails);
		uploadData(TICKET_BOOKING_FILE_NAME, ticketBookings);
		return true;
	}

	private int getTicketCountBasedOnStatus(int trainNo, TicketStatus ticketStatus) {

		List<TicketBooking> tempTicketBookings = ticketBookings.stream().filter(x -> x.getTrain() == trainNo).toList();

//		System.out.println(trainNo + " " + ticketStatus);
		if (tempTicketBookings.size() > 0) {

			System.out.println(ticketBookings.get(0).getPNRNO());

			return ticketDetails.stream().filter(x -> x.getPNR() == ticketBookings.get(0).getPNRNO()
					&& x.getTicketStatus().equals(ticketStatus.toString())).toList().size();
		}

		return 0;
	}

	private int getTicketId() {

		int min = 1000, max = 9999;
		Random random = new Random();
		int id = random.nextInt((max - min) + 1) + min;

		while (isIdTaken(ticketDetails, id)) {
			id = random.nextInt((max - min) + 1) + min;
		}

		return id;
	}

	private int getPNRNumber() {

		int min = 100000, max = 999999;
		Random random = new Random();
		int id = random.nextInt((max - min) + 1) + min;

		while (isTekenPNR(ticketBookings, id)) {
			id = random.nextInt((max - min) + 1) + min;
		}

		return id;
	}

	private boolean isTekenPNR(ArrayList<TicketBooking> ticketBookings2, int id) {

		return ticketBookings2.stream().anyMatch(x -> x.getPNRNO() == id);
	}

	private static boolean isIdTaken(List<TicketDetail> tickets, int id) {

		return tickets.stream().anyMatch(x -> x.getTicketId() == id);
	}

	public void getPassegerWithTrainInfo(List<Passenger> passengers, int trainNo) {

		TicketBooking ticketDetails = new TicketBooking();
		Train train_ = new Train();

		List<Train> train = trainList.stream().filter(x -> x.getNumber() == trainNo).toList();

		for (Train TempTrain : trainList) {

			if (TempTrain.getNumber() == trainNo) {
//				ticketDetails.setTrain(TempTrain);
				break;
			}
		}

		for (Passenger passenger : passengers) {

		}
	}

	public List<TicketDetail> getPNRStatus(int pnrNumber) {

		List<TicketDetail> tempTicketBookings = ticketDetails.stream().filter(x -> x.getPNR() == pnrNumber).toList();
		return tempTicketBookings;
	}

	public int onCancelTicket(int pnrNumber) {
		
		for(TicketBooking ticketBooking: ticketBookings) {
			
			if(ticketBooking.getPNRNO() == pnrNumber) {
				ticketBooking.setCanceled(true);
				return ticketBooking.getTicketAmount();
			}			
		}
		return 0;
	}
}
