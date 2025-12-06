package polihack.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import polihack.backend.model.Company;
import polihack.backend.repository.CompanyRepository;
import polihack.backend.service.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyServiceImpl(CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findByUsername(String username) {
        return companyRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Company registerCompany(Company company) {
        // Normalizam username-ul (lowercase)
        company.setUsername(company.getUsername().toLowerCase());
        // Encodam parola inainte de salvare
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        return companyRepository.save(company);
    }
}