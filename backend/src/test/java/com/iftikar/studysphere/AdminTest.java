package com.iftikar.studysphere;

import com.iftikar.studysphere.entity.Admin;
import com.iftikar.studysphere.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testCreateAdmin() {
        Admin admin = Admin.builder()
                .email("test email")
                .name("Admin")
                .username("admin_user")
                .phone("27462")
                .password("sdhj")
                .build();
        adminService.createAdmin(admin);
    }


    @Test
    public void testUpdateAdmin() {
        Admin admin = Admin.builder()
                .id(1L)
                .email("Changed email again")
                .name("Changed name")
                .phone("6000729486")
                .build();
        adminService.updateAdmin(admin);
    }

}
