package gajeman.jagalchi.jagalchiserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private String profileUrl;

    private char text;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    private Users(String email, String password, String profileUrl, char text) {
        this.email = email;
        this.password = password;
        this.text = text;
        this.role = UserRole.STUDENT;
        this.profileUrl = profileUrl;
    }

    public Users from(String email, String password, String profileUrl, char text) {
        return Users.builder()
                .email(email)
                .password(password)
                .text(text)
                .profileUrl(profileUrl)
                .build();
    }

}
