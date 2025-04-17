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
	public void logAfterThrowingCustomException(JoinPoint joinPoint, CustomException ex) {
		log.warn("[EXCEPTION] CODE : {} || ARGUMENTS : {}", ex.getCode().getCode(), getArguments(joinPoint));
		log.warn("[EXCEPTION] FINAL POINT : {}", ex.getStackTrace()[0]);
		log.warn("[EXCEPTION] MESSAGE : {}", ex.getMessage());
		log.warn("----");
	}

	@AfterThrowing(value = "logPointcut()", throwing = "ex")
	public void logAfterThrowingException(JoinPoint joinPoint, Exception ex) {
		if (ex instanceof CustomException) return;

		log.error("[ERROR] ARGUMENTS : {}", getArguments(joinPoint));
		log.error("[ERROR] FINAL POINT : {}", ex.getStackTrace()[0]);
		log.error("[ERROR] MESSAGE : {}", ex.getMessage());
		log.error("----");
	}

	private String getArguments(JoinPoint joinPoint) {
		List<String> fields = Arrays.stream(joinPoint.getArgs())
			.map(ExceptionLoggingAspect::getObjectFields)
			.toList();

		return toJoinStringWithFormat(fields);
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
				result.append(fields[i].getName()).append("= ACCESS_DENIED");
			}
			if (i < fields.length - 1) {
				result.append(", ");
			}
		}
		result.append("}");
		return result.toString();
	}

	private String toJoinStringWithFormat(List<String> fields) {
		if (fields == null) return "";

		return String.join(" | ", fields);
	}
}
