package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.verification.service.SendVerificationCodeCommand;
import gajeman.jagalchi.jagalchiserver.application.verification.service.ValidVerificationCodeCommand;
import gajeman.jagalchi.jagalchiserver.domain.verification.VerificationType;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.SendVerificationCodeRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class VerificationController {

    private final SendVerificationCodeCommand sendVerificationCodeCommand;
    private final ValidVerificationCodeCommand validVerificationCodeCommand;

    @PostMapping("/verification")
    public void sendSignupVerificationCode(
            @RequestBody SendVerificationCodeRequest request
    ){
        sendVerificationCodeCommand.sendVerificationCode(request, VerificationType.SIGN_UP);
    }

    @PostMapping("/auth/password-reset")
    public void sendPasswordResetVerificationCode(
            @RequestBody SendVerificationCodeRequest request
    ){
        sendVerificationCodeCommand.sendVerificationCode(request, VerificationType.UPDATE_PASSWORD);
    }

    @PatchMapping("/verification")
    public void validateSignupVerificationCode(
            @RequestBody VerifyRequest request
    ){
        validVerificationCodeCommand.validVerificationCode(request);
    }

    @PatchMapping("/auth/password-reset/verify")
    public void validatePasswordResetVerificationCode(
            @RequestBody VerifyRequest request
    ){
        validVerificationCodeCommand.validVerificationCode(request);
    }

}
