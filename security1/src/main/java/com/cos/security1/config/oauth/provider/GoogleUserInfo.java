package com.cos.security1.config.oauth.provider;

import java.util.Map;
import java.util.Objects;

public class GoogleUserInfo implements Oauth2UserInfo{

    private Map<String, Object> attributes;

    public  GoogleUserInfo(Map<String,Object>attributes){
        this.attributes=attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return (String) attributes.get("name") ;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return "google";
    }
}
