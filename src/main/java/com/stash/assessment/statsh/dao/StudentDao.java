package com.stash.assessment.statsh.dao;

import com.statsh.assessment.stash.dto.StudentRecordDTO;
import com.statsh.assessment.stash.utils.Response;

public interface StudentDao {

	public Response saveRecord(StudentRecordDTO studentRecord);

	public Response showAllRecords();

	public Response searchRecordByInput(String requestInput);
}
