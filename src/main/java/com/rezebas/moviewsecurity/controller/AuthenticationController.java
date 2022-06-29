package com.rezebas.moviewsecurity.controller;

import com.rezebas.moviewsecurity.dto.JwtAuthenticationDto;
import com.rezebas.moviewsecurity.dto.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.rezebas.moviewsecurity.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<TokenDto> gerarTokenJwt(

            @Valid @RequestBody JwtAuthenticationDto authenticationDto,
            BindingResult result)
            throws AuthenticationException {
        TokenDto response = new TokenDto();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.setError(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(),
                        authenticationDto.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                authenticationDto.getEmail());
        String token = jwtTokenUtil.obterToken(userDetails);
        response.setToken(token);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<TokenDto> gerarRefreshTokenJwt(
            HttpServletRequest request) {

        TokenDto response = new TokenDto();
        Optional<String> token = Optional.ofNullable(
                request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.setError("Token não informado.");
        } else if (!jwtTokenUtil.tokenValido(token.get())) {
            response.setError("Token inválido ou expirado.");
        }

        if (!response.getError().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        String refreshedToken = jwtTokenUtil.refreshToken(token.get());
        response.setToken(refreshedToken);

        return ResponseEntity.ok(response);
    }
}
