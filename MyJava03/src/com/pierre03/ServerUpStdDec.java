package com.pierre03;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;

public class ServerUpStdDec extends DecisionElementBase {

	@Override
	public String doDecision(String name, DecisionElementData data) throws Exception {
		//exit states: up, down, timeout, error
		
		String exit_state="up";
		int status_code=500;
		int timeoutMillis=5000;
		
		//get the TestUri and (optional) TestTimeout from Session Data
		String testUri = (String) data.getSessionData("TestUri");
		
		String testTimeout = (String) data.getSessionData("TestTimeout");
		if (testTimeout != null) {
			timeoutMillis = Integer.valueOf(testTimeout); //exception if can't be converted to integer
		}
		
		URL url = new URL(testUri); // quick fix: import java.net.URL
		
		//this creates the connection object but it doesn't connect yet!
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		//import java.net.HttpURLConnection
		connection.setReadTimeout(timeoutMillis);
		connection.setConnectTimeout(timeoutMillis);
		
		try {
			//this connects to the URI and tries to get the http response code, exception if down or times out
			status_code = connection.getResponseCode();
			//if we get here, the server is up
			exit_state = "up";
		} catch (SocketTimeoutException e) { //import java.net.SocketTimeoutException
			exit_state = "timeout";
			data.setElementData("response", e.getMessage(), 0, true);//true= add to log
		} catch (ConnectException e) { //import java.net.ConnectException
			exit_state = "down";
			data.setElementData("response", e.getMessage(), 0, true);//paste from above
		} catch (Exception e) {
			exit_state = "error";
			data.setElementData("response", e.toString(), 0, true);//paste from above
		}
		data.setElementData("exit_state", exit_state);
		data.setElementData("status_code", Integer.toString(status_code), 0, true);
		return exit_state;
	}
}
