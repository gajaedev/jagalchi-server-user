package gajeman.jagalchi.jagalchiserver.infrastructure.persistence.verification;

import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
