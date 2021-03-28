package br.com.codechallenge.zap.util;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

import com.google.gson.Gson;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

	public static final Charset UTF_8 = Charset.forName("UTF-8");
	
	public static Gson objectGson;
	
	public static Throwable getDeepCause(Throwable e) {
		if (isNull(e))
			return e;
		
		Throwable cause = e;
		Throwable prevCause = e;
		
		while(nonNull(cause)) {
			prevCause = cause;
			cause = cause.getCause();
		}
		
		return prevCause;
	}

	public static String getDeepCauseMessage(Throwable e) {
		Throwable error = getDeepCause(e);
		String message = nonNull(error) ? error.getMessage() : null;
		if (isNull(message) || isEmpty(message))
			message = "Sem mensagem";
		
		return message;
	}
	
	public static boolean isEmpty(Object value) {
		if (isNull(value))
			return true;
		
		if (value instanceof String)
			return value.toString().trim().isEmpty();
		
		if (value instanceof Collection)
			return ((Collection<?>) value).isEmpty();

		if (value.getClass().isArray())
			return Arrays.asList(value).isEmpty();
		
		return false;
	}
	
	public static <T> byte[] toJson(T source) {
		
		return getGson().toJson(source).getBytes(UTF_8);
	}
	
	private static Gson getGson() {
		return objectGson;
	}
	
	public static boolean isValidNumber(String object) {
		
		if (isEmpty(object))
			return false;
		
		try {
			BigDecimal value = new BigDecimal(object);			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}
