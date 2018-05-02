package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TicketReserveHelper {
    /**
     *
     * @param email the mail address
     * @return the boolean value
     */
    public boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     *
     * @param number the value in number
     * @return the boolean value
     */
    public boolean isValidNo(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return false;
        }
        if(number == null){
            return false;
        }
        return true;
    }

    /**
     *
     * @param heldSeat the list of held seats
     */
    public void printSeatHold(SeatHold heldSeat){
        if (heldSeat.getSeats() == null) {
            System.out.println("Invalid number of tickets.");
        } else {
            List<String> seats = new ArrayList<>();
            System.out.println("======SeatHold information====");
            System.out.println("Email:" + heldSeat.getCustomerMail());
            System.out.println("SeatHold id:" + heldSeat.getSeatHoldId());
            for(Seat s:heldSeat.getSeats()){
                seats.add("" + s.getRowId() + s.getColumnId());
            }
            System.out.println("Seats held currently are:"+ Arrays.asList(seats).toString());
            System.out.println("The expiry time for seats held is 90 seconds. Kindly reserve the tickets within this time."+"\n");

        }
    }

    /**
     *
     * @param seats the list of seats
     * @param status the status of the Seat object
     */
    public void changeSeatStatus(List<Seat> seats, SeatStatus status){
        for(Seat seat:seats){
            seat.setStatus(status);
        }
    }
}
