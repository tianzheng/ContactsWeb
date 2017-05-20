package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bean.ConstactInfoBean;
import bean.LoginBean;
import service.MyService;

public class CloudBackupsServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;  
   /* static int LOGIN_FAILED = 0;  
    static int LOGIN_SUCCEEDED = 1;  */
         
    public CloudBackupsServlet() {  
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
        String responseMsg="备份失败！！！";  
        
        // 设置编码形式    
        request.setCharacterEncoding("utf-8");   
        response.setCharacterEncoding("utf-8");   
        // 输出流    
        PrintWriter out = response.getWriter();    
    
        // 获取传入数据    
			//get json parameter from client
			StringBuffer jb = new StringBuffer();
			  String line = null;
		
			    BufferedReader reader = request.getReader();
			    while ((line = reader.readLine()) != null){
			      jb.append(line);
			    }
			    Gson gson=new Gson();
			    String getJson=jb.toString();
			    System.out.println(getJson);
			    
			    ConstactInfoBean data=gson.fromJson(getJson, ConstactInfoBean.class); 

	   
		LoginBean loginBean=new LoginBean(); 	    
			    
        // 访问数据库    
    //   int value = MyService.insertContact(data);  
        //插入短信
     int value=     MyService.insertSMS(data);
     
        if(value == MyService.BACKUPS_SUCCEEDED) {    
            responseMsg = "备份成功！！";    
        }  
        loginBean.setResult(value);
        loginBean.setMessage(responseMsg);
        
        System.out.println("");
       String json= new Gson().toJson(loginBean);
       System.out.println("login servlet json:" + json); 
        out.print(json); 
    }  

}