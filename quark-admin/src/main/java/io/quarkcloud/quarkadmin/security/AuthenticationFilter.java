package io.quarkcloud.quarkadmin.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.quarkcloud.quarkadmin.entity.UserEntity;
import io.quarkcloud.quarkadmin.service.UserService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.getUserId(token);
                UserEntity userEntity = userService.getById(userId);
                AuthUser authUser = new AuthUser();
                List<String> roles = null;
                if(userId.equals(1L)) {
                    roles = List.of("R_SUPER");
                }
                authUser.setId(userEntity.getId());
                authUser.setUsername(userEntity.getUsername());
                authUser.setNickname(userEntity.getNickname());
                authUser.setAvatar(userEntity.getAvatar());
                authUser.setEmail(userEntity.getEmail());
                authUser.setPhone(userEntity.getPhone());
                authUser.setRoles(roles);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        authUser, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}