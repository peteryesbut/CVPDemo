package com.pierre03;

import java.io.File;
import java.util.Scanner;

import com.audium.server.AudiumException;
import com.audium.server.proxy.StartCallInterface;
import com.audium.server.session.CallStartAPI;

public class ReadMotdFromFileCallStart implements StartCallInterface {
/*
 An application can be configured with a Call Start component that runs code when a call begins to visit an application, 
 but before re any VoiceXML pages are created by this application 

 A common use of the call start is to create Session data variables to be used by the application

 The startCallInterface class contains a single execution method named OnStartCall that is invoked by VXML Server for the call start class.

 This onStartCall method receives a single parameter, an instance of CallStartAPI that belongs to the Session API and provides access to the session. It can not create Element Data because it’s not called from within an Element.

 The onStartCall method does not end with a return statement
 p109
 
 This code sample creates an On Call Start action that reads the entire contents of a file as one string 
 and stores it into a Session data variable named sMOTD (message of the day).
*
*/
	@Override
	public void onStartCall(CallStartAPI data) throws AudiumException {

		data.addToLog("call start java", this.getClass().toString());
		String fileLoc = "D:\\CVP\\CVP-Java\\" + data.getApplicationName()+".motd.txt";
		data.addToLog("Call Start:reading file", fileLoc);
		Scanner scanner=null; //import from java.util
		File f=null; //import from java.io

		try {
			f = new File(fileLoc);
			scanner = new Scanner(f);
			String content = scanner.useDelimiter("\\Z").next(); // \\Z is read to ‘end of file’
			data.setSessionData("sMOTD", content);
			data.addToLog("sMOTD", content);
		} catch (Exception e) {
			data.logWarning(e.toString());
		} finally { //finally ensures you close the file
			if (scanner != null){
				try {
					scanner.close();
				} catch (Exception e) {
					//nothing
				}
			}
		}
	}
}
