package test.wallet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.wallet.challenge.repository.entities.UserEntity;


public interface SqlServerUserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    @Query("SELECT u FROM UserEntity  u WHERE u.userId = :userId")
    UserEntity findByUserId(@Param("userId") String userId);
}