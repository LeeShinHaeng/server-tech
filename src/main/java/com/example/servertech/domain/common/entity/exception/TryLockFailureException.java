package com.example.servertech.domain.common.entity.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.common.entity.exception.CommonExceptionCode.TRY_LOCK_FAILURE;

public class TryLockFailureException extends CustomException {
	public TryLockFailureException() {
		super(TRY_LOCK_FAILURE);
	}
}
