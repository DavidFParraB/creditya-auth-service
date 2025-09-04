package co.credit.app.api.mapper;

import co.credit.app.api.dto.AuthResponseDTO;
import co.credit.app.model.auth.Auth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthResponseDTOMapper {
  AuthResponseDTO toResponse(Auth auth);
}
