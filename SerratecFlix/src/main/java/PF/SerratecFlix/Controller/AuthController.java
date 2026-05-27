package PF.SerratecFlix.Controller;
 
import PF.SerratecFlix.DTO.Request.LoginDTORequest;
import PF.SerratecFlix.DTO.Response.LoginDTOResponse;
import PF.SerratecFlix.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
@RequiredArgsConstructor
public class AuthController {
 
    private final AuthService authService;
 
    @PostMapping("/login")
    @Operation(summary = "Realizar login e obter token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Logado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<LoginDTOResponse> login(@Valid @RequestBody LoginDTORequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}