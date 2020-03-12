package com.plurals.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.plurals.android.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VolunteerFormActivity extends AppCompatActivity {
    TextView textView;
    ArrayList<String> parliamentList, assemblyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_form);
        findViews();
        fetchPCJson();
    }

    Spinner sp_parliament, sp_assembly;
    ArrayAdapter parliamentAdapter,assemblyAdapter;
    private void findViews() {
        sp_assembly = findViewById(R.id.sp_assembly);
        sp_parliament = findViewById(R.id.sp_parliament);
        sp_parliament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0 ){
                    assemblyList=new ArrayList();
                    assemblyList.add("Select An Assembly");
                    Collections.sort(pcMap.get(parliamentList.get(position)), String.CASE_INSENSITIVE_ORDER);

                    assemblyList.addAll(pcMap.get(parliamentList.get(position)));
                    assemblyAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, assemblyList);
                    assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_assembly.setAdapter(assemblyAdapter);

                }
                else {
                    assemblyList=new ArrayList();
                    assemblyList.add("Select An Assembly");
                    assemblyAdapter = new ArrayAdapter(VolunteerFormActivity.this, android.R.layout.simple_spinner_item, assemblyList);
                    assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_assembly.setAdapter(assemblyAdapter);

                }

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
            assemblyList=new ArrayList();
            assemblyList.add("Select An Assembly");
            assemblyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, assemblyList);
            assemblyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_assembly.setAdapter(assemblyAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
