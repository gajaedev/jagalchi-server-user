package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.verification.service.SendVerificationCodeService;
import gajeman.jagalchi.jagalchiserver.application.verification.service.ValidVerificationCodeService;
import gajeman.jagalchi.jagalchiserver.domain.verification.VerificationType;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.SendVerificationCodeRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class VerificationController {

    private final SendVerificationCodeService sendVerificationCodeService;
    private final ValidVerificationCodeService validVerificationCodeService;

    @PostMapping("/verification")
    public void sendSignupVerificationCode(
            @RequestBody SendVerificationCodeRequest request
    ){
        sendVerificationCodeService.sendVerificationCode(request, VerificationType.SIGN_UP);
    }

    @PostMapping("/auth/password-reset")
    public void sendPasswordResetVerificationCode(
            @RequestBody SendVerificationCodeRequest request
    ){
        sendVerificationCodeService.sendVerificationCode(request, VerificationType.UPDATE_PASSWORD);
    }

    @PatchMapping("/verification")
    public void validateSignupVerificationCode(
            @RequestBody VerifyRequest request
    ){
        validVerificationCodeService.validVerificationCode(request);
    }

    @PatchMapping("/auth/password-reset/verify")
    public void validatePasswordResetVerificationCode(
            @RequestBody VerifyRequest request
    ){
        validVerificationCodeService.validVerificationCode(request);
    }

}
