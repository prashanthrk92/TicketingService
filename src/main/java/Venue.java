package main.java;

public class Venue {
    private Seat[][] seats;
    private int rows;
    private int columns;
    private int AvailableCapacity;

    public Venue(int rows,int columns){
        this.seats = new Seat[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.AvailableCapacity = rows*columns;
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                seats[i][j] = new Seat(i,j,SeatStatus.AVAILABLE);
            }
        }
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public void setSeats(Seat[][] seats) {
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getAvailableCapacity() {
        return AvailableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        AvailableCapacity = availableCapacity;
    }
}
