package unq.edu.li.pdes.micultura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {

    private String email;
    private String token;
    private String firstname;
    private String lastname;
    private String role;
}
