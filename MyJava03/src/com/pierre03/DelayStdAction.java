package com.pierre03;

import com.audium.server.session.ActionElementData;
import com.audium.server.voiceElement.ActionElementBase;

public class DelayStdAction extends ActionElementBase {

	@Override
	public void doAction(String name, ActionElementData data) throws Exception {

		String strAreaCode = "NA";
		
		String strAni = data.getAni();
		if(strAni.equalsIgnoreCase("NA") ) {
			data.addToLog("No ani received", "");
			data.logWarning("Ani does not exists, Setting session data AreaCode too NA");
		} else {
			data.addToLog("The ani is", strAni);			
			strAreaCode = strAni.substring(0, 3);
			data.addToLog("AreaCode",strAreaCode);
		}
	    data.setSessionData("AreaCode", strAreaCode);
	    
	    int intLengthGuid=0;
	    String strGUID = (String) data.getSessionData("callid");
	    if (strGUID != null) {
	    	intLengthGuid = strGUID.length();
	    } else {
	    	data.logWarning("Session Data callid does not exists. Setting Element Data guidlength to 0");
	    }
	    
	    //1: PD_INT
	    //0: PD STING
	    //2: float
	    //3: boolean
	    //true: add to activity log
	    
	    data.setElementData("guidlength", Integer.toString(intLengthGuid),1,true);
		
		String strDnis = data.getDnis();
		if("NA".equalsIgnoreCase(strDnis) ) {
			data.addToLog("No dnis received", "");
		} else {
			data.addToLog("The dnis is", strDnis);
		}

		String strGuid = (String) data.getSessionData("callid");
		if(strGuid ==null) {
			data.addToLog("Session data callid is null","");
		} else {
			data.addToLog("GUID (callid)", strGuid);
			int intLength= strGuid.length();
			data.addToLog("Length of GUID", Integer.toString(intLength));
		}		

		String appname = data.getApplicationName();
		data.addToLog("Current app name", appname );

//		data.addToLog("DelayPierre", "15s");
//		Thread.sleep(15000);
		
		data.addToLog("Done", "");
	}

}
