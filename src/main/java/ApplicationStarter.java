package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;


public class ApplicationStarter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Venue v = new Venue(10,10);
        TicketServiceImpl ticket = new TicketServiceImpl(v);
        TicketReserveHelper h = new TicketReserveHelper();
        boolean flag = true;
        System.out.println("**********  Welcome to the Ticketing Service Application   ********"+"\n");
        System.out.println("The venue has "+v.getRows()+ " rows and "+v.getColumns()+" seats per row with a total capacity of"+(v.getRows()*v.getColumns())+" people");
        while(flag) {
            System.out.println("Enter the numeral option as described below for ticket booking:");
            System.out.println("Select 1 for finding the number of seats available");
            System.out.println("Select 2 for finding and holding the best available seats");
            System.out.println("Select 3 for reserving the seats held");
            System.out.println("Select 4 for exiting from the application");

            switch (sc.nextInt()) {
                case 1:
                    int seatsAvailable = ticket.numSeatsAvailable();
                    System.out.println("The number of seats available:" + seatsAvailable+"\n");
                    break;
                case 2:
                    try {
                        System.out.println("Enter the number of seats to reserve:");
                        String noOfSeats = sc.next();
                        if(!h.isValidNo(noOfSeats)){
                            while(!h.isValidNo(noOfSeats)){
                                System.out.println("Enter a valid seat number:");
                                noOfSeats = sc.next();

                            }
                        }
                        int numberOfSeats = Integer.parseInt(noOfSeats);
                        System.out.println("Enter the customer email to begin ticket processing :");
                        String email = sc.next();
                        while (!h.isValidEmail(email)) {
                            System.out.println("Invalid email.Enter a valid mail of the format(xx@yy.com)");
                            email = sc.next();
                        }
                        int seats = ticket.numSeatsAvailable();
                        if(numberOfSeats >seats){
                            System.out.println("Invalid number of seats. Try with lesser number of seats");
                        }
                        else {
                            SeatHold heldSeat = ticket.findAndHoldSeats(numberOfSeats, email);
                            h.printSeatHold(heldSeat);
                        }
                        break;
                    }
                    catch(InputMismatchException e){
                        System.out.println("Enter a valid input.");
                    }
                case 3:
                    System.out.println("Enter the Seat Hold id:");
                    String id = sc.next();
                    if(!h.isValidNo(id)){
                        while(!h.isValidNo(id)){
                            System.out.println("Enter a valid seat hold id:");
                            id = sc.next();
                        }
                    }
                    int seatHoldId = Integer.parseInt(id);
                    System.out.println("Enter the customer mail associated with the id.");
                    String customerMail = sc.next();
                    while (!h.isValidEmail(customerMail)) {
                        System.out.println("Invalid email.Enter a valid mail of the format(xx@yy.com)");
                        customerMail = sc.next();
                    }
                    System.out.println("=====The details of the ticket confirmation=====\n"+ticket.getReservation(seatHoldId,customerMail));
                    break;
                case 4:
                    flag = false;
                    break;
            }
        }
    }
}
