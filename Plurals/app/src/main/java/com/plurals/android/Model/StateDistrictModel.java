package com.plurals.android.Model;

import java.util.ArrayList;

public class StateDistrictModel {
    private ArrayList<State> states;

    public ArrayList<State> getStates() { return states; }
    public void setStates(ArrayList<State> value) { this.states = value; }
    public class State {
        private String state;
        private ArrayList<String> districts;

        public String getState() {
            return state;
        }

        public void setState(String value) {
            this.state = value;
        }

        public void setDistricts(ArrayList<String> value) {
            this.districts = value;
        }

        public ArrayList<String> getDistricts() {
            return districts;
        }
    }

}