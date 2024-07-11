package com.tenco.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoDTO {
	private int id;
	private int userId;
	private String title;
	private String description;
	private String dueDate;
	private String completed;
	
	// completed 데이터를 변환하는 메소드를 만들 것이다.
	public String completedToString() {
		
		return completed.equals("1") ? "true" : "false";
	}
	
}
