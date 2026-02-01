package com.hermandad.asistencia.config;

import com.hermandad.asistencia.service.DetalleUsuarioService;
import com.hermandad.asistencia.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DetalleUsuarioService detalleUsuarioService;

    public JwtAuthenticationFilter(JwtService jwtService, DetalleUsuarioService detalleUsuarioService) {
        this.jwtService = jwtService;
        this.detalleUsuarioService = detalleUsuarioService;
    }

    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. Buscamos el token en la cabecera de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Si no hay token o no empieza con "Bearer ", dejamos pasar la petición (Spring Security la rechazará si es necesario más adelante)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraemos el token (quitamos la palabra "Bearer " que son 7 caracteres
        jwt = authHeader.substring(7);

        // 4. Extraemos el usuario del token
        userEmail = jwtService.extractUsername(jwt);

        // 5. Si encontramo el usuario y NO esta autenticado todavia en el contexto...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Buscamos sus datos en la base de daos
            UserDetails userDetails = this.detalleUsuarioService.loadUserByUsername(userEmail);

            // Verificamos si el token es valido

            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Creamos la "ficha de acceso" oficial de Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. ¡Lo dejamos pasar! Registramos que el usuario está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuamos con el siguiente filtro
        filterChain.doFilter(request, response);
    }

}
