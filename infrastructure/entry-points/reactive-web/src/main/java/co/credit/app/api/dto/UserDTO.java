package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @JsonIgnore
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotNull(message = "El apellido es obligatorio")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    @JsonProperty("last_name")
    private String lastName;
    @NotNull(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    private String email;
    @NotNull(message = "El documento es obligatorio")
    @Size(min = 10, max = 15, message = "El documento debe tener entre 10 y 15 caracteres")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "El documento no es válido")
    private String document;
    @NotNull(message = "El teléfono es obligatorio")
    @Size(min = 10, max = 20, message = "El teléfono debe tener entre 10 y 20 caracteres")
    @Pattern(regexp = "^[0-9]{10,20}$", message = "El teléfono no es válido")
    private String phone;
    @NotNull(message = "El salario es obligatorio")
    @Min(value = 100000, message = "El salario debe ser al menos $100,000")
    @Max(value = 5000000, message = "El salario debe ser menor o igual a $5,000,000")
    private Double salary;
    @JsonProperty("role_id")
    private Long roleId;
}