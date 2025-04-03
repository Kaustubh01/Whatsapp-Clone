package com.kaustubh.whatsappclone.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import lombok.NonNull;

public class KeycloakJwtAuthenticationConverter implements org.springframework.core.convert.converter.Converter<Jwt, AbstractAuthenticationToken>{



    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt arg0) {

        return new JwtAuthenticationToken(arg0,
        Stream.concat(new JwtGrantedAuthoritiesConverter().convert(arg0).stream(), extractResourceRoles(arg0).stream()).collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt arg0) {
        var resoureceAccess = new HashMap<>(arg0.getClaim("resource_access"));
        var eternal = (Map<String, List<String>>)resoureceAccess.get("account");
        var roles = eternal.get("roles");
        return roles.stream()
            .map(role->new SimpleGrantedAuthority("ROLE_"+role.replace("-", "_"))).collect(Collectors.toSet());
    }

}
