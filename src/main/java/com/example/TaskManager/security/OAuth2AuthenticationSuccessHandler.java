package com.example.TaskManager.security;


import com.example.TaskManager.model.User;
import com.example.TaskManager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final @Lazy UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        Map<String, Object> attributes = oauthUser.getAttributes();

        User user = userService.processOAuthUser(
                email,
                (String) attributes.get("given_name"),
                (String) attributes.get("family_name")
        );

        String jwt = jwtUtils.generateToken(String.valueOf(user));
        URI redirectUri = buildRedirectUri(jwt);

        response.setHeader("Set-Cookie", buildSecureCookie(jwt));
        getRedirectStrategy().sendRedirect(request, response, redirectUri.toString());
    }

    private URI buildRedirectUri(String token) {
        return URI.create("/dashboard?token=" + token);
    }

    private String buildSecureCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .sameSite("Lax")
                .build().toString();
    }
}
