package com.pierre03;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;

/**
 * @author PapaMaman
 *
 *Check if element data for:  getCh04_Dob named value is a valid date (MMDDYYYY)
  (02292001 is not a valid date)
 */
public class DobElementDataTest_StandardDecision extends DecisionElementBase {

	@Override
	public String doDecision(String name, DecisionElementData data) throws Exception {

		// Decision return the exit state to follow as a String
		// Ours will be valid or invalid

		String strExitState = "invalid";
		String strDob = data.getElementData("getCh4_Dob", "value");

		if (strDob == null) 
		{
			data.logWarning("Element Data for Element getCh4_Dob named value does not exit");

		} else if (strDob.length() != 8) {
			data.logWarning("Element Data for Element getCh4_Dob  is not 8 digit in length");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
			sdf.setLenient(false); // don't accept dates where the day is out of range for the month (like 32, or 02292001)

			try {
				sdf.parse(strDob);
				// the date is valid
				strExitState = "valid"; 
			} catch (ParseException e) {
				// strExitState = "invalid";
			}
		}
		return strExitState;
	}
}
