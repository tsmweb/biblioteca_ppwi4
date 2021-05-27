package br.com.tsmweb.biblioteca.web.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tsmweb.biblioteca.models.service.exception.EmailCadastradoException;
import br.com.tsmweb.biblioteca.models.service.exception.EntidadeNaoCadastradaException;

@ControllerAdvice
public class BibliotecaExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problemType = ProblemType.ERRO_INTERNO;
		var detail = ex.getMessage();
		
		var problem = createBuilderProblem(status, problemType, detail)
							.addUserMessage("Erro interno do sistema")
							.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoCadastradaException.class)
	public ResponseEntity<?> handleEntidadeNaoCadastradaExecption(EntidadeNaoCadastradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problemType = ProblemType.REGISTRO_NAO_ENCONTRADO;
		var detail = ex.getMessage();
		
		var problem = createBuilderProblem(status, problemType, detail)
							.addUserMessage("Entidade não encontrada")
							.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EmailCadastradoException.class)
	public ResponseEntity<?> handleEmailExecption(EmailCadastradoException ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problemType = ProblemType.SERVICO_EMAIL;
		var detail = ex.getMessage();
		
		var problem = createBuilderProblem(status, problemType, detail)
							.addUserMessage("Erro na digitação do email")
							.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private Problem.Builder createBuilderProblem(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.addTimeStamp(LocalDateTime.now())
				.addStatus(status.value())
				.addType(problemType.getUri())
				.addTitle(problemType.getTitle())
				.addDetail(detail);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var problemType = ProblemType.DADOS_INVALIDOS;
		var detail = "Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.";
		
		var bindingResult = ex.getBindingResult();
		
		List<Fields> fields = bindingResult.getFieldErrors().stream().map(fieldError -> {
			var message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			var field = new Fields();
			field.setName(fieldError.getField());
			field.setUserMessage(message);
			return field;
		}).collect(Collectors.toList());
		
		var problem = createBuilderProblem(status, problemType, detail)
				.addUserMessage(detail)
				.addListFields(fields)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (body == null || body instanceof String) {
			body = Problem.builder()
					.addTimeStamp(LocalDateTime.now())
					.addStatus(status.value())
					.addTitle(status.getReasonPhrase())
					.addUserMessage("Ocorreu um erro interno inesperado no sistema. Tente novamente mais tarde.")
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}
