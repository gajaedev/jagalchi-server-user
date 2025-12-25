package gajeman.jagalchi.jagalchiserver.application.auth.usecase;

public interface DeleteAccountUseCase {
    /**
     * 비밀번호 변경 메서드
     * @param usersId 유저 Id
     */
    void deleteAccount(Long usersId);
}
