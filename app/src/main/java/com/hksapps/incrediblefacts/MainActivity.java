package com.hksapps.incrediblefacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private String result;
    private LinearLayout triviaRandom, triviaQuest, dateRandom, dateQuest, yearRandom, yearQuest, mathRandom, mathQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        triviaRandom = (LinearLayout) findViewById(R.id.trivia_random);
        triviaQuest = (LinearLayout) findViewById(R.id.trivia_quest);
        dateRandom = (LinearLayout) findViewById(R.id.date_random);
        dateQuest = (LinearLayout) findViewById(R.id.date_quest);
        yearRandom = (LinearLayout) findViewById(R.id.year_random);
        yearQuest = (LinearLayout) findViewById(R.id.year_quest);
        mathRandom = (LinearLayout) findViewById(R.id.math_random);
        mathQuest = (LinearLayout) findViewById(R.id.math_quest);


        triviaRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, GetFactFromApi("random", "trivia"), Toast.LENGTH_SHORT).show();

            }
        });


        dateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, GetFactFromApi("random", "date"), Toast.LENGTH_SHORT).show();

            }
        });


        yearRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, GetFactFromApi("random", "year"), Toast.LENGTH_SHORT).show();

            }
        });


        mathRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(MainActivity.this, GetFactFromApi("random", "math"), Toast.LENGTH_SHORT).show();

            }
        });


    }





    private String GetFactFromApi(String numberOrRandom, String type) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://numbersapi.com/" + numberOrRandom + "/" + type;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        result = response.toString();


                        Log.d("Response", result);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
                result = "Error Occured";
            }
        });
        queue.add(getRequest);

        return result;
    }


}
