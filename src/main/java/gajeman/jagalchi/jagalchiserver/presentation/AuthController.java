package gajeman.jagalchi.jagalchiserver.presentation;

import gajeman.jagalchi.jagalchiserver.application.auth.service.ChangePasswordCommand;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final ChangePasswordCommand changePasswordCommand;

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

}
