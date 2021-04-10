package com.pierre03;

import com.audium.server.AudiumException;
import com.audium.server.proxy.StartCallInterface;
import com.audium.server.session.CallStartAPI;

public class RenameCallidAtCallStart implements StartCallInterface {

	@Override
	public void onStartCall(CallStartAPI data) throws AudiumException {
		
		String srcAppName = data.getSrcAppName();
		//If the source app name is null then it’s a new call from ICM, do nothing.
		
		if (srcAppName != null) {
			//if the source application passed a callid, it’ll be named <srcApp>_callid
			String callidVar = (String) data.getSessionData(srcAppName + "_callid");
			
			//test if there is Session data named this
			if ( callidVar !=null) {
				//create session data named “callid” to hold the passed value
				data.setSessionData("callid", callidVar);
				data.addToLog("callidInTheBank", callidVar);
			} else {
				data.logWarning ("no callid");
			}
		}
	}
}
