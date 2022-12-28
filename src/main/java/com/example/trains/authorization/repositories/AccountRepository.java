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
/*    @Query(value = "SELECT id_city, city_name,  FROM city\n" +
            "RIGHT JOIN topology t on city.id_city = t.city_id_city\n" +
            "group by id_city, city_name", nativeQuery = true)
    ArrayList<AccountDTO> getModeratorsSQL();*/
}
