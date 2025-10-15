package com.iftikar.studysphere;

import com.iftikar.studysphere.entity.Institution;
import com.iftikar.studysphere.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InstitutionTest {

    @Autowired
    private InstitutionService institutionService;

    @Test
    public void testRegisterInstitution() {
        Institution institution = Institution.builder()
                .uniqueName("test ins 1")
                .name("Glorious")
                .secretKey("shareme")
                .build();

        institutionService.registerInstitution(institution, 1L);
    }

    @Test
    public void testFindSpecificInstitution() {
        Institution institution = institutionService.getSpecificInstitution(6L);

        System.out.println(institution.toString());
    }

    @Test
    public void testUpdateInstitution() {
        Institution institution = Institution.builder()
                .id(1L)
                .name("Changed Institution")
                .uniqueName("changed")
                .build();
        institutionService.updateInstitution(institution);
    }

    @Test
    public void testChangeAdmin() {
        institutionService.changeAdmin(2L, 1L);
    }

    @Test
    public  void getAllInstitutions() {
        var instituions = institutionService.getAllInstitution(0, 4).getContent();
        instituions.forEach(System.out::println);
    }
}























