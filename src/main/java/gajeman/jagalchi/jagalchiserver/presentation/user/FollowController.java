package gajeman.jagalchi.jagalchiserver.presentation.user;

import gajeman.jagalchi.jagalchiserver.application.follow.service.ToggleFollowCommand;
import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import gajeman.jagalchi.jagalchiserver.presentation.user.dto.request.FollowToggleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class FollowController {

    private final ToggleFollowCommand toggleFollowCommand;

    @PatchMapping("/{name}/follow")
    public void toggleFollow(
            @PathVariable String name,
            @RequestBody FollowToggleRequest request,
            @AuthenticationPrincipal Users user
    ) {
        toggleFollowCommand.toggleFollowing(
                user.getId(),
                name,
                request.getToggle()
        );
    }

}
