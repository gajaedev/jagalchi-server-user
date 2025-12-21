package gajeman.jagalchi.jagalchiserver.application.verification.usecase;

import gajeman.jagalchi.jagalchiserver.domain.verification.VerificationType;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.SendVerificationCodeRequest;

public interface SendVerificationCodeUseCase {
    void sendVerificationCode(SendVerificationCodeRequest request, VerificationType type);
}
