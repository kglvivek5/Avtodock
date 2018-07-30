package com.vivekapps.DTO;

public class BookingHistoryDTO {

    private BookingHistoryDataDTO[] data;

    private String success;

    public BookingHistoryDataDTO[] getData ()
    {
        return data;
    }

    public void setData (BookingHistoryDataDTO[] data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "BookingHistoryDTO [data = "+data+", success = "+success+"]";
    }

}
