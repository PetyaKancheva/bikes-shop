package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface   UserRepository extends JpaRepository<UserEntity,Long> {
       Optional< UserEntity> findUserByEmail(String email);
@Query(value="SELECT *  FROM users AS u WHERE ? IN (u.e_mail, u.first_name , u.last_name ) AND u.is_enabled is TRUE",
        nativeQuery = true)
    List<UserEntity> findAllByEmailFirsOrLastName(String name);
}
