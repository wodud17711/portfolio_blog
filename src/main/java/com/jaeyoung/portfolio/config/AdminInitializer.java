package com.jaeyoung.portfolio.config;

import com.jaeyoung.portfolio.domain.Member;
import com.jaeyoung.portfolio.domain.MemberRole;
import com.jaeyoung.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (memberRepository.existsByUsername(adminUsername)) {
            return;
        }

        Member admin = Member.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role(MemberRole.ADMIN)
                .build();
        memberRepository.save(admin);
        System.out.println("✅ Admin account created: " + adminUsername);
    }
}