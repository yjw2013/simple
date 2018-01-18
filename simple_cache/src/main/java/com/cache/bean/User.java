package com.cache.bean;

import java.io.Serializable;

public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 449778691146210562L;

	private Integer userId;

    private String userName;

    private String passWord;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", passWord=" + passWord + "]";
	}
    
}