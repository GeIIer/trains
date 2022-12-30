package com.example.trains.authorization.controllers;

import com.example.trains.api.dto.CityDTOWithCount;
import com.example.trains.authorization.dto.AccountDTO;
import com.example.trains.authorization.dto.AccoutDTOModerator;
import com.example.trains.authorization.entities.AccountEntity;
import com.example.trains.authorization.entities.RoleEntity;
import com.example.trains.authorization.repositories.AccountRepository;
import com.example.trains.authorization.repositories.RoleRepository;
import com.example.trains.authorization.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/services/controller/user")
@AllArgsConstructor
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/getUser")
    public AccountDTO loadUserByEmail(@RequestParam(value = "email", required = false) String email) {
        if (email != null){
            AccountEntity accountEntity = accountRepository.findByEmail(email);
            return new AccountDTO(accountEntity.getId(),
                    accountEntity.getName(),
                    accountEntity.getEmail(),
                    accountEntity.getRole().getName());
        }
        else return null;
    }

    @GetMapping("/allmod")
    public List<AccountDTO> getModerators(){
        List<AccoutDTOModerator> accoutDTOModerators = accountRepository.getModeratorsSQL(2L);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (int i = 0; i < accoutDTOModerators.size(); i++) {
            accountDTOS.add(new AccountDTO((long) accoutDTOModerators.get(i).getId(), accoutDTOModerators.get(i).getName(),
                    accoutDTOModerators.get(i).getEmail(), "MODERATOR"));
        }
        return accountDTOS;
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody AccountEntity account) {
        return new ResponseEntity<>(userService.saveDto(account), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam("idAccount") Long idAccount) {
        Optional<AccountEntity> optionalAccountEntity = accountRepository.findById(idAccount);
        if (optionalAccountEntity.isPresent())
        {
            AccountEntity account = optionalAccountEntity.get();
            accountRepository.delete(account);
            return ResponseEntity.ok("Удаление произошло успешно");
        }
        else {
            return ResponseEntity.badRequest().body("Данного пользователя не существует");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountEntity account) {
        try {
            if (account == null) {
                return ResponseEntity.badRequest().body("Нет данных для ввода");
            }
            if (account.getPassword().length() < 4) {
                return ResponseEntity.badRequest().body("Пароль не может быть меньше четырех символов");
            }
            if (accountRepository.existsAccountEntityByEmail(account.getEmail())) {
                return ResponseEntity.badRequest().body("Пользователь с данным Email уже существует");
            }
            RoleEntity role = roleRepository.findByName("MODERATOR");
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setName(account.getName());
            accountEntity.setEmail(account.getEmail());
            accountEntity.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
            accountEntity.setRole(role);
            accountRepository.save(accountEntity);
            return ResponseEntity.ok("Регистрация прошла успешно");
        }
        catch (Exception ex) {
            System.err.println("Ошибка создания нового пользователя: " + ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
