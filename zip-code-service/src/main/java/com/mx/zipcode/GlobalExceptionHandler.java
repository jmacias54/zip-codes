package com.mx.zipcode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mx.zipcode.exception.BadRequestException;
import com.mx.zipcode.exception.UnknownResourceException;
import com.mx.zipcode.exception.ValidacionException;
import com.mx.zipcode.model.ResponseError;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERROR_BUSINESS_EXCEPTION = "ERROR_BUSINESS_EXCEPTION";
	

	/**
	 * "The resource does not exist. Please try with other parameter";
	 *
	 * @param ex
	 * @param request
	 * @return ResponseError
	 */
	
	@ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<ResponseError> badRequestException(Exception ex, WebRequest request) {
        ResponseError responseError = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
                ERROR_BUSINESS_EXCEPTION, new ArrayList<String>());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler({ UnknownResourceException.class })
	public ResponseEntity<ResponseError> handleResourceNotFound(Exception ex, WebRequest request) {
		ResponseError responseError = createResponseError(ex, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString(),
				ERROR_BUSINESS_EXCEPTION, new ArrayList<String>());
		return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
	}
	

	
	@ExceptionHandler(ValidacionException.class)
	public ResponseEntity<ResponseError> validacionException(final Exception ex, final WebRequest request ) {
		imprimeError(ex);
		ResponseError responseError = createResponseError(ex, HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), ERROR_BUSINESS_EXCEPTION, null);

		return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ SQLException.class, BadSqlGrammarException.class, DataIntegrityViolationException.class,
			DataAccessException.class, IncorrectResultSizeDataAccessException.class})
	public ResponseEntity<ResponseError> handleSQLException(final Exception ex, final WebRequest request) {
		log.error("SQLException Occured:: URL " + request.getContextPath());
		log.error("Error detail:: ", ex);
		ResponseError responseError = createResponseError(ex, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString(),
				ERROR_BUSINESS_EXCEPTION, new ArrayList<String>());
		return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle default exception. (500)
	 *
	 * @param ex the ex
	 * @return the string
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseError> handleDefaultException(final Exception ex, final WebRequest request) {
		imprimeError(ex);
		ResponseError responseError = createResponseError(ex, HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), ERROR_BUSINESS_EXCEPTION, null);

		return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ResponseError body = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
				ERROR_BUSINESS_EXCEPTION, errors);

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		imprimeError(ex);
		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ResponseError body = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
				ERROR_BUSINESS_EXCEPTION, errors);

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		ResponseError body = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
				ERROR_BUSINESS_EXCEPTION, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final String error = ex.getRequestPartName() + " part is missing";

		ResponseError body = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
				ERROR_BUSINESS_EXCEPTION, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final String error = ex.getParameterName() + " parameter is missing";

		ResponseError body = createResponseError(ex, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
				ERROR_BUSINESS_EXCEPTION, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	// 404
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		ResponseError body = createResponseError(ex, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString(),
				ERROR_BUSINESS_EXCEPTION, Arrays.asList(error));

		return handleExceptionInternal(ex, body, headers, HttpStatus.NOT_FOUND, request);
	}

	// 405 - method is not supported for this request. Supported methods are
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		imprimeError(ex);
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		for (HttpMethod httpMethod : ex.getSupportedHttpMethods()) {
			builder.append(httpMethod + "");
		}

		HttpRequestMethodNotSupportedException exa = new HttpRequestMethodNotSupportedException(
				request.getHeader("accept"), String.valueOf(ex.getSupportedHttpMethods()));
		ResponseError body = createResponseError(exa, HttpStatus.METHOD_NOT_ALLOWED,
				HttpStatus.METHOD_NOT_ALLOWED.toString(), ERROR_BUSINESS_EXCEPTION, Arrays.asList(builder.toString()));

		return handleExceptionInternal(exa, body, headers, HttpStatus.METHOD_NOT_ALLOWED, request);

	}

	// 415
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		imprimeError(ex);

		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		for (MediaType mediaType : ex.getSupportedMediaTypes()) {
			builder.append(mediaType + ",");
		}

		MediaType mediaType = MediaType.parseMediaType(request.getHeader("accept"));
		HttpMediaTypeNotSupportedException exa = new HttpMediaTypeNotSupportedException(mediaType,ex.getSupportedMediaTypes());
		headers.setAccept(ex.getSupportedMediaTypes());
		ResponseError body = createResponseError(exa, HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), ERROR_BUSINESS_EXCEPTION,
				Arrays.asList(builder.substring(0, builder.length() - 2)));

		return handleExceptionInternal(exa, body, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
	}

	private ResponseError createResponseError(Throwable ex, HttpStatus httpStatus, String httpStatusStr,
			String errorType, List<String> errors) {
		String mensajeRoot = null;

		if (ex != null) {
			if(ex.getMessage().equals("Registro no encontrado.") || ex.getMessage().equals("Prevalidación no encontrada.")){
				mensajeRoot = ex.getMessage() != null ? ex.getMessage() : "";
				return ResponseError.builder().errorCode(httpStatus.value()).httpStatus("NO_FOUND")
						.errorMessage(mensajeRoot).build();
			}else{
				mensajeRoot = ex.getMessage() != null ? ex.getMessage() : "";
			}
		}
		return ResponseError.builder().errorCode(httpStatus.value()).rootErrorMessage(mensajeRoot)
				.httpStatus(httpStatusStr).errorList(errors).build();
	}

	private void imprimeError(Throwable ex) {
		log.error("Clase ejecutada  " + ex.getClass().getName());
		log.error("Exception handler executed  " + ex);
	}
}