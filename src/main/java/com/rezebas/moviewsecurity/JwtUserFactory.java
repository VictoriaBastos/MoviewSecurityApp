package com.rezebas.moviewsecurity;

import com.rezebas.moviewsecurity.enums.ProfileEnum;
import com.rezebas.moviewsecurity.repositories.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getProfile()));

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(
            ProfileEnum profile) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profile.toString()));
        return authorities;
    }
}
