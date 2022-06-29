package com.rezebas.moviewsecurity.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationDto {

    @NotEmpty(message = "Campo email obrigatorio.")
    @Email(message = "Email inválido.")
    private String email;

    @NotEmpty(message = "Campo senha obrigatorio.")
    private String senha;
}
