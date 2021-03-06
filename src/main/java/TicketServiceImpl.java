package main.java;
import java.time.Instant;
import java.util.*;


public class TicketServiceImpl implements TicketService{
    private Venue venue;
    private int seatCount;
    private int id;
    private Map<Integer, SeatHold> seatIdHoldMap;
    private Map<String,Integer> reverseseatIdHoldMap;
    private SeatHold seatHold;
    private List<SeatHold> heldSeatList;
    private long seconds = 90L;
    private TicketReserveHelper h;
    private List<Seat> initialSeatsHeld;

    public TicketServiceImpl(Venue v){
        this.venue = v;
        h = new TicketReserveHelper();
        reverseseatIdHoldMap = new HashMap<>();
    }

    /**
     *
     * @return the total number of seats available at that particular time.
     */
    @Override
    public int numSeatsAvailable() {
        seatCount = 0;
        Seat[][] seats = venue.getSeats();
        int rows = venue.getRows();
        int columns = venue.getColumns();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                if(seats[i][j].getStatus() == SeatStatus.AVAILABLE){
                    seatCount = seatCount+1;
                }
            }
        }
        return seatCount;
    }

    /**
     *
     * @param numSeats the number of seats to be booked
     * @param customerEmail the customer mail address
     * @return the seat hold object containing the information of the seats held.
     */
    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        int seatsAvailable = numSeatsAvailable();
        if(numSeats > seatsAvailable){
            System.out.println("Invalid number of seats");
            return null;
        }
        seatIdHoldMap = new HashMap<Integer, SeatHold>();
        heldSeatList = new ArrayList<SeatHold>();

        List<Seat> heldSeats = findSeats(numSeats);
        seatHold = getHoldSeats(heldSeats, customerEmail);
        heldSeatList.add(seatHold);
        seatIdHoldMap.put(seatHold.getSeatHoldId(),seatHold);
        reverseseatIdHoldMap.put(seatHold.getCustomerMail(),seatHold.getSeatHoldId());
        return seatHold;
    }

    /**
     *
     * @param numSeats the number of seats to be booked
     * @return the list of seats to be held for reservation.
     */
    public List<Seat> findSeats(int numSeats){
        List<Seat> seatsHeld = new ArrayList<Seat>();

        for(int i=0;i<venue.getRows();i++){
            for(int j=0;j<venue.getColumns();j++){
                Seat[][] seats = venue.getSeats();
                if(seats[i][j].getStatus() == SeatStatus.AVAILABLE){
                    seats[i][j].setStatus(SeatStatus.HOLD);
                    seatsHeld.add(seats[i][j]);
                    numSeats--;

                    if(numSeats == 0){
                        return seatsHeld;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param heldSeats the list of seats held for reservation
     * @param customerMail the customer mail address
     * @return the seathold object
     */
    public SeatHold getHoldSeats(List<Seat> heldSeats, String customerMail){
        Random rnd = new Random();
        id = rnd.nextInt(10000);
        SeatHold seatHold = new SeatHold();
        if(reverseseatIdHoldMap.containsKey(customerMail)){
            int seatHoldId = reverseseatIdHoldMap.get(customerMail);
            seatHold.setSeatHoldId(seatHoldId);
        }
        else {
            seatHold.setSeatHoldId(id);
        }
        seatHold.setSeats(heldSeats);
        seatHold.setExpiryTime(Instant.now().getEpochSecond());
        seatHold.setCustomerMail(customerMail);
        return seatHold;
    }

    /**
     *
     * @param seatHoldId the id of the seatHold object
     * @param customerEmail the customer mail address
     * @return the confirmed reservation details
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        String confirmedReservation = getReservation(seatHoldId,customerEmail);
        seatIdHoldMap.remove(seatHoldId);
        return confirmedReservation;
    }

    /**
     *
     * @param seatHoldId the id of the seatHold object
     * @param customerEmail the customer mail address
     * @return the confirmed reservation details
     */
    public String getReservation(int seatHoldId, String customerEmail){
        removeExpiredSeats(Instant.now().getEpochSecond(),heldSeatList);
        List<String> seats = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        if(seatIdHoldMap.containsKey(seatHoldId)){
            if(seatIdHoldMap.get(seatHoldId).getCustomerMail().equals(customerEmail)) {
                SeatHold seatHold = seatIdHoldMap.get(seatHoldId);
                for (Seat s : seatHold.getSeats()) {
                    s.setStatus(SeatStatus.RESERVED);
                    seats.add("" + s.getRowId() + s.getColumnId());

                }
                sb.append("Customer id:" + customerEmail + "\nSeats reserved:" + Arrays.asList(seats).toString()+"\n");
                seatIdHoldMap.remove(seatHoldId);
                return sb.toString();
            }
            else{
                return "Not the correct customer email for the given SeatHold id"+"\n";
            }
        }
        else{
            return "The seatHoldId had either expired or invalid.Kindly start booking again"+"\n";
        }
    }

    /**
     *
     * @param currentTime the current Epoch time
     * @param heldSeatList the list of held seats
     */
    public void removeExpiredSeats(long currentTime,List<SeatHold> heldSeatList){
        List<SeatHold> tempHeldSeatHold = new ArrayList<>();
        if(heldSeatList == null) return;

        for(SeatHold eachSeatHold: heldSeatList){
            if(currentTime - eachSeatHold.getExpiryTime() > seconds){
                h.changeSeatStatus(eachSeatHold.getSeats(), SeatStatus.AVAILABLE);
                seatIdHoldMap.remove(eachSeatHold.getSeatHoldId());
                tempHeldSeatHold.add(eachSeatHold);
            }
        }
        heldSeatList.removeAll(tempHeldSeatHold);
    }
}

