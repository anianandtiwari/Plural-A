package com.plurals.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.plurals.android.Model.StateDistrictModel;
import com.plurals.android.R;
import com.plurals.android.Utility.CommonUtils;
import com.plurals.android.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class VolunteerFormActivity extends AppCompatActivity {
    TextView textView;
    ArrayList<String> parliamentList, assemblyList, districtList;
    View snackView;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_form);
        snackView = findViewById(R.id.register_activity);
        back_button = findViewById(R.id.rv_back);
        findViews();
        fetchPCJson();
        fetchStateDistrictJson();
        // snackBar(snackView,"Welcome");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       // snackBar(snackView,"Welcome");
    }

    Spinner sp_parliament, sp_assembly, sp_state, sp_district;
    ArrayAdapter parliamentAdapter, assemblyAdapter, stateAdapter, districtAdapter;
    EditText rv_name, rv_name_last, rv_mob, rv_email, rv_address_1, rv_pincode;
    TextView rv_save;


    private void findViews() {
        sp_district = findViewById(R.id.sp_district);
        sp_state = findViewById(R.id.sp_state);
        rv_save = findViewById(R.id.rv_save);
        rv_pincode = findViewById(R.id.rv_pincode);
        rv_address_1 = findViewById(R.id.rv_address_1);
        rv_name = findViewById(R.id.rv_name);
        rv_name_last = findViewById(R.id.rv_name_last);
        rv_mob = findViewById(R.id.rv_mob);
        rv_email = findViewById(R.id.rv_email);
        sp_assembly = findViewById(R.id.sp_assembly);
        sp_parliament = findViewById(R.id.sp_parliament);
        rv_save.setEnabled(false);
        rv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.hideForcefullyKeyboard(VolunteerFormActivity.this);
                CommonUtils.showToastInDebug(VolunteerFormActivity.this, "saved");
               JSONObject dataJson= collectDate();

               //TODO create your msg string below like -
                try {
                    String msg=dataJson.getString("name")+"/ "+dataJson.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        setSpinnerListeners();
        setTextWatchers();
    }

    private JSONObject collectDate() {
        JSONObject data= new JSONObject();
        try {
            data.put("first_name",rv_name.getText().toString());
            data.put("last_name",rv_name_last.getText().toString());
            data.put("email",rv_email.getText().toString());
            data.put("mobile",rv_mob.getText().toString());
            data.put("mobile",rv_mob.getText().toString());
            data.put("address",rv_address_1.getText().toString());
            data.put("pincode",rv_pincode.getText().toString());
            data.put("state",selectedState);
            data.put("district",selectedDistrict);
            data.put("parliament",selectedParliament);
            data.put("assembly",selectedAssembly);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    String selectedState, selectedDistrict,selectedParliament,selectedAssembly;

    private void setSpinnerListeners() {
        sp_parliament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    isValideParliament = true;
                    selectedParliament=parliamentList.get(position);
                    assemblyList = new ArrayList();
                    assemblyList.add("Select An Assembly");
                    Collections.sort(pcMap.get(parliamentList.get(position)), String.CASE_INSENSITIVE_ORDER);

                    assemblyList.addAll(pcMap.get(parliamentList.get(position)));
                    assemblyAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, assemblyList);
                    assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_assembly.setAdapter(assemblyAdapter);

                } else {
                    selectedParliament="";
                    isValideParliament = false;
                    assemblyList = new ArrayList();
                    assemblyList.add("Select An Assembly");
                    assemblyAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, assemblyList);
                    assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_assembly.setAdapter(assemblyAdapter);

                }
                checkValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_assembly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedAssembly=assemblyList.get(position);
                    isValideAssembly = true;
                } else {
                    selectedAssembly="";
                    isValideAssembly = false;
                }
                checkValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedState = stateList.get(position);
                    isvalidState = true;
                    districtList = new ArrayList();
                    districtList.add("District");
                    districtList.addAll(stateDistrictModel.getStates().get(position - 1).getDistricts());
                    districtAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, districtList);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_district.setAdapter(districtAdapter);

                } else {
                    selectedState = "";
                    isvalidState = false;
                    districtList = new ArrayList();
                    districtList.add("District");
                    districtAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, districtList);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_district.setAdapter(districtAdapter);

                }
                checkValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedDistrict = districtList.get(position);
                    isValideDistrict = true;
                } else {
                    selectedDistrict = "";
                    isValideDistrict = false;
                }
                checkValidation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void fetchPCJson() {
        String json = null;
        try {
            InputStream is = getAssets().open("bihar_pc_ac_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject dataJson = new JSONObject(json);
            preparePCMap(dataJson);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    StateDistrictModel stateDistrictModel;

    private void fetchStateDistrictJson() {
        String json = null;
        try {
            InputStream is = getAssets().open("india_state_capitals.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            stateDistrictModel = new Gson().fromJson(json, StateDistrictModel.class);
            if (stateDistrictModel != null) {
                initStateSpinenr();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ArrayList<String> stateList;

    private void initStateSpinenr() {
        stateList = new ArrayList();
        stateList.add("State");

        for (int i = 0; i <= stateDistrictModel.getStates().size() - 1; i++) {
            stateList.add(stateDistrictModel.getStates().get(i).getState());
        }
        stateAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(stateAdapter);


    }


    Map<String, ArrayList<String>> pcMap = new TreeMap();

    private void preparePCMap(JSONObject dataJson) {
        pcMap.clear();
        try {
            JSONArray array = dataJson.getJSONArray("bihar");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (pcMap.containsKey(obj.getString("PC_Name"))) {
                    pcMap.get(obj.getString("PC_Name")).add(obj.getString("AC_Name"));
                } else {
                    ArrayList<String> list = new ArrayList();
                    list.add(obj.getString("AC_Name"));
                    pcMap.put(obj.getString("PC_Name"), list);
                }
            }
            parliamentList = new ArrayList();
            parliamentList.add("Select A Parliament");
            parliamentList.addAll(pcMap.keySet());
            parliamentAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, parliamentList);
            parliamentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_parliament.setAdapter(parliamentAdapter);

            //add thumbnail text for assembly spinner
            assemblyList = new ArrayList();
            assemblyList.add("Select An Assembly");
            assemblyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, assemblyList);
            assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_assembly.setAdapter(assemblyAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void snackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();

    }

    private void setTextWatchers() {
        rv_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isValidName = true;
                } else {
                    isValidName = false;
                }
                checkValidation();
            }
        });
        rv_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && Pattern.matches(Constants.EMAIL_ID_REJEX, s.toString())) {
                    isValidEmail = true;
                } else {
                    isValidEmail = false;
                }
                checkValidation();

            }
        });
        rv_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10 && Pattern.matches(Constants.MOBILE_NUM_REGEX, s.toString())) {
                    isValidNum = true;
                } else {
                    isValidNum = false;
                }
                checkValidation();
            }
        });
        rv_address_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isValidAddress1 = true;
                } else {
                    isValidAddress1 = false;
                }
                checkValidation();
            }
        });
        rv_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 4 && Pattern.matches(Constants.PINCODE_REJEX, s.toString())) {
                    isValidPincode = true;
                } else {
                    isValidPincode = false;
                }
                checkValidation();
            }
        });
    }

    private Boolean isValidName = false;
    private Boolean isValidNum = false;
    private Boolean isValidAddress1 = false;
    private Boolean isValidPincode = false;
    private Boolean isValidEmail = false;
    private Boolean isvalidState = false;
    private Boolean isValideDistrict = false;
    private Boolean isValideParliament = false;
    private Boolean isValideAssembly = false;

    private void checkValidation() {
        if (isValidName && isValidEmail && isValidNum && isValidAddress1 && isValidPincode && isvalidState && isValideDistrict && isValideParliament && isValideAssembly) {
            rv_save.setAlpha(1f);
            rv_save.setEnabled(true);
        } else {
            rv_save.setAlpha(0.5f);
            rv_save.setEnabled(false);
        }
    }

}
