package com.pierre03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;

public class ReadPropFile_StdDecision extends DecisionElementBase {

	public String doDecision(String name, DecisionElementData data) throws Exception {
		// TODO Auto-generated method stub

		
		String strDebug = (String) data.getSessionData("Debug");
		boolean debug = false;
		
		if("true".equalsIgnoreCase(strDebug)) debug=true;
		
		
		//get fileLoc from a Setting or hard code it 
		String fileLoc="D:\\CVP\\CVP-Java\\MyApp03.callstart.txt";	

		String exitstate="failure";
		Properties prop = new Properties();  //QF:import Properties (java.util)
		FileInputStream fis = null;	         //QF:import FileInputStream (java.io)
		try {
			//This reads the entire file of name=value pairs
			fis = new FileInputStream(fileLoc);
			prop.load(fis);

			//This gets the names of the variables to create into an array
			Object[] varNames = prop.keySet().toArray();

			//This loops through and get the value for each variable read from the file
			for(int i=0;i<varNames.length;i++){
				String varName=(String) varNames[i];
				String varValue = prop.getProperty(varName);
				//TODO - create variable varName with value varValue

				data.setSessionData(varName,varValue);
				if(debug) data.addToLog(varName,varValue);
				//TODO - add it to log
			}
			//TODO - set exitstate to done
			exitstate = "done";

		} catch (FileNotFoundException e) {
			//TODO - set exitstate to noFile

		} catch (Exception e) { 
			//TODO - log a warning containing e.getMessage
			//TODO - set exitstate to failure

			data.logWarning(e.getMessage());
			exitstate = "failure";
		} finally {	
			//Be sure to close the file
			if (fis != null)
			{
				try
				{
					fis.close();					
				}
				catch (Exception e)
				{
					//Catch any exceptions, if it fails to close, not a lot we can do at this point
					//so just ignore this rather than logging it.
				}
			}		
		}
		return exitstate;
	}
}
