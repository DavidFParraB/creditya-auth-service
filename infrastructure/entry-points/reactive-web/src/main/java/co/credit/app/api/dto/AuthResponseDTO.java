package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponseDTO {
  private String token;
  @JsonProperty("expiration_time")
  private Long expirationTime;
}
