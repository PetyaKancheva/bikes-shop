package bg.softuni.bikes_shop.util;

import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;
import bg.softuni.bikes_shop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner       {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public void run(String... args) throws Exception {
            if(userRoleRepository.count()==0){
                userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.USER));
                userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.ADMIN));
                userRoleRepository.save(new UserRoleEntity().setName(UserRoleEnum.EMPLOYEE));
            }
    }
}
