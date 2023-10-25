package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    // Inject a service or repository that handles data access
    private final ComplianceService complianceService;

    @Autowired
    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

	@GetMapping({ "/dashboard", "/" })
    public List<Employee> getComplianceReport() {
		String filterArr[] = new String[] {};
		List<Employee> report = complianceService.findAllComplianceReports(filterArr);
		return report;

    }
	
    
	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	@ResponseBody
	public List<Employee> filterCompliance(@RequestBody String[] filterArr)
				throws ParseException, IOException, GeneralSecurityException {
		
		List<Employee> report = complianceService.findAllComplianceReports(filterArr);
		return report;

	}
}
