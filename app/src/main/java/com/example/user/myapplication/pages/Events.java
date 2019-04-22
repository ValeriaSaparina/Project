package com.example.user.myapplication.pages;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.CategoryResponse;
import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.API.EventsResponse;
import com.example.user.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Events extends AppCompatActivity {

    static final String BASE_URL = "https://api.timepad.ru/";

    ImageView imgEvent1;
    ImageView imgEvent2;

    EditText event1;
    EditText event2;

    EditText eventInfo1;
    EditText eventInfo2;

    Button add_event1;
    Button add_event2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        event1 = findViewById(R.id.event1);
        event2 = findViewById(R.id.event2);

        eventInfo1 = findViewById(R.id.eventInfo1);
        eventInfo2 = findViewById(R.id.eventInfo2);

        add_event1 = findViewById(R.id.add_event1);
        add_event2 = findViewById(R.id.add_event2);

        imgEvent1 = findViewById(R.id.img_event1);
        imgEvent2 = findViewById(R.id.img_event2);

        getReport();

    }

    void getReport() {
        Log.d("API", "start");
        Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        List<String> cityList = new ArrayList<String>();
        cityList.add("Казань");
        Call<EventsResponse> call = api.eventList(2, 10, cityList);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                try {
                    EventsResponse eventsResponse = response.body();

                    Log.d("API", "raw response: " + response.raw().toString());
                    if (eventsResponse == null) Log.d("API", "response is null");
                    else {

                        //Picasso.get().load(eventsResponse.getValues().get(0).getPoster_image().getDefault_url()).resize(800, 100).into(imgEvent1);

                        List<EventResponse> myList = eventsResponse.getValues();

                        Picasso.get().load(myList.get(0).getPoster_image().getDefault_url()).into(imgEvent1);
                        Picasso.get().load(myList.get(1).getPoster_image().getDefault_url()).into(imgEvent2);

                        event1.setText(myList.get(0).getName());
                        event2.setText(myList.get(1).getName());

                        eventInfo2.setText((CharSequence) myList.get(1).getCategories().get(0));



                        for(EventResponse er: myList) {
                            Log.d("API", "id = " + er.getId() + " name = " + er.getName() + " url = " + er.getUrl() + " img = " + er.getPoster_image().getDefault_url());
                        }
                        Log.d("API", "event list is " + eventsResponse.getTotal() + " length");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                Log.d("API", "failed");
            }
        });
    }
}
