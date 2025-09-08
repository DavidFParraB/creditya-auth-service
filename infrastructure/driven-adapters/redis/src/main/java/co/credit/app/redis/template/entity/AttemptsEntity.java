package co.credit.app.redis.template.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttemptsEntity {
  @JsonProperty("nro_attempts")
  private Integer nroAttempts;
}
