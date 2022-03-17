package club.hosppy.account.repo;

import club.hosppy.account.model.AccountSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountSecretRepository extends JpaRepository<AccountSecret, String> {

    AccountSecret findAccountSecretByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AccountSecret account SET account.passwordHash = :passwordHash WHERE account.id = :id")
    @Transactional
    int updatePasswordHashById(String passwordHash, String id);
}