package com.example.trains.authorization.controllers;

import com.example.trains.authorization.dto.AccountDTO;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/services/controller/user")
@AllArgsConstructor
public class MainController {
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
                    accountEntity.getRoles()
                            .stream()
                            .map(RoleEntity::getName)
                            .collect(Collectors.toList()));
        }
        else return null;
    }


    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody AccountEntity account) {
        return new ResponseEntity<>(userService.saveDto(account), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AccountEntity account) {
        if (account == null) {
            return ResponseEntity.badRequest().body("Error: нет данных");
        }
        if (accountRepository.existsAccountEntityByEmail(account.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email уже существует");
        }
        ArrayList<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("MODERATOR"));
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName(account.getName());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountEntity.setRoles(roles);
        accountRepository.save(accountEntity);
        return ResponseEntity.ok("Регистрация прошла успешно");
    }
}
