package PF.SerratecFlix.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request){
        List<String> erros = new ArrayList<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            erros.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ErroResposta erroResposta = new ErroResposta(status.value(),
                "Existem campos inválidos.", LocalDateTime.now(), erros);

        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErroResposta> handleBadRequest(BadRequestException ex){
        ErroResposta erro = new ErroResposta(HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos.", LocalDateTime.now(), List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResposta> handleNotFound(ResourceNotFoundException ex){
        ErroResposta erro = new ErroResposta(HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado.", LocalDateTime.now(), List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public  ResponseEntity<ErroResposta> handleConflict(DuplicateEntryException ex){
        ErroResposta erro = new ErroResposta(HttpStatus.CONFLICT.value(),
                "Conflito.", LocalDateTime.now(), List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErroResposta> handleUnauthorized(UnauthorizedException ex){
        ErroResposta erro = new ErroResposta(HttpStatus.UNAUTHORIZED.value(),
                "Não autorizado.", LocalDateTime.now(), List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
}

