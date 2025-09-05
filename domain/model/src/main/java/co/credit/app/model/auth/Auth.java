package co.credit.app.model.auth;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Auth {

  private String username;
  private String password;
  private Long role;
  private String token;
  private Long expirationTime;
}
