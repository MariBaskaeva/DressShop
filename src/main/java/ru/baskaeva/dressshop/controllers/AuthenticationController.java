package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.baskaeva.dressshop.config.JwtTokenUtil;
import ru.baskaeva.dressshop.dto.user.CredentialDTO;
import ru.baskaeva.dressshop.dto.user.TokenDTO;
import ru.baskaeva.dressshop.models.user.Role;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.repositories.user.UserRepository;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager manager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;
    private final UserRepository userRepository;

    @GetMapping("/role")
    public Collection<? extends GrantedAuthority> getRole(@AuthenticationPrincipal User user){
        return user == null ? null : user.getAuthorities();
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody CredentialDTO credential) {
        Authentication authentication;
        try {
            authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getLogin(), credential.getPassword()));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "400 - bad credentials", ex
            );
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenDTO(tokenUtil.generateAccessToken(userDetailsService.loadUserByUsername(credential.getLogin())));
    }

    @PostMapping("/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/register")
    public TokenDTO register(@RequestBody User user){
        String password = user.getPassword();
        // TODO: 21.04.2023 Инжектировать в класс PasswordEncoder,а не создавать его тут
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return login(new CredentialDTO(user.getEmail(), password));
    }
}
