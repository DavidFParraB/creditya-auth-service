package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthRequestDTO {
  @JsonProperty("user")
  private String username;
  private String password;
}
