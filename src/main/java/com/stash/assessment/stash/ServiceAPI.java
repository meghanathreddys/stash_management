package com.stash.assessment.stash;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statsh.assessment.stash.dto.StudentRecordDTO;
import com.statsh.assessment.stash.utils.HttpConnectionManager;

public class ServiceAPI {

	public static StudentRecordDTO getjsonResponse(StudentRecordDTO studentRecord)
			throws ClientProtocolException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String stringEntity = "{\"email\":\"" + studentRecord.getEmail() + "\",\"key\":\"" + studentRecord.getKey()
				+ "\"}";

		String resp = HttpConnectionManager.getJsonResponse("https://account-key-service.herokuapp.com/v1/account",
				stringEntity);
		StudentRecordDTO studentRecordDto = mapper.readValue(resp, StudentRecordDTO.class);
		studentRecord.setAccount_key(studentRecordDto.getAccount_key());
		return studentRecord;
	}

}
