package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.LoginRequest;
import polihack.backend.dto.response.LoginResponse;
import polihack.backend.dto.response.CompanyRegisterResponse;
import polihack.backend.model.Company;
import polihack.backend.security.JwtUtil;
import polihack.backend.service.CompanyService;

@RestController
@RequestMapping("/auth/company")
public class CompanyAuthController {

    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CompanyAuthController(CompanyService companyService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.companyService = companyService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<CompanyRegisterResponse> registerCompany(@RequestBody Company company) {
        Company savedCompany = companyService.registerCompany(company);

        CompanyRegisterResponse response = new CompanyRegisterResponse(
                savedCompany.getId(),
                savedCompany.getCompanyName(),
                savedCompany.getCui(),
                savedCompany.getRegistrationNumber(),
                savedCompany.getHeadquartersAddress(),
                savedCompany.getContactPerson(),
                savedCompany.getPhoneNumber(),
                savedCompany.getEmail(),
                savedCompany.getUsername()
        );

        System.out.println("Company registration successful for: " + savedCompany.getCompanyName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCompany(@RequestBody LoginRequest request) {
        Company company = companyService.findByUsername(request.getUsername().toLowerCase());

        if (company != null && passwordEncoder.matches(request.getPassword(), company.getPassword())) {
            String token = jwtUtil.generateToken(company.getUsername(), company.getPassword());

            System.out.println("Company " + company.getUsername() + " logged successfully!");
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(401).body("Invalid credentials!");
    }
}