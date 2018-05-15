package com.vivekapps.DTO;

public class LoginDTO {

        private LoginDataDTO[] data;

        private String success;

        public LoginDataDTO[] getData ()
        {
            return data;
        }

        public void setData (LoginDataDTO[] data)
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
            return "LoginDTOPojo [data = "+data+", success = "+success+"]";
        }

}
