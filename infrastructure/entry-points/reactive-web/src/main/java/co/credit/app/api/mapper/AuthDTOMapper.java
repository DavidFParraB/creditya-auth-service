package co.credit.app.api.mapper;

import co.credit.app.api.dto.AuthDTO;
import co.credit.app.api.dto.AuthResponseDTO;
import co.credit.app.model.auth.Auth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthDTOMapper {
  AuthResponseDTO toResponse(Auth auth);
}
