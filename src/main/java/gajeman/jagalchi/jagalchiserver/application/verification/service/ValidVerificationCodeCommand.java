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

    /**
    사용자 작성한 인증코드가 올바른지 검증
    * @param request 인증코드와 이메일 정보를 담은 요청 DTO
     */
    @Override
    @Transactional
    public void validVerificationCode(VerifyRequest request) {
        Verification verification = verificationRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("인증코드를 찾을 수 없습니다."));

        verification.verify(request.getCode());
    }

}
