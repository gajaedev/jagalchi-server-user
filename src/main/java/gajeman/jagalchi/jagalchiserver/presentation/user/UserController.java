package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.user.service.UpdateProfileCommand;
import gajeman.jagalchi.jagalchiserver.application.user.usecase.QueryUserByNameUseCase;
import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import gajeman.jagalchi.jagalchiserver.global.response.MessageResponse;
import gajeman.jagalchi.jagalchiserver.presentation.user.request.UpdateProfileRequest;
import gajeman.jagalchi.jagalchiserver.presentation.user.response.QueryUserResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UpdateProfileCommand updateProfileCommand;
    private final QueryUserByNameUseCase queryUserByNameUseCase;

    /**
     * 프로필 업데이트 메서드
     * @param user 유저 식별자
     * @param request 프로필 이미지 url, 자기소개, 링크를 담은 dto
     */
    @PatchMapping("/profile")
    public MessageResponse updateProfile(
            @RequestBody UpdateProfileRequest request,
            @Parameter(hidden = true)
            @AuthenticationPrincipal Users user
    ) {
        updateProfileCommand.updateProfile(user, request);

        return  MessageResponse.from("프로필이 성공적으로 수정되었습니다.");
    }

    /**
     * 프로필 업데이트 메서드
     * @param name 검색어
     * @param user 유저(null 가능)
     */
    @GetMapping
    public QueryUserResponse getUserByName(
            @RequestParam("name") String name,
            @Parameter(hidden = true)
            @AuthenticationPrincipal(errorOnInvalidType = false) Users user
    ) {
        return queryUserByNameUseCase.getUserByName(name, user);
    }

    /**
     * 현재 로그인한 사용자 조회 메서드
     * @param user 현재 로그인한 사용자
     */
    @GetMapping("/me")
    public QueryUserResponse getCurrentUser(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Users user
    ) {
        return queryUserByNameUseCase.getCurrentUser(user);
    }

}
