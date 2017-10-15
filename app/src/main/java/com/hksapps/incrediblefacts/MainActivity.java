package com.hksapps.incrediblefacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    private String result;
    private LinearLayout triviaRandom, triviaQuest, dateRandom, dateQuest, yearRandom, yearQuest, mathRandom, mathQuest;
    private EditText customEdittext;

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


                getRandomFactsFromApi("trivia");

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

                        showRandomFacts(response.toString(), type);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());


                showRandomFacts("Error Occured", type);
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
        new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setTitle("Quest Facts")
                .setMessage("Enter a number for " + type + " Quest Fact")
                .setInputType(2)
                .setInputFilter("Please Enter a number", new LovelyTextInputDialog.TextFilter() {
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

    private void getQuestFactsFromApi(final String num, final String type) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://numbersapi.com/" + num + "/" + type;


        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showQuestFacts(type, response.toString(), num);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());


                showQuestFacts(type, "Error Occured", num);
            }
        });
        queue.add(getRequest);
    }

}