package com.example.demo.Project;

public class Message 
{
       public String content,type;
       public Message() {
		// TODO Auto-generated constructor stub
	}
	public String getContent() {
		return content;
	}
	public Message(String content, String type) {
		this.content = content;
		this.type = type;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Message [content=" + content + ", type=" + type + "]";
	}
       
}
