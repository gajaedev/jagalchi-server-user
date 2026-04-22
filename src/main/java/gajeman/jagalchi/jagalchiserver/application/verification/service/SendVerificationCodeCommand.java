package gajeman.jagalchi.jagalchiserver.application.verification.service;

import gajeman.jagalchi.jagalchiserver.application.verification.usecase.SendVerificationCodeUseCase;
import gajeman.jagalchi.jagalchiserver.domain.verification.Verification;
import gajeman.jagalchi.jagalchiserver.domain.verification.VerificationType;
import gajeman.jagalchi.jagalchiserver.infrastructure.mail.MailUtil;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.verification.VerificationRepository;
import gajeman.jagalchi.jagalchiserver.presentation.auth.dto.request.SendVerificationCodeRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendVerificationCodeCommand implements SendVerificationCodeUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendVerificationCodeCommand.class);

    private final MailUtil mailUtil;
    private final VerificationRepository verificationRepository;

    @Override
    public void sendVerificationCode(SendVerificationCodeRequest request, VerificationType type){
        Verification verification = Verification.from(request.getEmail(), type);
        verificationRepository.save(verification);
        try {
            mailUtil.sendMimeMessage(verification.getEmail(), verification.getCode());
        } catch (RuntimeException e) {
            log.warn("메일 전송에 실패했지만 인증코드는 저장했습니다. email={}", verification.getEmail(), e);
        }
    }

}
