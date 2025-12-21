package gajeman.jagalchi.jagalchiserver.application.verification.usecase;

import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.VerifyRequest;

public interface ValidVerificationCodeUseCase {
    void validVerificationCode(VerifyRequest request);
}
