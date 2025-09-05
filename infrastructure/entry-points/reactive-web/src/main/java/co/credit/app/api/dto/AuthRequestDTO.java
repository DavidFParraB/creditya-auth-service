package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDTO {
  @NotBlank(message = "Ingrese un nombre de usuario válido")
  @NotNull(message = "Ingrese un nombre de usuario válido")
  @JsonProperty("user")
  private String username;
  @NotBlank(message = "Ingrese un password")
  @NotNull(message = "Ingrese un password")
  private String password;
}
