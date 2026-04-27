package com.jaeyoung.portfolio.service;

import com.jaeyoung.portfolio.config.JwtTokenProvider;
import com.jaeyoung.portfolio.domain.Member;
import com.jaeyoung.portfolio.dto.LoginRequest;
import com.jaeyoung.portfolio.dto.LoginResponse;
import com.jaeyoung.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername());
        return LoginResponse.builder()
                .accessToken(token)
                .username(member.getUsername())
                .build();
    }
}