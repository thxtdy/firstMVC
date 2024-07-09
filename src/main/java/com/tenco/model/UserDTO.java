package com.tenco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 데이터 변환, 담는 개념, !메소드! 사용할 수 있다. -> DTO(Data Transfer Object)
 * 데이터 변환, 담는 개념 -> VO (Value Object..?)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
 
	private int id;
	private String userName;
	private String password;
	private String email;
	private String createdAt;
	
	// 필요하다면 메소드 정의 가능
	
}
