package gajeman.jagalchi.jagalchiserver.infrastructure.jwt.service;

import gajeman.jagalchi.jagalchiserver.domain.user.UserRole;
import gajeman.jagalchi.jagalchiserver.domain.user.Users;
import gajeman.jagalchi.jagalchiserver.infrastructure.jwt.domain.RefreshToken;
import gajeman.jagalchi.jagalchiserver.infrastructure.jwt.domain.TokenType;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.refreshToken.RefreshTokenRepository;
import gajeman.jagalchi.jagalchiserver.infrastructure.persistence.users.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsersRepository usersRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(Users user) {
        Long accessTokenExpiration = 60 * 60 * 1000L;

        return generateToken(user.getId(), user.getRole(), TokenType.ACCESS_TOKEN, accessTokenExpiration);
    }

    public String generateRefreshToken(Users user) {
        Long refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000L;

        String token = generateToken(user.getId(), user.getRole(), TokenType.REFRESH_TOKEN, refreshTokenExpiration);

        refreshTokenRepository.save(RefreshToken.from(user.getId(), token));

        return token;
    }

    public String generateToken(Long id, UserRole role, TokenType type, Long time) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("role", role.toString());
        claims.put("type", type.name());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = parseToken(refreshToken);

        String type = claims.get("type", String.class);
        if (!TokenType.REFRESH_TOKEN.name().equals(type)) {
            throw new IllegalArgumentException("올바른 토큰 타입이 아닙니다.");
        }

        Long id = claims.get("id", Long.class);
        RefreshToken token = refreshTokenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리프레쉬 토큰을 찾을 수 없습니다."));

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("올바른 토큰이 아닙니다.");
        }

        Users user = getUserById(id);

        return generateAccessToken(user);
    }


    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getType(String token) {
        return parseToken(token).get("type", String.class);
    }

    private Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

}
