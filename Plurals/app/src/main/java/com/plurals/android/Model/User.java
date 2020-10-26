package com.plurals.android.Model;

public class User {

    /**
     * success : 302
     * message : Otp Verified and User Already Available
     * data : {"refid":29,"mobileno":"7004784626","username":"Animesh","mailid":"ani6754@gmail.com","typeid":"1","address":"Laxminagar","image":"animesh anand"}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
         * refid : 29
         * mobileno : 7004784626
         * username : Animesh
         * mailid : ani6754@gmail.com
         * typeid : 1
         * address : Laxminagar
         * image : animesh anand
         */

        private int refid;
        private String mobileno;
        private String username;
        private String mailid;
        private String typeid;
        private String address;
        private String image;

        public int getRefid() {
            return refid;
        }

        public void setRefid(int refid) {
            this.refid = refid;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMailid() {
            return mailid;
        }

        public void setMailid(String mailid) {
            this.mailid = mailid;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
