package ru.nonoka.securityadminka.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nonoka.securityadminka.entity.Admin;
import ru.nonoka.securityadminka.repository.AdminRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminService implements UserDetailsService {
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Admin '%s' не найден", username)));
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.emptyList()
        );
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findById(username);
    }
}
