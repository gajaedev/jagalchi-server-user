package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.auth.service.SignUpCommand;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.SignUpRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final SignUpCommand signUpCommand;

    /**
     * 회원가입 메서드
     * @param request 이메일, 비밀번호, 이름을 담은 DTO
     */
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpCommand.signUp(request));
    }

}
