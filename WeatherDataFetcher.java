package com.nivek.kevoweather;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherDataFetcher {

    private NigerianState[] nigerianStates ;
    private static final String  TAG ="WeatherDataFetcher" ;
    private static final String APPID = "5380558eb6f894fc9c34b06227195c3f";
    /*
    * Given the url string , returns content of the url inform of byte
    * @pa
    *
    */

    public WeatherDataFetcher(){
        nigerianStates = NigerianState.nigerianStates ;
    }


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec) ;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;


        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream() ;
            InputStream in = connection.getInputStream() ;

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw  new IOException(connection.getResponseMessage() +": with" + urlSpec) ;
            }

            int byteRead  = 0 ;
            byte[] buffer = new byte[1024] ;

            while((byteRead=in.read(buffer))>0){// while ther is data to read, continue writing to the to the outputsream
                out.write(buffer,0,byteRead);
            }
            out.close();

            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    //converts the byte array to string
    public String getUrlString(String urlSpec) throws IOException{
        return  new String(getUrlBytes(urlSpec)) ;
    }

    //given the location it fetches the weather data in json format
    public String getWeatherData(String lat , String lon){
        try{
            String url = Uri.parse("https://api.openweathermap.org/data/2.5/weather").buildUpon()
                    .appendQueryParameter("lat",lat).appendQueryParameter("lon",lon).appendQueryParameter("units","metric")
                    .appendQueryParameter("appid",APPID)
                    .build().toString() ;
            String json = getUrlString(url) ;
            Log.i(TAG,"Received json: "+ json);
            return json;
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to fetch weather data",ioe) ;
            return null;
        }

    }


    //attaches the weather data to each city
    public NigerianState[] getInflatedData () throws JSONException {
        Log.i(TAG,"getInflatedData was called");
        String stateJson ;
        for(NigerianState nigerianState : nigerianStates){
            Log.i(TAG,"looped through at least once");
            stateJson = getWeatherData(nigerianState.getLatitude() , nigerianState.getLongitude()) ;
            JSONObject jsonObject = new JSONObject(stateJson);
            JSONArray weather = jsonObject.getJSONArray("weather") ;
            JSONObject first = weather.getJSONObject(0);

            //set the description and icon for the current weather state
            String description =first.getString("description");
            String icon =first.getString("icon");
            nigerianState.setDesc(description);
            nigerianState.setIcon(icon);

            //set the minimum and maximum temperature from the main attributes in the json object
            String lowtemp = String.valueOf(jsonObject.getJSONObject("main").getDouble("temp_min"));
            String hightemp = String.valueOf(jsonObject.getJSONObject("main").getDouble("temp_max"));
            nigerianState.setLowTemp(lowtemp);
            nigerianState.setHighTemp(hightemp);

            Log.i(TAG,description +"  " + icon + " "+ lowtemp +"  " + hightemp);
        }

        return nigerianStates;
    }
}
