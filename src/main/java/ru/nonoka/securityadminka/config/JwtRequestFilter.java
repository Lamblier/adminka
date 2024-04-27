package ru.nonoka.securityadminka.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nonoka.securityadminka.utils.JwtTokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            if (jwtToken.isBlank()) {
                log.debug("Токен не корректный");
                //response.sendError(HttpServletResponse.SC_FORBIDDEN, "Не корректный токен");
            } else {
                try {
                    username = jwtTokenUtils.getUsername(jwtToken);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()
                        );
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                } catch (ExpiredJwtException ex) {
                    log.debug("Не корректная дата");
                    //response.sendError(HttpServletResponse.SC_FORBIDDEN, "Не корректная дата");
                } catch (SignatureException e) {
                    log.debug("Подпись неправильная");
                    //response.sendError(HttpServletResponse.SC_FORBIDDEN, "Подпись неправильная");
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
