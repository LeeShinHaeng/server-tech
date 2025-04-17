package com.example.servertech.common.aop;

import com.example.servertech.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

	@Pointcut("execution(* com.example.servertech.domain..application..*(..))")
	private void logPointcut() {
	}

	@AfterThrowing(value = "logPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, CustomException ex) {
		List<String> arguments = getArguments(joinPoint);
		String parameterMessage = getParameterMessage(arguments);

		log.warn("[EXCEPTION] CODE : {} || ARGUMENTS : {}", ex.getCode().getCode(), parameterMessage);
		log.warn("[EXCEPTION] FINAL POINT : {}", ex.getStackTrace()[0]);
		log.warn("[EXCEPTION] MESSAGE : {}", ex.getMessage());
		log.warn("");
	}

	@AfterThrowing(value = "logPointcut()", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
		if (ex instanceof CustomException) return;

		List<String> arguments = getArguments(joinPoint);
		String parameterMessage = getParameterMessage(arguments);

		log.error("[SERVER-ERROR] ARGUMENTS : {}", parameterMessage);
		log.error("[SERVER-ERROR] FINAL POINT : {}", ex.getStackTrace()[0]);
		log.error("[SERVER-ERROR] MESSAGE : {}", ex.getMessage());
		log.error("");
	}

	private List<String> getArguments(JoinPoint joinPoint) {
		return Arrays.stream(joinPoint.getArgs())
			.map(ExceptionLoggingAspect::getObjectFields)
			.toList();
	}

	private static String getObjectFields(Object obj) {
		StringBuilder result = new StringBuilder();
		Class<?> objClass = obj.getClass();
		result.append(objClass.getSimpleName()).append(" {");

		Field[] fields = objClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				result.append(fields[i].getName()).append(" = ")
					.append(fields[i].get(obj));
			} catch (IllegalAccessException e) {
				result.append(fields[i].getName()).append("=ACCESS_DENIED");
			}
			if (i < fields.length - 1) {
				result.append(", ");
			}
		}
		result.append("}");
		return result.toString();
	}

	private String getParameterMessage(List<String> arguments) {
		if (arguments == null)
			return "";

		return String.join(" | ", arguments);
	}
}
