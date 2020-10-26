package com.plurals.android.Model;

public class GenerateOtp {

    /**
     * success : 200
     * message : Otp Send Successfully : 601498
     * data : {"refid":43,"otpclientreference":0,"otpmobilenumber":"9555203470","otppurpose":"verify","otppincode":"601498","otpsmsgatewayreference":"sentsuccess","otpsmsgatewayresponse":"send"}
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
         * refid : 43
         * otpclientreference : 0
         * otpmobilenumber : 9555203470
         * otppurpose : verify
         * otppincode : 601498
         * otpsmsgatewayreference : sentsuccess
         * otpsmsgatewayresponse : send
         */

        private int refid;
        private int otpclientreference;
        private String otpmobilenumber;
        private String otppurpose;
        private String otppincode;
        private String otpsmsgatewayreference;
        private String otpsmsgatewayresponse;

        public int getRefid() {
            return refid;
        }

        public void setRefid(int refid) {
            this.refid = refid;
        }

        public int getOtpclientreference() {
            return otpclientreference;
        }

        public void setOtpclientreference(int otpclientreference) {
            this.otpclientreference = otpclientreference;
        }

        public String getOtpmobilenumber() {
            return otpmobilenumber;
        }

        public void setOtpmobilenumber(String otpmobilenumber) {
            this.otpmobilenumber = otpmobilenumber;
        }

        public String getOtppurpose() {
            return otppurpose;
        }

        public void setOtppurpose(String otppurpose) {
            this.otppurpose = otppurpose;
        }

        public String getOtppincode() {
            return otppincode;
        }

        public void setOtppincode(String otppincode) {
            this.otppincode = otppincode;
        }

        public String getOtpsmsgatewayreference() {
            return otpsmsgatewayreference;
        }

        public void setOtpsmsgatewayreference(String otpsmsgatewayreference) {
            this.otpsmsgatewayreference = otpsmsgatewayreference;
        }

        public String getOtpsmsgatewayresponse() {
            return otpsmsgatewayresponse;
        }

        public void setOtpsmsgatewayresponse(String otpsmsgatewayresponse) {
            this.otpsmsgatewayresponse = otpsmsgatewayresponse;
        }
    }
}
