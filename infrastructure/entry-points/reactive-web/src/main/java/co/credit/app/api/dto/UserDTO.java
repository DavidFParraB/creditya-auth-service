package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDTO {

    private String name;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String document;
    private String phone;
    private Double salary;
    @JsonProperty( "role_id")
    private Long roleId;
}