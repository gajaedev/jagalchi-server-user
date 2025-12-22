package gajeman.jagalchi.jagalchiserver.application.auth.usecase;

import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.ChangePasswordRequest;

public interface ChangePasswordUseCase {
    void changePassword(ChangePasswordRequest request);
}
