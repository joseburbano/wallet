package test.wallet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.wallet.challenge.repository.entities.AccountEntity;


public interface SqlServerAccountRepository extends JpaRepository<AccountEntity, Integer>, JpaSpecificationExecutor<AccountEntity> {

    @Query("SELECT a FROM AccountEntity a WHERE a.user = :userId")
    AccountEntity findByUserId(@Param("userId") String userId);
}