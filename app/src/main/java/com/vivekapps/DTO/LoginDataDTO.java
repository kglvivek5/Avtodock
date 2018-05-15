package com.vivekapps.DTO;

public class LoginDataDTO {

    private String User_ID;

    private String Password;

    private String last_updated;

    private String User_name;

    public String getUser_ID ()
    {
        return User_ID;
    }

    public void setUser_ID (String User_ID)
    {
        this.User_ID = User_ID;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    public String getLast_updated ()
    {
        return last_updated;
    }

    public void setLast_updated (String last_updated)
    {
        this.last_updated = last_updated;
    }

    public String getUser_name ()
    {
        return User_name;
    }

    public void setUser_name (String User_name)
    {
        this.User_name = User_name;
    }

    @Override
    public String toString()
    {
        return "LoginDataDTPPojo [User_ID = "+User_ID+", Password = "+Password+", last_updated = "+last_updated+", User_name = "+User_name+"]";
    }

}
