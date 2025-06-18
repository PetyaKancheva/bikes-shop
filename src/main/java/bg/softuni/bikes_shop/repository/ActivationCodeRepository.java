package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.entity.UserActivationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ActivationCodeRepository extends JpaRepository<UserActivationCodeEntity, Long> {
    Optional<UserActivationCodeEntity> findByActivationCode(String activationCode);
       void deleteUserActivationCodeEntitiesByCreatedIsBefore(Instant date);


}
