package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.RegisterBean;
import bean.UserData;
import service.MyService;

/**
 * Servlet implementation class RegisterrServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  

         
    public RegisterServlet() {  
        super();  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        doPost(request, response);    
        response.getWriter().append("Served at: ").append(request.getContextPath());  
    }  
      
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   
            throws ServletException, IOException {  
        // 返回字符串    
        String responseMsg="注册失败!";    
        // 设置编码形式    
        request.setCharacterEncoding("UTF-8"); 
        response.setCharacterEncoding("UTF-8");
        // 输出流    
        PrintWriter out = response.getWriter();    
       
       
        
    	//get json parameter from client
		StringBuffer jb = new StringBuffer();
		  String line = null;
	
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null){
		      jb.append(line);
		    }
		      Gson gson=new Gson();
		      UserData data=gson.fromJson(jb.toString(), UserData.class); 
        
        // 获取传入数据    
       
        String username =data.getUserName();
        String password = data.getUserPwd();
        String device= data.getDevice();
        System.out.println("id:" + device + " --try to register");  
        RegisterBean registerBean=new RegisterBean();
        // 访问数据库    
        int value = MyService.register(device, username, password);  
        if(value == MyService.REGISTER_SUCCEEDED) {    
       
            registerBean.setMessage("注册成功");
            registerBean.setResult(value);
        }else  if(value == MyService.REGISTER_FAILED_USER_EXIST) {    
            registerBean.setMessage("用户已经存在!");
        }else {
        	 registerBean.setMessage("注册失败");
		}
        registerBean.setResult(value);     
        System.out.println("register servlet responseMsg:" +   new Gson().toJson(registerBean));    
        out.print(  new Gson().toJson(registerBean));  
    }  

}
