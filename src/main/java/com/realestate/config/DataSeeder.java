package com.realestate.config;

import com.realestate.entity.*;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        User admin = userRepository.save(User.builder()
                .fullName("Admin User")
                .email("admin@realestate.com")
                .password(passwordEncoder.encode("admin123"))
                .phone("9999999999")
                .role(Role.ADMIN)
                .enabled(true)
                .build());

        User agent1 = userRepository.save(User.builder()
                .fullName("Rahul Sharma")
                .email("agent@realestate.com")
                .password(passwordEncoder.encode("agent123"))
                .phone("9888888888")
                .role(Role.AGENT)
                .enabled(true)
                .build());

        User customer1 = userRepository.save(User.builder()
                .fullName("Priya Nair")
                .email("customer@realestate.com")
                .password(passwordEncoder.encode("customer123"))
                .phone("9777777777")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build());

        propertyRepository.save(Property.builder()
                .title("Spacious 3BHK Apartment")
                .description("Modern 3BHK apartment with park view, modular kitchen, and covered parking.")
                .type(PropertyType.RESIDENTIAL)
                .purpose(PropertyPurpose.SALE)
                .price(BigDecimal.valueOf(8500000))
                .location("Whitefield")
                .city("Bangalore")
                .bedrooms(3)
                .bathrooms(2)
                .areaSqft(1450.0)
                .status(PropertyStatus.AVAILABLE)
                .imageUrl("https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=600")
                .agent(agent1)
                .build());

        propertyRepository.save(Property.builder()
                .title("Commercial Office Space")
                .description("Premium office space in a prime business district, ready to move in.")
                .type(PropertyType.COMMERCIAL)
                .purpose(PropertyPurpose.RENT)
                .price(BigDecimal.valueOf(75000))
                .location("MG Road")
                .city("Bangalore")
                .bedrooms(0)
                .bathrooms(2)
                .areaSqft(2200.0)
                .status(PropertyStatus.AVAILABLE)
                .imageUrl("https://images.unsplash.com/photo-1497366216548-37526070297c?w=600")
                .agent(agent1)
                .build());

        propertyRepository.save(Property.builder()
                .title("Cozy 2BHK Independent House")
                .description("Independent house with small garden, ideal for a small family.")
                .type(PropertyType.RESIDENTIAL)
                .purpose(PropertyPurpose.RENT)
                .price(BigDecimal.valueOf(28000))
                .location("Indiranagar")
                .city("Bangalore")
                .bedrooms(2)
                .bathrooms(2)
                .areaSqft(1100.0)
                .status(PropertyStatus.AVAILABLE)
                .imageUrl("https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=600")
                .agent(agent1)
                .build());

        propertyRepository.save(Property.builder()
                .title("Residential Plot")
                .description("DTCP approved residential plot, close to highway access.")
                .type(PropertyType.LAND)
                .purpose(PropertyPurpose.SALE)
                .price(BigDecimal.valueOf(3200000))
                .location("Sarjapur")
                .city("Bangalore")
                .bedrooms(0)
                .bathrooms(0)
                .areaSqft(2400.0)
                .status(PropertyStatus.AVAILABLE)
                .imageUrl("https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=600")
                .agent(agent1)
                .build());

        System.out.println("=================================================");
        System.out.println(" Demo data loaded. Login credentials:");
        System.out.println(" ADMIN    -> admin@realestate.com / admin123");
        System.out.println(" AGENT    -> agent@realestate.com / agent123");
        System.out.println(" CUSTOMER -> customer@realestate.com / customer123");
        System.out.println("=================================================");
    }
}
