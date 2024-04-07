package test.wallet.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.wallet.challenge.repository.entities.Account;


public interface SqlServerAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    @Query("SELECT a FROM Account a WHERE a.user = :userId")
    Account findByUserId(@Param("userId") String userId);
}