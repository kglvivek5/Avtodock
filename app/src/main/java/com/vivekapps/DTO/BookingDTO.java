package com.vivekapps.DTO;

public class BookingDTO {

    private BookingDataDTO data;

    private String success;

    public BookingDataDTO getData ()
    {
        return data;
    }

    public void setData (BookingDataDTO data)
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
        return "BookingDTOPojo [data = "+data+", success = "+success+"]";
    }

}
