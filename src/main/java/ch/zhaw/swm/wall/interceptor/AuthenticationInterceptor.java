package ch.zhaw.swm.wall.interceptor;

import ch.zhaw.swm.wall.context.Context;
import ch.zhaw.swm.wall.context.LoggedInUser;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EMAIL_FIELD = "email";
    private static final String SUB_FIELD = "sub";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        parseUser(request).ifPresent(loggedInUser -> Context.getCurrentContext().setLoggedInUser(loggedInUser));
        return true;
    }

    private Optional<LoggedInUser> parseUser(HttpServletRequest request) {
        String authContent = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authContent != null && authContent.startsWith(TOKEN_PREFIX)) {
            authContent = "." + authContent.replace(TOKEN_PREFIX, "").split("\\.")[1] + ".";
            Jwt jwt = Jwts.parser().parse(authContent);
            if (jwt != null && jwt.getBody() != null) {
                DefaultClaims defaultClaims = (DefaultClaims) jwt.getBody();
                return Optional.of(new LoggedInUser((String) defaultClaims.get(SUB_FIELD), (String) defaultClaims.get(EMAIL_FIELD)));
            }
        }
        return Optional.of(LoggedInUser.newDefaultUser());
    }
}
