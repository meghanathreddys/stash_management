package com.stash.assessment.stash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stash.assessment.statsh.dao.StudentDao;
import com.statsh.assessment.stash.dto.StudentRecordDTO;
import com.statsh.assessment.stash.utils.Response;

@RestController
public class Controller {

	@Autowired
	private StudentDao studentDao;

	@RequestMapping(value = "/v1/users", method = RequestMethod.GET)
	public Response getStudentUserRecord(String request) {
		if (request == null) {
			return studentDao.showAllRecords();
		} else {
			return studentDao.searchRecordByInput(request);
		}

	}

	@RequestMapping(value = "/v1/users", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response persistStudentRecord(@RequestBody StudentRecordDTO studentRecord) {

		return studentDao.saveRecord(studentRecord);
	}

}
