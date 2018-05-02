Ticketing Service

A Java application which is used to reserve tickets.

### Install and Test the Application

Download the application, and go to the directory path(where POM.xml is present):
1. Give the command "mvn clean install" or "mvn clean install -DskipTests"(if needed to skip the test cases).
2. After the build is successful, go to the target directory, and give "java -jar Walmart-TicketingService-1.0-SNAPSHOT.jar" and 
   the application will start.
   
### Classes implemented

1. TicketServiceImpl - the java service that implements all the interface methods for finding the seats available, holding the seats
   for specific time, and reserving those tickets.
2. Venue - contains a two dimensional array of seats depicting a cine theatre, with specified number of rows and columns.
3. Seat - object containing the location of the seat(row and column number), and the status of the seat
4. SeatHold - contains the customer details pertaining to the seats held by that customer
5. SeatStatus - an enum class describing the status of the seat
6. TicketReserveHelper - a helper class containing the methods for validating customer details and user input




