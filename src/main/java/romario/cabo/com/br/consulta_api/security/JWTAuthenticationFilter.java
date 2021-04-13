package romario.cabo.com.br.consulta_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import romario.cabo.com.br.consulta_api.exception.UnauthorizedException;
import romario.cabo.com.br.consulta_api.service.form.AuthenticateForm;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws UnauthorizedException {
        try {
            AuthenticateForm authenticateForm = new ObjectMapper()
                    .readValue(req.getInputStream(), AuthenticateForm.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticateForm.getEmail(), authenticateForm.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String email = ((UserSS) auth.getPrincipal()).getEmail();
        String token = jwtUtil.generateToken(email);
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("access-control-expose-headers", "Authorization");

        UserSS user = (UserSS) auth.getPrincipal();

        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(userJson(user));
        out.flush();
    }

    private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\"}";
        }
    }

    private String userJson(UserSS user) {
        return "{"
                + "\"id\": " + user.getId() + ", "
                + "\"name\": \"" + user.getUsername() + "\", "
                + "\"email\": \"" + user.getEmail() + "\", "
                + "\"profile\": \"" + formatterAuthorities(user.getAuthorities().toString()) + "\" " +
                "}";
    }

    private String formatterAuthorities(String authorities) {
        String[] aux = authorities.split("_");
        return aux[1].replace("]", "");
    }
}

