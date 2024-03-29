package com.example.trains.authorization.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.trains.authorization.entities.AccountEntity;
import com.example.trains.authorization.repositories.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.trains.authorization.service.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    AccountRepository accountRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager,
                                  AccountRepository accountRepository) {
        super(authManager);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {

                AccountEntity account = accountRepository.findByEmail(user);

                if (account == null) {
                    throw new BadCredentialsException("Не существует: "+account);
                }


                UserDetails principal = User.builder()
                        .username(account.getEmail())
                        .password(account.getPassword())
                        .roles(account.getRole().getName())
                        .build();

                return new UsernamePasswordAuthenticationToken(user, null, principal.getAuthorities());
            }

            return null;
        }

        return null;
    }
}
