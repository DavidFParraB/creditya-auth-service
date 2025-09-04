package co.credit.app.api.mapper;

import co.credit.app.api.dto.AuthRequestDTO;
import co.credit.app.api.dto.AuthResponseDTO;
import co.credit.app.api.dto.UserDTO;
import co.credit.app.model.auth.Auth;
import co.credit.app.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthRequestDTOMapper {
  Auth toModel( AuthRequestDTO authRequestDTO);
}
