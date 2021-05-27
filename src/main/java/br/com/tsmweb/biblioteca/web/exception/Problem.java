package br.com.tsmweb.biblioteca.web.exception;

import java.time.LocalDateTime;
import java.util.List;

public class Problem {

	private Integer status; // código http 400, 500...
	
	private LocalDateTime timeStamp;
	
	private String type; // uri descreve o tipo de erro...
	
	private String title; // breve descrição do tipo do erro
	
	private String detail; // detalhe da ocorrência do erro
	
	private String userMessage;
	
	private List<Fields> fields;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Problem problem;
		
		public Builder() {
			this.problem = new Problem();
		}
		
		public Builder addStatus(Integer status) {
			this.problem.status = status;
			return this;
		}
		
		public Builder addTimeStamp(LocalDateTime timeStamp) {
			this.problem.timeStamp = timeStamp;
			return this;
		}
		
		public Builder addType(String type) {
			this.problem.type = type;
			return this;
		}
		
		public Builder addTitle(String title) {
			this.problem.title = title;
			return this;
		}
		
		public Builder addDetail(String detail) {
			this.problem.detail = detail;
			return this;
		}
		
		public Builder addUserMessage(String userMessage) {
			this.problem.userMessage = userMessage;
			return this;
		}
		
		public Builder addListFields(List<Fields> fields) {
			this.problem.fields = fields;
			return this;
		}
		
		public Problem build() {
			return this.problem;
		}
	}
	
}
