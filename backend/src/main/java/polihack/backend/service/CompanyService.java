package polihack.backend.service;

import polihack.backend.model.Company;
import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    Company findByUsername(String username);
    Company registerCompany(Company company);
}