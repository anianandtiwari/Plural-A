package com.plurals.android.Model;

public class OtpLogin {

    /**
     * success : true
     * message : Data return successfully.
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZ2FhdGhhb25haXIuY29tIiwiaWF0IjoxNTcwNzY5OTkwLCJuYmYiOjE1NzA3Njk5OTAsImV4cCI6MTU3MTYzMzk5MCwiZGF0YSI6eyJ1c2VyIjp7ImlkIjoiNTI1In19fQ.BYn5Oqw6a3oKHKVnrHQELl_o50FSIQDPd8ofYk1RaAs","user_email":"","user_nicename":"9555203470","user_display_name":"9555203470","profile_image_url":""}
     */

    private String success;
    private String message;
    private DataBean data;

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvZ2FhdGhhb25haXIuY29tIiwiaWF0IjoxNTcwNzY5OTkwLCJuYmYiOjE1NzA3Njk5OTAsImV4cCI6MTU3MTYzMzk5MCwiZGF0YSI6eyJ1c2VyIjp7ImlkIjoiNTI1In19fQ.BYn5Oqw6a3oKHKVnrHQELl_o50FSIQDPd8ofYk1RaAs
         * user_email :
         * user_nicename : 9555203470
         * user_display_name : 9555203470
         * profile_image_url :
         */

        private String token;
        private String user_email;
        private String user_nicename;
        private String user_display_name;
        private String profile_image_url;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public String getUser_display_name() {
            return user_display_name;
        }

        public void setUser_display_name(String user_display_name) {
            this.user_display_name = user_display_name;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "token='" + token + '\'' +
                    ", user_email='" + user_email + '\'' +
                    ", user_nicename='" + user_nicename + '\'' +
                    ", user_display_name='" + user_display_name + '\'' +
                    ", profile_image_url='" + profile_image_url + '\'' +
                    '}';
        }
    }
}
