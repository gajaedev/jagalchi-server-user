package gajeman.jagalchi.jagalchiserver.infrastructure.oauth2;

import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provideId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String password = "OAuth2";

        Optional<Users> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            Users newUser = Users.builder()
                    .email(email)
                    .name(name)
                    .password(password)
                    .build();

            userRepository.save(newUser);
            return new PrincipalDetails(newUser, oauth2User.getAttributes());
        } else {
            return new PrincipalDetails(user.get(), oauth2User.getAttributes());
        }
    }
}
