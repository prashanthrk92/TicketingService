package main.java;

public class Seat {
    private int rowId;
    private int columnId;
    private SeatStatus status;

    Seat(int i,int j, SeatStatus status){
        this.rowId = i;
        this.columnId = j;
        this.status = status;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

}
