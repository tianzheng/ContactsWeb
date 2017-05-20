package bean;
/**
 * Created by FoolishFan on 2016/7/14.
 */

public class UserData {
    private String userName="";                  //用户名
    private String userPwd="";   //用户密码 
	private String device="";  
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
 
  
}
