package ch.zhaw.swm.wall.config;

import ch.zhaw.swm.wall.interceptor.AuthenticationInterceptor;
import ch.zhaw.swm.wall.model.auth.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private TokenVerifier tokenVerifier;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(tokenVerifier));
    }
}
