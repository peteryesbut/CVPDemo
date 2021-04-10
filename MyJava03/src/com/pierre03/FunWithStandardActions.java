package com.pierre03;

import com.audium.server.session.ActionElementData;
import com.audium.server.voiceElement.ActionElementBase;

public class FunWithStandardActions extends ActionElementBase {

	@Override
	public void doAction(String name, ActionElementData data) throws Exception {
		// TODO Auto-generated method stub
		
		String strAreaCode="NA";		
		
		String strAni = data.getAni();
		
		if (!strAni.equalsIgnoreCase("NA")) {
			strAreaCode = strAni.substring(0, 3);
			// starting index is inclusive
			// ending index is exclusive
			data.addToLog("AreaCode", strAreaCode);
		}
		else {
			data.logWarning("Ani does not exist. Setting session data AreaCode to NA");			
		}
		//save the area code into session data named AreaCode
		data.setSessionData("AreaCode", strAreaCode);
		// data.setElementData(arg0, arg1);
		
		// The GUID is stored in a session Data named callid
		String strGUID = (String) data.getSessionData("callid");
		// will return a null if it doesn't exist.
		
		int intLength = 0;
		
		if (strGUID != null) {
			intLength = strGUID.length();
		}
		else
		{
			data.logWarning("Session data callid does not exist. Setting Element Data guilength to 0");
		}
				
		data.setElementData("GuidLength", Integer.toString(intLength),0, true);
		
	}

}
