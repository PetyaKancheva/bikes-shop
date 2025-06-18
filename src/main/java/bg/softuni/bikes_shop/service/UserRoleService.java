package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;

import java.util.Optional;

public interface UserRoleService {

  Optional< UserRoleEntity> getUserRoleByName(UserRoleEnum userRoleEnum);


}
