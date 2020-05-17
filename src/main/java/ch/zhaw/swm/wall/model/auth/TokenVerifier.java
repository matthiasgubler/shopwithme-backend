package ch.zhaw.swm.wall.model.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TokenVerifier {

    private static final String CLIENT_ID = "191866797331-1odd82r54nq1e4rllun9t8sjadl5hq6t.apps.googleusercontent.com";

    private final GoogleIdTokenVerifier verifier;

    public TokenVerifier() {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();
    }

    public GoogleIdTokenVerifier getVerifier() {
        return verifier;
    }

}
