package com.hojong.a2018_seoulapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LogoActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		Log.d("onCreate", "hello");

		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				// Create URL
				try {
					URL serverUrl = new URL("http://api.github.com/");
					// Create connection
					HttpURLConnection myConnection = (HttpURLConnection) serverUrl.openConnection();
					myConnection.setRequestMethod("POST");
					myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

					myConnection.setDoOutput(true);
					String myData = "message=Hello";
					myConnection.getOutputStream().write(myData.getBytes());

					if (myConnection.getResponseCode() == 200) {
						InputStream responseBody = myConnection.getInputStream();
						InputStreamReader responseBodyReader =
								new InputStreamReader(responseBody, "UTF-8");

						JsonReader jsonReader = new JsonReader(responseBodyReader);
						jsonReader.beginObject(); // Start processing the JSON object
						while (jsonReader.hasNext()) { // Loop through all keys
							String key = jsonReader.nextName(); // Fetch the next key
							if (key.equals("organization_url")) { // Check if desired key
								// Fetch the value as a String
								String value = jsonReader.nextString();

								// Do something with the value
								// ...

								break; // Break out of the loop
							} else {
								jsonReader.skipValue(); // Skip values of other keys
							}
						}
						jsonReader.close();
						myConnection.disconnect();
					} else {
						// Error handling code goes here
					}
				}
				catch (Exception e)
				{
					Log.e("Logo", e.toString());
				}
			}
		});
	}
}
