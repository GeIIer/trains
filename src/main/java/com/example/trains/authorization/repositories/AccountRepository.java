package com.example.trains.authorization.repositories;

import com.example.trains.authorization.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsAccountEntityByEmail(String email);
    AccountEntity findByName(String name);

    AccountEntity findByEmail(String email);
    AccountEntity save(AccountEntity registration);

    @Override
    long count();
}
