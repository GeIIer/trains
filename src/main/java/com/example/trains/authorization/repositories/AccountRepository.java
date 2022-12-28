package com.example.trains.authorization.repositories;

import com.example.trains.api.dto.CityDTOWithCount;
import com.example.trains.authorization.dto.AccountDTO;
import com.example.trains.authorization.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    boolean existsAccountEntityByEmail(String email);
    Optional<AccountEntity> findByName(String name);

    AccountEntity findByEmail(String email);
    AccountEntity save(AccountEntity registration);

    @Override
    long count();

    //
    @Query(value = "SSELECT a.id, a.name, email, r.name From account a RIGHT JOIN role r on r.id = a.role_id WHERE r.name = 'MODERATOR' group by a.id, a.name, email, r.name\n", nativeQuery = true)
    ArrayList<AccountDTO> getModeratorsSQL();
}
