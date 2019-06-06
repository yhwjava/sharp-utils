package com.sharp.utils.linux;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @ClassName:HostInfo.java     
 * @version V1.0 
 * @author: wgcloud     
 * @date: 2019年1月14日
 * @Description: hostIP密码等信息
 * @Copyright: 2019 wgcloud. All rights reserved.
 *
 */
public class HostInfo implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3875927332935900938L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * ssh端口
	 */
    private int sshPort = 22;

	/**
	 * host名称
	 */
    private String hostname;

    /**
	 * 用户
	 */
    private String username;
    
    /**
	 * 密码
	 */
    private String password;
    
    
    /**
     * 创建时间
     */
    private Timestamp createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSshPort() {
		return sshPort;
	}

	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "HostInfo{" +
				"id='" + id + '\'' +
				", sshPort=" + sshPort +
				", hostname='" + hostname + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", createTime=" + createTime +
				'}';
	}
}