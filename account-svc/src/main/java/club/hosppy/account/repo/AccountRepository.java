package club.hosppy.account.repo;

import club.hosppy.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account findAccountById(String id);

    Account findAccountByEmail(String email);

    Account findAccountByPhoneNumber(String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account account SET account.email = :email, account.confirmedAndActive = true WHERE account.id = :id")
    @Transactional
    int updateEmailAndActivateById(String email, String id);
}