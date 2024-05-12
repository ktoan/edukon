package com.java.backend.util.impl;

import com.java.backend.entity.UserEntity;
import com.java.backend.enums.Role;
import com.java.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContextUtilImpl implements ContextUtil {
	public boolean isAdminRole() {
		UserEntity currentUser = loadUserFromContext();
		return currentUser.getAuthorities().stream()
				.anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));
	}

	public UserEntity loadUserFromContext() {
		return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
