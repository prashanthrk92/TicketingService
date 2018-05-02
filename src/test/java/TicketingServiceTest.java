package test.java;

import main.java.SeatHold;
import main.java.TicketServiceImpl;
import main.java.Venue;
import org.junit.*;
import static org.junit.Assert.*;

public class TicketingServiceTest {
    private TicketServiceImpl ticketService;
    private long wait = 5000;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        ticketService = new TicketServiceImpl(new Venue(1,1));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void numSeatsAvailable() throws InterruptedException{
        ticketService = new TicketServiceImpl(new Venue(10,10));
        int availableSeats = ticketService.numSeatsAvailable();
        assert(availableSeats == (10*10));
        ticketService.findAndHoldSeats(2, "prashanth@gmail.com");
        availableSeats = ticketService.numSeatsAvailable();
        assert(availableSeats == ((10*10)-2));
        Thread.sleep(wait);
        System.out.println("After waiting: " + ticketService.numSeatsAvailable());
        assert(ticketService.numSeatsAvailable() == (98));
        ticketService.findAndHoldSeats((10*10), "prashanth@gmail.com");
        System.out.println(ticketService.numSeatsAvailable());
        assertEquals("Number of seats do not match the expected value.", ticketService.numSeatsAvailable(), 98);
    }

    @Test
    public void findAndHoldSeats() throws InterruptedException{
        SeatHold seatHold = ticketService.findAndHoldSeats(1, "xx@yahoo.com");
        assertNotNull(seatHold);
        System.out.println(seatHold);
        assertEquals(1,seatHold.getSeats().size());
        seatHold = ticketService.findAndHoldSeats(1, "x@yahoo.com");
        assert(seatHold == null);
        seatHold = ticketService.findAndHoldSeats(2, "xy@yahoo.com");
        assert(seatHold == null);
        seatHold = ticketService.findAndHoldSeats(3,"pr@gmail.com");
        assert(seatHold == null);
    }


    @Test
    public void reserveSeats() throws InterruptedException{
        SeatHold s1 = ticketService.findAndHoldSeats(1, "xx@yahoo.com");
        String ticketConfirmation = ticketService.reserveSeats(s1.getSeatHoldId(), "xx@yahoo.com");
        assertNotNull(ticketConfirmation);
        assertTrue(ticketConfirmation.contains("Customer"));
        ticketConfirmation = ticketService.reserveSeats(0, "xx@yahoo.com");
        String s = "The seatHoldId had either expired or invalid.Kindly start booking again"+"\n";
        assertEquals(ticketConfirmation,s);
    }

}
