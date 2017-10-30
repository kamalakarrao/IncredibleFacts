package com.hksapps.incrediblefacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout triviaRandom = (LinearLayout) findViewById(R.id.trivia_random);
        LinearLayout triviaQuest = (LinearLayout) findViewById(R.id.trivia_quest);
        LinearLayout dateRandom = (LinearLayout) findViewById(R.id.date_random);
        LinearLayout dateQuest = (LinearLayout) findViewById(R.id.date_quest);
        LinearLayout yearRandom = (LinearLayout) findViewById(R.id.year_random);
        LinearLayout yearQuest = (LinearLayout) findViewById(R.id.year_quest);
        LinearLayout mathRandom = (LinearLayout) findViewById(R.id.math_random);
        LinearLayout mathQuest = (LinearLayout) findViewById(R.id.math_quest);


        triviaRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {getRandomFactsFromApi("trivia");
            }
        });


        dateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomFactsFromApi("date");
            }
        });


        yearRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomFactsFromApi("year");
            }
        });


        mathRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomFactsFromApi("math");
            }
        });


        triviaQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForQuestNumber("trivia");
            }
        });


        dateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForQuestNumber("date");

            }
        });


        yearQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForQuestNumber("year");
            }
        });


        mathQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForQuestNumber("math");

            }
        });


    }

    private void getRandomFactsFromApi(final String type) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://numbersapi.com/random/" + type;


        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showRandomFacts(response, type);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());


                showRandomFacts("Error Occurred. Please try again", type);
            }
        });
        queue.add(getRequest);

    }

    private void showRandomFacts(String description, final String type) {

        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setTitle(type.toUpperCase())
                .setMessage(description)
                .setPositiveButton("Randomize", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRandomFactsFromApi(type);
                    }
                })
                .setNegativeButton("Close", null)
                .show();


    }

    private void askForQuestNumber(final String type) {

        int inputTypeValue = 2;
        String inputFilterText = "Please Enter a number";
        String hint = "Eg:45";

        if(type.toLowerCase().equals("date")){
            inputTypeValue = 4;
            inputFilterText = "Please Enter a number or date";
            hint ="Eg:01/23 or 35";

        }

        new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setTitle("Quest Facts")
                .setHint(hint)
                .setMessage("Enter a number for " + type + " Quest Fact")
                .setInputType(inputTypeValue)
                .setInputFilter(inputFilterText, new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.matches("\\w+");
                    }
                })
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {

                        getQuestFactsFromApi(text, type);
                    }
                })
                .show();
    }

    private void getQuestFactsFromApi(final String num, final String type) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://numbersapi.com/" + num + "/" + type;


        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showQuestFacts(type, response, num);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                showQuestFacts(type, "Error Occurred. Please try again", num);
            }
        });
        queue.add(getRequest);
    }

    private void showQuestFacts(final String type, String description, final String num) {

        new LovelyStandardDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setTitle(type.toUpperCase())
                .setMessage(description)
                .setPositiveButton("Next", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getQuestFactsFromApi(String.valueOf(Integer.parseInt(num) + 1), type);
                    }
                })
                .setNegativeButton("Close", null)
                .show();

    }



}