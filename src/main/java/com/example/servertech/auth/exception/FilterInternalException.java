package com.example.servertech.auth.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.auth.exception.AuthExceptionCode.FILTER_INTERNAL;

public class FilterInternalException extends CustomException {
	public FilterInternalException() {
		super(FILTER_INTERNAL);
	}
}
