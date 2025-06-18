package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
    Optional<UserRoleEntity> findByName(UserRoleEnum userRoleEnum);


     List<UserRoleEntity> findAllByNameIn(List<UserRoleEnum> names);

}

