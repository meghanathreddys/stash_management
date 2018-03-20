package com.stash.assessment.stash;

import com.stash.assessment.stash.controller.Controller;
import com.statsh.assessment.stash.dto.StudentRecordDTO;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class ServiceAPITest extends TestCase {
	static Controller ct = new Controller();

	static StudentRecordDTO studentRecord = new StudentRecordDTO();

	public static void main(String[] args) {
		studentRecord.setEmail("testemail@gmail.com");
		studentRecord.setFullName("Stats_assesment");
		studentRecord.setMetadata("This is metadata");
		studentRecord.setPhoneNumber("1234567890");
		studentRecord.setPassword("password");

//		System.out.println(ct.getStudentUserRecord(null).getPayload().toString());
//		System.out.println(ct.getStudentUserRecord(null).getCode());
//		System.out.println(ct.getStudentUserRecord(null).getStatus());
//		System.out.println(ct.getStudentUserRecord("test").getCode());
//		System.out.println(ct.getStudentUserRecord("test").getStatus());
//		System.out.println(ct.getStudentUserRecord("test").getPayload().toString());
//		System.out.println(ct.persistStudentRecord(studentRecord).getCode());
//		System.out.println(ct.persistStudentRecord(studentRecord).getStatus());
//		System.out.println(ct.persistStudentRecord(studentRecord).getPayload().toString());
	}
}
