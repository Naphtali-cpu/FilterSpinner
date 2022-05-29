package com.example.filterspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.filterspinner.entities.District;
import com.example.filterspinner.entities.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerState, spinnerDistrict, spinnerSubDist;
    private ArrayList<String> getStateName = new ArrayList<String>();
    private ArrayList<String> getdistrictName = new ArrayList<String>();
    private ArrayList<String> getsubdistrictName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        spinnerSubDist = (Spinner) findViewById(R.id.spinnerSubDist);
        getState();
    }

    private void getState() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiPassId.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        ApiPassId apiPassId = retrofit.create(ApiPassId.class);
        Call<String> call = apiPassId.getState();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("NAPH", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("NAPH", response.body().toString());
                        try {
                            String getResponse = response.body().toString();
                            List<State> getStateData = new ArrayList<State>();
                            JSONArray jsonArray = new JSONArray(getResponse);
                            Log.i("TAGJson", jsonArray.toString());
//                            getStateData.add(new State(0, "SELECT"));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                State states = new State();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                states.setId(jsonObject.getInt("id"));
                                states.setName(jsonObject.getString("name"));
                                getStateData.add(states);
                            }
                            for (int i = 0; i < getStateData.size(); i++) {
                                getStateName.add(getStateData.get(i).getName().toString());
                            }

                            ArrayAdapter<String> spinStateAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getStateName);
                            spinStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerState.setAdapter(spinStateAdapter);
                            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    int getstatesID = getStateData.get(i).getId();
                                    get_District(getstatesID);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void get_District(int getstatesID) {

        getdistrictName.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiPassId.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        ApiPassId apiPassId = retrofit.create(ApiPassId.class);
        Call<String> call = apiPassId.getDistrict(getstatesID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i("NewNaph", response.body());
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            String getResponse = response.body().toString();
                            Log.i("NewNaph", getResponse.toString());
                            List<District> getdistrictData = new ArrayList<District>();
                            JSONArray jsonArray = new JSONArray(getResponse);

                            getdistrictData.add(new District(0,"---- SELECT ----"));
                            for(int i=0;i<jsonArray.length();i++){
                                District district = new District();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                district.setId(jsonObject.getInt("id"));
                                district.setName(jsonObject.getString("name"));
                                getdistrictData.add(district);
                            }

                            for (int i = 0; i < getdistrictData.size(); i++) {
                                getdistrictName.add(getdistrictData.get(i).getName().toString());
                            }

                            ArrayAdapter<String> spinDistrictAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, getdistrictName);
                            spinDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDistrict.setAdapter(spinDistrictAdapter);
                            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}