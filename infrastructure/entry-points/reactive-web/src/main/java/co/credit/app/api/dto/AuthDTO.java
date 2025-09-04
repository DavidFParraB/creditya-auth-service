package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthDTO {
  private String token;
  @JsonProperty("expiration_time")
  private Long expirationTime;
}
