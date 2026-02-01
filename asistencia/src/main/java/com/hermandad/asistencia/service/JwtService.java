package com.hermandad.asistencia.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // ESTA ES LA CLAVE SECRETA (La "Firma del Dueño")
    // En producción, esto de estar en application.properties y ser MUY largo.
    // Aquí usamos una generada aleatoriamente para que funcione con HS256.
     private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

     // 1. Obtener el nombre de usuario del token (Leer el sello)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Generar el token (Poner el sello)
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails ) {
        return Jwts
                .builder() // 1. Iniciamos el constructor (La hoja en blanco)
                .setClaims(extraClaims) // 2. Agregamos datos extra si los hay (ej. "esAdmin: true")
                .setSubject(userDetails.getUsername()) // 3. Ponemos el nombre del usuario (El "Dueño" del token)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 4. Ponemos la fecha de hoy (Cuándo se creó)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 5. Ponemos fecha de vencimiento (24 horas después)
                .signWith(getSignInkey(), SignatureAlgorithm.HS256) // 6. ¡IMPORTANTE! Firmamos con nuestra CLAVE SECRETA
                .compact(); // 7. Empaquetamos todo en un String final
    }

    // 3. Validar si el token es correcto (Verificar el sello)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // --- Herramientas Auxiliares ---

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver ) {
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
