package ch.zhaw.swm.wall.interceptor;

import ch.zhaw.swm.wall.context.Context;
import ch.zhaw.swm.wall.context.LoggedInUser;
import ch.zhaw.swm.wall.controller.exception.NotAuthenticatedException;
import ch.zhaw.swm.wall.model.auth.TokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenVerifier tokenVerifier;

    public AuthenticationInterceptor(TokenVerifier tokenVerifier) {
        this.tokenVerifier = tokenVerifier;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String idTokenString = getIdTokenString(request);

        parseUser(idTokenString).ifPresent(loggedInUser -> Context.getCurrentContext().setLoggedInUser(loggedInUser));
        return true;
    }

    private String getIdTokenString(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader.replace(TOKEN_PREFIX, "");
    }

    private GoogleIdToken authenticate(String authContent) {
        GoogleIdToken idToken;
        try {
            idToken = tokenVerifier.getVerifier().verify(authContent);
        } catch (GeneralSecurityException | IOException e) {
            throw new NotAuthenticatedException(e);
        }
        return idToken;
    }

    private Optional<LoggedInUser> parseUser(String idTokenString) {
        if (!idTokenString.isEmpty()) {
            GoogleIdToken idToken = authenticate(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String name = ((String) payload.get("given_name")).concat(" " + ((String) payload.get("family_name")).substring(0, 1));
                String picture = (String) payload.get("picture");
                return Optional.of(new LoggedInUser(payload.getSubject(), payload.getEmail(), name, picture));
            }
        }
        return Optional.of(LoggedInUser.newDefaultUser());
    }
}
