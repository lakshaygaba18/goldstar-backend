package goldstar_backend.security;

import goldstar_backend.entity.Owner;
import goldstar_backend.entity.Worker;
import goldstar_backend.repository.OwnerRepository;
import goldstar_backend.repository.WorkerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final WorkerRepository workerRepository;
    private final OwnerRepository ownerRepository; // NEW

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.contains("/login") || path.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String subject = jwtService.extractSubject(token);

        System.out.println("JWT Subject = " + subject);

        Owner owner = ownerRepository.findByEmail(subject).orElse(null);
        System.out.println("Owner = " + owner);
        // First, check if this token belongs to an OWNER (subject = email)
        Owner owner = ownerRepository.findByEmail(subject).orElse(null);
        if (owner != null) {
            request.setAttribute("ownerId", owner.getId());
            filterChain.doFilter(request, response);
            return;
        }

        // Otherwise, check if it's a WORKER token (subject = workerCode)
        Worker worker = workerRepository.findByWorkerCode(subject).orElse(null);

        if (worker == null || !worker.isActive()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access disabled or worker not found");
            return;
        }

        request.setAttribute("workerId", worker.getId());
        request.setAttribute("ownerId", worker.getOwner().getId());

        filterChain.doFilter(request, response);
    }
}