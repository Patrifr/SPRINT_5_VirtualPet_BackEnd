package cat.itacademy.s05.t02.n01.service.serviceimpl;

import cat.itacademy.s05.t02.n01.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiration}")
    private long expirationTime;
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //El método extractAllClaims recibe un token JWT, verifica su firma utilizando una clave secreta,
    // y luego extrae y devuelve las reclamaciones (claims) contenidas en el cuerpo del token.
    // Este cuerpo es un objeto de tipo Claims, que contiene la información almacenada en el token
    // (como el usuario, los roles, la fecha de expiración, etc.).
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Coge la jwtSigninKey, la convierte en una cadena de bytes,
    // y luego la convierte en una key utilizando el algoritmo HMAC-SHA.
    // Esta clave es necesaria para firmar el JWT o verificar su firma
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
