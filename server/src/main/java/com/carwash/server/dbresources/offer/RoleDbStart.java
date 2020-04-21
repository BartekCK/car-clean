package com.carwash.server.dbresources.offer;

import com.carwash.server.models.authority.Role;
import com.carwash.server.models.authority.RoleName;
import com.carwash.server.repositories.RoleJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class RoleDbStart implements ApplicationRunner {

    RoleJPARepository roleJPARepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleJPARepository.count() <= 0) {
            try {
                roleJPARepository.saveAll(Arrays.asList(
                        new Role(RoleName.ROLE_USER),
                        new Role(RoleName.ROLE_EMPLOYEE)));
            } catch (Error e) {
                System.out.println("Bład dodawania roli do bazy danych");
            }
        }
    }
}
