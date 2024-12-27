package com.example.security.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.model.Users;
import com.example.security.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public PrincipalOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 회원가입 강제 진행
        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
        String providerId = oauth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String email = oauth2User.getAttribute("email");
        String role = "USER";

        // BCryptPasswordEncoder 인스턴스 필요 시 직접 생성하거나 외부에서 전달
        String password = new BCryptPasswordEncoder().encode("default-password");

        Users userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            System.out.println("구글로그인이 최초입니다다");
            userEntity = Users.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println("구글로그인을 햇습니다 자동로그인이 되엇습니다");
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}
