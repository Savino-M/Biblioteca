package it.savinomarzano.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.savinomarzano.service.UtenteService;
import it.savinomarzano.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(JwtUtils tokenProvider, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter authFilter) throws Exception {

        return http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/registraUtente/**", "/user/login/**",
                                "/webBaseModel/ping", "/actuator/**", "/v3/api-docs/**", "/swagger-ui.html",
                                "/swagger-ui/**", "/auth/**", "/oauth2/**")
                        .permitAll())
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(new UtenteService(), passwordEncoder()))
                .oauth2Login(login -> login.successHandler(oAuth2LoginSuccessHandler))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;

    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }

    @Bean
    JwtAuthFilter jwtAuthFilter(JwtUtils jwtUtils, UtenteService utenteService) {
        return new JwtAuthFilter(jwtUtils, utenteService);
    }

}

@Slf4j
@Component
class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtService;

    private final UtenteService utenteService;

    public JwtAuthFilter(JwtUtils jwtService, UtenteService utenteService) {
        this.jwtService = jwtService;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                token = authHeader.substring(7);
                email = jwtService.extractEmail(token);

            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = utenteService.loadUserByUsername(email);

                if (jwtService.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

            }

        } catch (UsernameNotFoundException e) {

            log.warn("Rilevato tentativo di accesso fallito da " + request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Accesso non autorizzato");
            return;

        }

        filterChain.doFilter(request, response);

    }

}

@Component
class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils tokenProvider;

    public OAuth2LoginSuccessHandler(JwtUtils tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {

        // Genera il token JWT
        String token = tokenProvider.generateToken(authentication.getName());

        // Imposta il token JWT in un cookie
        Cookie jwtCookie = new Cookie("Token", token);
        jwtCookie.setHttpOnly(true); // Il cookie non sarà accessibile via JavaScript
        jwtCookie.setSecure(true); // Usa HTTPS in produzione
        jwtCookie.setPath("/"); // Il cookie sarà valido per l'intera applicazione
        jwtCookie.setMaxAge(60 * 60); // Imposta la durata del cookie a 1 ora
        response.addCookie(jwtCookie);

        // Reindirizza l'utente alla dashboard del frontend
        response.sendRedirect("http://localhost:4200/home");

    }

}
