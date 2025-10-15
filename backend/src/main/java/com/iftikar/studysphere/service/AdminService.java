package com.iftikar.studysphere.service;

import com.iftikar.studysphere.entity.Admin;
import com.iftikar.studysphere.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public void createAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    // update any details of admin

    @Transactional
    public void updateAdmin(Admin admin) {
        Admin existingAdmin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // we can change either all details or partial except for password, I will make another fun to change password

        if (admin.getEmail() != null) {
            existingAdmin.setEmail(admin.getEmail());
        }

        if (admin.getName() != null) {
            existingAdmin.setName(admin.getName());
        }

        if (admin.getPhone() != null) {
            existingAdmin.setPhone(admin.getPhone());
        }
    }

    /*
    We will not do delete admin, instead we will change admin if needed ( basically bro is kicked
    out of his own institution ).
     */
}





















