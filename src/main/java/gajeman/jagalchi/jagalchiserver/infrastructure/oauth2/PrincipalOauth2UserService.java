package gajeman.jagalchi.jagalchiserver.infrastructure.oauth2;

import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String email = getEmail(registrationId, oauth2User);
        String name = getName(registrationId, oauth2User);
        String password = "OAuth2";

        Users user = userRepository.findByEmail(email)
                .orElseGet(() ->
                        userRepository.save(
                                Users.builder()
                                        .email(email)
                                        .name(name)
                                        .password(password)
                                        .build()
                        )
                );

        return new PrincipalDetails(user, oauth2User.getAttributes());
    }

    private String getEmail(String registrationId, OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");

        if (email == null && "github".equals(registrationId)) {
            String login = oauth2User.getAttribute("login");
            email = login + "@users.noreply.github.com";
        }

        return email;
    }

    private String getName(String registrationId, OAuth2User oauth2User) {
        String name = oauth2User.getAttribute("name");

        if (name == null && "github".equals(registrationId)) {
            name = oauth2User.getAttribute("login");
        }

        return name;
    }
}