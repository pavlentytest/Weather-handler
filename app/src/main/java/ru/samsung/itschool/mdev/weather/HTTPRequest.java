package ru.samsung.itschool.mdev.weather;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequest implements Runnable{
    private URL url;
    private Handler handler;

    HTTPRequest(Handler in_handler) {
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=");
            handler = in_handler;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String tempString;
            while ((tempString = input.readLine()) != null) {
                response.append(tempString);
            }
            input.close();
            conn.disconnect();

            Message msg = Message.obtain();
            msg.obj = response.toString();
            handler.sendMessage(msg);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
