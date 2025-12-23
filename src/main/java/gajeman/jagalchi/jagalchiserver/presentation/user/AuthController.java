package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.auth.result.LoginResult;
import gajeman.jagalchi.jagalchiserver.application.auth.service.ChangePasswordCommand;
import gajeman.jagalchi.jagalchiserver.application.auth.service.LoginCommand;
import gajeman.jagalchi.jagalchiserver.application.auth.service.SignUpCommand;
import gajeman.jagalchi.jagalchiserver.infrastructure.cookie.CookieUtil;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.ChangePasswordRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.LoginRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.SignUpRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.response.LoginResponse;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.response.SignUpResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final SignUpCommand signUpCommand;
    private final ChangePasswordCommand changePasswordCommand;
    private final LoginCommand loginCommand;
    private final CookieUtil cookieUtil;

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

    /**
     * 비밀번호 변경 메서드
     * @param request 이메일, 신규비밀번호를 담은 DTO
     */
    @PatchMapping("/auth/password-reset")
    public void changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        changePasswordCommand.changePassword(request);
    }

    /**
     * 로그인 변경 메서드
     * @param request 로그인용 이메일, 비밀번호를 담은 DTO
     */
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        LoginResult result = loginCommand.login(request);

        LoginResponse response = LoginResponse.from(result.accessToken());

        cookieUtil.addRefreshToken(httpServletResponse, result.refreshToken(), true);

        return ResponseEntity.ok(response);
    }

}
