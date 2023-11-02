package com.example.demo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class ComplianceService {
	@Autowired
	JdbcTemplate jdbc;
	public static final String DEFAULT_DATE_COMPLETION = "1900-01-01";//2023-11-02T11:01//2023-10-01

	
	public List<Employee> findAllComplianceReports(String[] filterArr) {
		//SELECT * FROM springbootdb.slots where slot_start>='2022-08-31 11:00:00' and slot_end <= '2022-09-01 14:00:00';
				//String sql = "SELECT * FROM Employee";
//		filterArr = new String[] { filter_patch_region, filter_patch_name, filter_patch_compliance, filter_roll_no_emp_name};

		HashMap<Integer, String> patchIdNameMap = findPatchIdNameMap();
        HashMap<String, Integer> reversedPatchIdNameMap = new HashMap<>();
        
		for (Entry<Integer, String> entry : patchIdNameMap.entrySet()) {
			reversedPatchIdNameMap.put(entry.getValue(), entry.getKey());
	        }

		String filter_patch_region="", filter_patch_name="", filter_patch_compliance="", filter_roll_no_emp_name="";
	       if(ArrayUtils.isNotEmpty(filterArr))
	       {
		 filter_patch_region = filterArr[0] == null ? "" : filterArr[0];
		 filter_patch_name = filterArr[1] == null ? "" : filterArr[1];
		 filter_patch_compliance = filterArr[2] == null ? "" : filterArr[2];
		 filter_roll_no_emp_name = filterArr[3] == null ? "" : filterArr[3];
	       }

		// Your initial SQL query
		String sql = "SELECT * FROM Employee";

		// Initialize a StringBuilder to build the dynamic WHERE clause
		StringBuilder whereClause = new StringBuilder();

		// Check if any filter values are provided and add conditions to the WHERE clause
		if (StringUtils.isNotBlank(filter_patch_region) && !("ANY".equalsIgnoreCase(filter_patch_region) || "ALL".equalsIgnoreCase(filter_patch_region))) {
		    whereClause.append(" AND emp_region = '").append(filter_patch_region).append("'");
		}

		if (StringUtils.isNotBlank(filter_patch_name) && !("ANY".equalsIgnoreCase(filter_patch_name) || "ALL".equalsIgnoreCase(filter_patch_name))) {
		    int filter_patch_id = reversedPatchIdNameMap.get(filter_patch_name);
		    whereClause.append(" AND applicable_patch_id = '").append(filter_patch_id).append("'");
		}

		if (StringUtils.isNotBlank(filter_patch_compliance) && !("ANY".equalsIgnoreCase(filter_patch_compliance)|| "ALL".equalsIgnoreCase(filter_patch_compliance))) {
		    whereClause.append(" AND patch_compliance = '").append(filter_patch_compliance).append("'");
		}

		if (StringUtils.isNotBlank(filter_roll_no_emp_name) && !("ANY".equalsIgnoreCase(filter_roll_no_emp_name)|| "ALL".equalsIgnoreCase(filter_roll_no_emp_name))) {
		    whereClause.append(" AND (roll_no = '").append(filter_roll_no_emp_name).append("' OR emp_name LIKE '%").append(filter_roll_no_emp_name).append("%')");
		}

		// Combine the SQL query and the WHERE clause
		if (whereClause.length() > 0) {
		    sql += " WHERE " + whereClause.substring(5); // Remove the initial " AND "
		}

		// The SQL query is now ready for execution

		// Now, you can execute the SQL query
	
			List<Employee> slots = new ArrayList<>();

				List<Map<String, Object>> rows = jdbc.queryForList(sql);
				String tz_Tmp, region_tmp;
				boolean isBookTmp;
				
				
					
				
				for (Map row : rows) {
					try {
						System.err.println(row.get("roll_no"));
					Employee obj = new Employee();
				
					obj.setRoll_no(((Integer) row.get("roll_no")));
					obj.setEmp_name(((String) row.get("emp_name")).toString());
					
					int patchId = (Integer) row.get("applicable_patch_id");
					
					obj.setApplicable_patch_id(((String) patchIdNameMap.get(patchId).toString()));
					obj.setPatch_compliance(((String) row.get("patch_compliance")).toString());
					
					String dateOfCompletion = ((Date) row.get("date_of_completion")).toString();
					if(StringUtils.equalsIgnoreCase(DEFAULT_DATE_COMPLETION, dateOfCompletion))
						dateOfCompletion = StringUtils.EMPTY;
					
					obj.setDate_of_completion(dateOfCompletion);
					obj.setE_mail_id(((String) row.get("e_mail_id")).toString());

					obj.setEmp_region(((String) row.get("emp_region")).toString());


				

					slots.add(obj);
				} catch (Exception e) {
					System.err.println("exception - " + e);
				}
			}
				return slots;
			}

	public HashMap<Integer, String> findPatchIdNameMap() {
				String sql = "SELECT * FROM PatchCatalog";

				List<Map<String, Object>> rows = jdbc.queryForList(sql);
				
				HashMap<Integer, String> patchIdNameMap = new HashMap<>();

				for (Map row : rows) {

					patchIdNameMap.put((Integer) row.get("id"), (String) row.get("name"));
				
				}

				return patchIdNameMap;
			}

}
