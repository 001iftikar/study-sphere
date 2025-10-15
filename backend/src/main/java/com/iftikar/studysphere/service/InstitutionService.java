package com.iftikar.studysphere.service;

import com.iftikar.studysphere.entity.Admin;
import com.iftikar.studysphere.entity.Institution;
import com.iftikar.studysphere.repository.AdminRepository;
import com.iftikar.studysphere.repository.InstitutionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public void registerInstitution(Institution institution, Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                        .orElseThrow(() -> new RuntimeException("Not found"));

        Institution newIns = Institution.builder()
                .uniqueName(institution.getUniqueName())
                .name(institution.getName())
                .secretKey(institution.getSecretKey())
                .admin(admin)
                .build();
        institutionRepository.save(newIns);

    }

    public Institution getSpecificInstitution(Long id) {
        return institutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
    }

    @Transactional
    public Page<Institution> getAllInstitution(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return institutionRepository.findAll(pageable);
    }

    // We will only change name and unique name
    @Transactional
    public void updateInstitution(Institution institution) {
        Institution existing = institutionRepository
                .findById(institution.getId())
                .orElseThrow(
                        () -> new RuntimeException("Institution not found")
                );

        if (institution.getUniqueName() != null) {
            existing.setUniqueName(institution.getUniqueName());
        }

        if (institution.getName() != null) {
            existing.setName(institution.getName());
        }
    }

    // Here we will change admin
    @Transactional
    public void changeAdmin(Long adminId, Long institutionId) {

        Institution existingInstitution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));

        Admin newAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));


        existingInstitution.setAdmin(newAdmin);
    }
}

















