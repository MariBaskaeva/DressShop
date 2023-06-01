package ru.baskaeva.dressshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.product.ProductInfo;
import ru.baskaeva.dressshop.models.user.Role;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.repositories.product.ProductRepository;
import ru.baskaeva.dressshop.repositories.user.UserRepository;

@SpringBootApplication
public class DressShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DressShopApplication.class, args);
    }

//    @Bean
    public CommandLineRunner run(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder()
                    .name("user")
                    .surname("User")
                    .email("user")
                    .password(passwordEncoder.encode("user"))
                    .phoneNumber("777")
                    .telegram("@user")
                    .role(Role.ROLE_USER)
                    .build();
            User admin = User.builder()
                    .name("admin")
                    .surname("admin")
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .phoneNumber("666")
                    .telegram("@admin")
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(user);
            userRepository.save(admin);
        };
    }
}
