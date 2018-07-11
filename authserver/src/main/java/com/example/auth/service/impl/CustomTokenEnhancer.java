package com.example.auth.service.impl;

import com.example.auth.entity.User;
import com.example.auth.service.IUserService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.util.StringUtils;

import java.util.*;

public class CustomTokenEnhancer implements TokenEnhancer {

    private IUserService userService;

    public CustomTokenEnhancer(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        String username = authentication.getOAuth2Request().getRequestParameters().get("username");
        if (!authentication.isClientOnly()) {
            User user = (User) authentication.getUserAuthentication().getPrincipal();

            Integer userId = user.getId();/**/
            String userEmail = user.getEmail();
            additionalInfo.put("user_id", userId);
            additionalInfo.put("user_email", user.getEmail());
            additionalInfo.put("user_name", user.getLoginName());
            additionalInfo.put("name", StringUtils.isEmpty(userEmail)?"":userEmail.substring(0,userEmail.indexOf("@")));
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

            Set<String> allScopes = getClientScopes(accessToken);
            //List<String> userScope = retrieveScope(userId);
            //allScopes.addAll(userScope);

            ((DefaultOAuth2AccessToken) accessToken).setScope(allScopes);
        }
        return accessToken;
    }

    private Set<String> getClientScopes(OAuth2AccessToken accessToken) {

        Set<String> res = new HashSet<String>();

        Set<String> scopes = accessToken.getScope();
        for (String scope : scopes) {
            res.add(scope);
        }
        return res;
    }
}
