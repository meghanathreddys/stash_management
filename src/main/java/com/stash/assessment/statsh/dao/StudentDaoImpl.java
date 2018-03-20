package com.stash.assessment.statsh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.stash.assessment.stash.ServiceAPI;
import com.statsh.assessment.stash.dto.StudentRecordDTO;
import com.statsh.assessment.stash.utils.Response;
import com.statsh.assessment.stash.utils.ResponseCode;

public class StudentDaoImpl implements StudentDao {

	private JdbcTemplate jdbcTemplate;

	ServiceAPI api = new ServiceAPI();

	public StudentDaoImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Response saveRecord(StudentRecordDTO studentRecord) {
		Response response = new Response();
		List<String> errorList = validateInputRecord(studentRecord);
		if (!errorList.isEmpty()) {
			response.setPayload(errorList);
			response.setResponseCode(ResponseCode.UNPROCESSABLEENTITY);
			return response;
		} else {
			UUID u = UUID.randomUUID();
			studentRecord.setKey(u.toString());
			try {
				ServiceAPI.getjsonResponse(studentRecord);
			} catch (Exception e) {
				response.setPayload(null);
				response.setResponseCode(ResponseCode.SERVEREXCEPTION);
			}
		}
		try {
			String sql = "INSERT INTO STUDENT (FULL_NAME, PHONE_NUMBER, EMAIL,PASSWORD,`KEY`,ACCOUNT_KEY,METADATA)"
					+ " VALUES (?,?,?, ?, ?, ?,?)";
			jdbcTemplate.update(sql, studentRecord.getFullName(), studentRecord.getPhoneNumber(),
					studentRecord.getEmail(), studentRecord.getPassword(), studentRecord.getKey(),
					studentRecord.getAccount_key(), studentRecord.getMetadata());

			String responseSql = "select * from student where account_key='" + studentRecord.getAccount_key()
					+ "'order by id desc";
			List<StudentRecordDTO> studentRecordList = jdbcTemplate.query(responseSql,
					new RowMapper<StudentRecordDTO>() {

						@Override
						public StudentRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							StudentRecordDTO studentRecord = new StudentRecordDTO();
							studentRecord.setAccount_key(rs.getString("ACCOUNT_KEY"));
							studentRecord.setEmail(rs.getString("EMAIL"));
							studentRecord.setFullName(rs.getString("FULL_NAME"));
							studentRecord.setId(rs.getInt("ID"));
							studentRecord.setKey(rs.getString("KEY"));
							studentRecord.setMetadata(rs.getString("METADATA"));
							studentRecord.setPassword(rs.getString("PASSWORD"));
							studentRecord.setPhoneNumber(rs.getString("PHONE_NUMBER"));

							return studentRecord;
						}

					});
			response.setPayload(studentRecordList);
			response.setResponseCode(ResponseCode.CREATED);
			return response;
		} catch (Exception e) {
			response.setPayload(null);
			response.setResponseCode(ResponseCode.UNPROCESSABLEENTITY);
			return response;
		}

	}

	private boolean isPhoneNumberExist(String phoneNumber) {
		String sql = "select PHONE_NUMBER from student where phone_number='" + phoneNumber + "'";
		List<Map<String, Object>> count = jdbcTemplate.queryForList(sql);
		if (!count.isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean isEmailUnique(String email) {
		String sql = "select EMAIL from student where email='" + email + "'";
		List<Map<String, Object>> count = jdbcTemplate.queryForList(sql);
		if (!count.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Response showAllRecords() {

		Response response = new Response();
		try {
			String sql = "select * from student order by id desc";
			List<StudentRecordDTO> studentRecordList = jdbcTemplate.query(sql, new RowMapper<StudentRecordDTO>() {

				@Override
				public StudentRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					StudentRecordDTO studentRecord = new StudentRecordDTO();
					studentRecord.setAccount_key(rs.getString("ACCOUNT_KEY"));
					studentRecord.setEmail(rs.getString("EMAIL"));
					studentRecord.setFullName(rs.getString("FULL_NAME"));
					studentRecord.setId(rs.getInt("ID"));
					studentRecord.setKey(rs.getString("KEY"));
					studentRecord.setMetadata(rs.getString("METADATA"));
					studentRecord.setPassword(rs.getString("PASSWORD"));
					studentRecord.setPhoneNumber(rs.getString("PHONE_NUMBER"));

					return studentRecord;
				}

			});
			response.setPayload(studentRecordList);
			response.setResponseCode(ResponseCode.SUCCESS);
			return response;
		} catch (Exception e) {
			response.setPayload(null);
			response.setResponseCode(ResponseCode.UNPROCESSABLEENTITY);
			return response;
		}

	}

	@Override
	public Response searchRecordByInput(String requestInput) {
		Response response = new Response();

		try {
			String sql = "select * from student where metadata like '%" + requestInput + "%' OR email like '%"
					+ requestInput + "%' OR full_name like '%" + requestInput + "%'order by id desc;";
			List<StudentRecordDTO> studentRecordList = jdbcTemplate.query(sql, new RowMapper<StudentRecordDTO>() {

				@Override
				public StudentRecordDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					StudentRecordDTO studentRecord = new StudentRecordDTO();
					studentRecord.setAccount_key(rs.getString("ACCOUNT_KEY"));
					studentRecord.setEmail(rs.getString("EMAIL"));
					studentRecord.setFullName(rs.getString("FULL_NAME"));
					studentRecord.setId(rs.getInt("ID"));
					studentRecord.setKey(rs.getString("KEY"));
					studentRecord.setMetadata(rs.getString("METADATA"));
					studentRecord.setPassword(rs.getString("PASSWORD"));
					studentRecord.setPhoneNumber(rs.getString("PHONE_NUMBER"));

					return studentRecord;
				}

			});
			if (studentRecordList.isEmpty()) {
				response.setPayload("No records found with given input");
				response.setResponseCode(ResponseCode.SUCCESS);
				return response;
			}
			response.setPayload(studentRecordList);
			return response;
		} catch (Exception e) {
			response.setPayload(null);
			response.setResponseCode(ResponseCode.UNPROCESSABLEENTITY);
			return response;
		}

	}

	private List<String> validateInputRecord(StudentRecordDTO studentRecord) {
		List<String> errorList = new ArrayList<String>();
		if (studentRecord.getFullName().length() > 100 || studentRecord.getFullName() == null) {
			errorList.add("Please enter a valid name with less than 100 characters");
		}
		if (studentRecord.getEmail().length() > 200 || studentRecord.getEmail() == null) {
			errorList.add("Please enter a valid Email with less than 200 characters");
		}
		if (studentRecord.getEmail() != null) {
			if (!isEmailUnique(studentRecord.getEmail())) {
				errorList.add("Email already exist");
			}
		}
		if (studentRecord.getPhoneNumber() == null) {
			errorList.add("Please enter phone Number");
		}
		if (!isPhoneNumberExist(studentRecord.getPhoneNumber())) {
			errorList.add("Phone Number already Exist");
		}
		if (studentRecord.getPassword() == null) {
			errorList.add("Please enter a valid password");
		}
		return errorList;
	}
}
