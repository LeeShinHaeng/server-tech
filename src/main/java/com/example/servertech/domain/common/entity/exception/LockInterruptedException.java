package com.example.servertech.domain.common.entity.exception;

import com.example.servertech.common.exception.CustomException;

import static com.example.servertech.domain.common.entity.exception.CommonExceptionCode.LOCK_INTERRUPTED_EXCEPTION;

public class LockInterruptedException extends CustomException {
	public LockInterruptedException() {
		super(LOCK_INTERRUPTED_EXCEPTION);
	}
}
