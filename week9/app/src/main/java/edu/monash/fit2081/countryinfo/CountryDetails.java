package edu.monash.fit2081.countryinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {

    private TextView name;
    private TextView capital;
    private TextView code;
    private TextView population;
    private TextView area;
    private TextView currencies;
    private TextView languages;
    private TextView region;
    private ImageView flag;
    private Button button;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        getSupportActionBar().setTitle(R.string.title_activity_country_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        name = findViewById(R.id.country_name);
        capital =  findViewById(R.id.capital);
        code =  findViewById(R.id.country_code);
        population =  findViewById(R.id.population);
        area = findViewById(R.id.area);
        currencies = findViewById(R.id.currencies);
        languages = findViewById(R.id.languages);
        flag = findViewById(R.id.imageView);
        region = findViewById(R.id.region);
        button = findViewById(R.id.button);

        new GetCountryDetails().execute(selectedCountry);
    }


    private class GetCountryDetails extends AsyncTask<String, String, CountryInfo> {

        @Override
        protected CountryInfo doInBackground(String... params) {
            CountryInfo countryInfo = null;
            try {
                // Create URL
                String selectedCountry = params[0];
                URL webServiceEndPoint = new URL("https://restcountries.eu/rest/v2/name/" + selectedCountry); //

                // Create connection
                HttpsURLConnection myConnection = (HttpsURLConnection) webServiceEndPoint.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    //JSON data has arrived successfully, now we need to open a stream to it and get a reader
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    //now use a JSON parser to decode data
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginArray(); //consume arrays's opening JSON brace
                    String keyName;
                    countryInfo = new CountryInfo(); //nested class (see below) to carry Country Data around in
                    boolean countryFound = false;
                    while (jsonReader.hasNext() && !countryFound) { //process array of objects
                        jsonReader.beginObject(); //consume object's opening JSON brace
                        while (jsonReader.hasNext()) {// process key/value pairs inside the current object
                            keyName = jsonReader.nextName();
                            switch (keyName) {
                                case "name":
                                    countryInfo.setName(jsonReader.nextString());
                                    if (countryInfo.getName().equalsIgnoreCase(selectedCountry)) {
                                        countryFound = true;
                                    }
                                    break;
                                case "alpha2Code":
                                    countryInfo.setAlpha2Code(jsonReader.nextString());
                                    break;
                                case "alpha3Code":
                                    countryInfo.setAlpha3Code(jsonReader.nextString());
                                    break;
                                case "capital":
                                    countryInfo.setCapital(jsonReader.nextString());
                                    break;
                                case "population":
                                    countryInfo.setPopulation(jsonReader.nextInt());
                                    break;
                                case "area":
                                    countryInfo.setArea(jsonReader.nextDouble());
                                    break;
                                case "region":
                                    countryInfo.setRegion(jsonReader.nextString());
                                    break;
                                case "currencies":
                                    ArrayList<String> currencies = new ArrayList<String>();
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            String name = jsonReader.nextName();
                                            if (name.equals("name")) {
                                                currencies.add(jsonReader.nextString());
                                            } else {
                                                jsonReader.skipValue();
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                    countryInfo.setCurrencies(currencies);
                                    break;
                                case "languages":
                                    ArrayList<String> languages = new ArrayList<String>();
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            String name = jsonReader.nextName();
                                            if (name.equals("name")) {
                                                languages.add(jsonReader.nextString());
                                            } else {
                                                jsonReader.skipValue();
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                    countryInfo.setLanguages(languages);
                                    break;
                                default:
                                    jsonReader.skipValue();
                                    break;
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                } else {
                    Log.i("INFO", "Error:  No response");
                }

                // All your networking logic should be here
            } catch (Exception e) {
                Log.i("INFO", "Error " + e.toString());
            }
            return countryInfo;
        }

        @Override
        protected void onPostExecute(CountryInfo countryInfo) {
            super.onPostExecute(countryInfo);
            name.setText(countryInfo.getName());
            capital.setText(countryInfo.getCapital());
            code.setText(countryInfo.getAlpha3Code());
            population.setText(Integer.toString(countryInfo.getPopulation()));
            area.setText(Double.toString(countryInfo.getArea()));
            currencies.setText(countryInfo.getCurrencies().stream().collect(Collectors.joining(", ")));
            languages.setText(countryInfo.getLanguages().stream().collect(Collectors.joining(", ")));
            region.setText(countryInfo.getRegion());
            button.setText("Wiki "  + countryInfo.getName());

            //String url = "https://www.countryflags.io/" + countryInfo.getAlpha2Code() + "/flat/64.png";
            String url = "https://flagcdn.com/144x108/" + countryInfo.getAlpha2Code().toLowerCase() + ".png";
            new FlagImage(flag).execute(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(View v) {
        Intent myIntent = new Intent(CountryDetails.this, WebWiki.class);
        myIntent.putExtra("url", "https://en.wikipedia.org/wiki/" + name.getText());
        CountryDetails.this.startActivity(myIntent);
    }

    private class FlagImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public FlagImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private class CountryInfo {
        private String name;
        private String alpha2Code;
        private String alpha3Code;
        private String capital;
        private int population;
        private double area;
        private ArrayList<String> currencies = new ArrayList<String>();
        private ArrayList<String> languages = new ArrayList<String>();
        private String region;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlpha3Code() {
            return alpha3Code;
        }

        public void setAlpha3Code(String alpha3Code) {
            this.alpha3Code = alpha3Code;
        }

        public String getAlpha2Code() {
            return alpha2Code;
        }

        public void setAlpha2Code(String alpha2Code) {
            this.alpha2Code = alpha2Code;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public double getArea() {
            return area;
        }

        public void setArea(double area) {
            this.area = area;
        }

        public ArrayList<String> getCurrencies() {
            return currencies;
        }

        public void setCurrencies(ArrayList<String> currencies) {
            this.currencies = currencies;
        }

        public ArrayList<String> getLanguages() {
            return languages;
        }

        public void setLanguages(ArrayList<String> languages) {
            this.languages = languages;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion() {
            return region;
        }
    }
}
