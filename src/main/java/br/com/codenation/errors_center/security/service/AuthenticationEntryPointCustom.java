package br.com.codenation.errors_center.security.service;

import br.com.codenation.errors_center.infrastructure.translate.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Authentication entry point custom.
 */
@Component
public class AuthenticationEntryPointCustom implements AuthenticationEntryPoint {

    @Autowired
    private Translator translator;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, translator.toLocale("user.auth.unauthorized_user"));
    }

}
