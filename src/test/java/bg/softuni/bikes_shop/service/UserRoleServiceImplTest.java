package bg.softuni.bikes_shop.service;


import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;
import bg.softuni.bikes_shop.repository.UserRoleRepository;
import bg.softuni.bikes_shop.service.impl.UserRoleServiceImpl;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserRoleServiceImplTest {
    private UserRoleServiceImpl userRoleServiceTest;
    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @BeforeEach
    void setUp() {
        userRoleServiceTest = new UserRoleServiceImpl(mockUserRoleRepository);
    }


    @Test
    void testUserRoleIsFoundCorrectly() {
        //arrange
        UserRoleEntity testUserRoleEntity = createUserRoleEntity();

        when(mockUserRoleRepository.findByName(UserRoleEnum.valueOf(testUserRoleEntity.getName().name())))
                .thenReturn(Optional.of(testUserRoleEntity));

        //act
        UserRoleEnum roleName =UserRoleEnum.valueOf("ADMIN");
        UserRoleEntity foundEntity = userRoleServiceTest.getUserRoleByName(roleName).orElseThrow()    ;

        //assert

        Assertions.assertNotNull(foundEntity);
        Assertions.assertEquals( foundEntity.getName().name(),roleName.name());

    }

    private static UserRoleEntity createUserRoleEntity() {
        return  new UserRoleEntity().setName(UserRoleEnum.valueOf("ADMIN"));
    }
}









