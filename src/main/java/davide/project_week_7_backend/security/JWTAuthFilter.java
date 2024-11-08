package davide.project_week_7_backend.security;

import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.exceptions.UnauthorizedException;
import davide.project_week_7_backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath()) ||
              new AntPathMatcher().match("/v3/api-docs/**", request.getServletPath()) ||
              new AntPathMatcher().match("/v3/api-docs.yaml", request.getServletPath()) ||
              new AntPathMatcher().match("/swagger-ui/**", request.getServletPath()) ||
              new AntPathMatcher().match("/swagger-ui.html", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (shouldNotFilter(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            throw new UnauthorizedException("Please provide a valid token in the authorization header");
        }

        String token = authHeader.substring(7);
        String id = jwtTools.extractIdFromToken(token);
        User currentUser = userService.findById(UUID.fromString(id));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
