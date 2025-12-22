package gajeman.jagalchi.jagalchiserver.application.verification.service;

import gajeman.jagalchi.jagalchiserver.application.verification.usecase.ValidVerificationCodeUseCase;
import gajeman.jagalchi.jagalchiserver.domain.verification.Verification;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.verification.VerificationRepository;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidVerificationCodeCommand implements ValidVerificationCodeUseCase {

    private final VerificationRepository verificationRepository;

    /*
    인증코드 인증용 메서드
     **/
    @Override
    @Transactional
    public void validVerificationCode(VerifyRequest request) {
        Verification verification = verificationRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("인증코드를 찾을 수 없습니다."));

        verification.verify(request.getCode());
    }

}
