package co.credit.app.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.credit.app.api.dto.UserDTO;
import co.credit.app.model.user.User;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    UserDTO toResponse(User user);

    List<UserDTO> toResponseList (List<User> users);

    User toModel(UserDTO userDTO);

}