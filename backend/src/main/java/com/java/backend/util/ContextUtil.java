package com.java.backend.util;

import com.java.backend.entity.UserEntity;


public interface ContextUtil {
	boolean isAdminRole();

	UserEntity loadUserFromContext();
}
