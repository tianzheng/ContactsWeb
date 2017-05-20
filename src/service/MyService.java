package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import bean.ConstactInfoBean;
import bean.ContactBean;
import bean.SMSBean;
import bean.SmsListBean;
import db.DBManager;

public class MyService {
	static PreparedStatement preparedStatement = null;    
    static ResultSet resultSet = null;    
    public  static int updateRowCnt = 0;  
    public   static int LOGIN_FAILED = 0;  
    public 	static int LOGIN_SUCCEEDED = 1;  
    public   static int REGISTER_FAILED = 2;  
    public   static int REGISTER_SUCCEEDED = 3;  
    public   static int REGISTER_FAILED_USER_EXIST = 4;  
    
    public   static int BACKUPS_FAILED = 5;  
    public   static int BACKUPS_SUCCEEDED = 6; 
    
    
    public static int login(String username, String password) {   
    	System.out.println("username="+username+"=="+password);

        int result = LOGIN_FAILED;    
        resultSet = null;  
        // 执行 SQL 查询语句    
        String sql = "select * from user_list where username='" + username +"'";    
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                resultSet = preparedStatement.executeQuery();    
                // 查询结果    
                if(resultSet.next()){      
                    if(resultSet.getString("password").equals(password)){  
                        result = LOGIN_SUCCEEDED;  
                        System.out.println("username:" + username   
                            + " username:" + resultSet.getString("username")  
                            + " --login");  
                    }  
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("login service result:" + result);  
        return result;    
    }    
      
    public static int register(String device, String username, String password) {    
        int result = REGISTER_FAILED;    
        
        result  = isExist(username);
        if(result==REGISTER_FAILED_USER_EXIST){
        	   return result;  
        }
        
        updateRowCnt = 0;  
        // 执行 SQL 插入语句    
        String sql = "insert into user_list(`device`, `username`,`password`) values ('"  
                + device + "', '" + username + "', '" + password + "')";  
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                updateRowCnt = preparedStatement.executeUpdate();    
                // 插入结果    
                if(updateRowCnt != 0){      
                        result = REGISTER_SUCCEEDED;  
                        System.out.println("id:" + device   
                            + " username:" + resultSet.getString("username")  
                            + " --register");  
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("register service result:" + result);  
        return result;    
    }  
    
    
    
    
    public static int isExist(String username) {   
        int result = LOGIN_FAILED;    
        resultSet = null;  
        // 执行 SQL 查询语句    
        String sql = "select * from user_list where username='" + username +"'";    
        try {       
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                resultSet = preparedStatement.executeQuery();    
                // 查询结果    
                if(resultSet.next()){      
               
                        result = REGISTER_FAILED_USER_EXIST;  
                    
                }    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("REGISTER_FAILED_USER_EXIST:=" + result);  
        return result;    
    }    
    
    
    
    
    public static int insertContact( ConstactInfoBean data) {    
        int result = BACKUPS_FAILED;    
        String username=data.getUsername();
        System.out.println("username="+username);
        List<ContactBean> contactList=data.getContactList();
        
        if(contactList.size()>0){
        	isPhoneExist(username);
        }
        
        for (int i = 0; i < contactList.size(); i++) {
        	
            updateRowCnt = 0;  
            int contactId=contactList.get(i).getContactId();
            String displayName=contactList.get(i).getDisplayName();
            String phoneNum=contactList.get(i).getPhoneNum();
            String sortKey=contactList.get(i).getSortKey();
            String lookUpKey=contactList.get(i).getLookUpKey();
            long photoId=contactList.get(i).getPhotoId();
            int selected=contactList.get(i).getSelected();
        
            // 执行 SQL 插入语句    
            String sql = "insert into contact_info(`contactId`, `displayName`,`phoneNum`, `sortKey`,`lookUpKey`, `photoId`,`selected`, `username`) values ('"  
                    + contactId + "', '" + displayName  + "', '" + phoneNum  + "', '" + sortKey  + "', '" + lookUpKey  + "', '" + photoId  + "', '" + selected + "', '" + username + "')";  
        //   System.out.println("sql="+sql);
            try {       
                Connection con = DBManager.getConnection();    
                preparedStatement = con.prepareStatement(sql);    
                try{    
                    updateRowCnt = preparedStatement.executeUpdate();    
                    // 插入结果    
                    if(updateRowCnt != 0){      
                            result = BACKUPS_SUCCEEDED;  
                           
                    }    
                    preparedStatement.close();    
                    con.close();    
                }catch(Exception e){    
                    e.printStackTrace();    
                }    
            }catch(Exception e){    
                e.printStackTrace();    
            } 	
        	
        	
        	
        	
		}
        
           
        return result;    
    }  
   
    
    public static void isPhoneExist(String username) {   
     
        // 执行 SQL 查询语句    
        String sql = "DELETE  FROM contact_info where username='" + username +"'";    
        try {  
        	System.out.println("开始删除sql="+sql);
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                preparedStatement.execute();   
                // 查询结果    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("删除成功");  
       
    }  
    
    
    
    
    public static int insertSMS( ConstactInfoBean data) {    
        int result = BACKUPS_FAILED;    
        String username=data.getUsername();
        System.out.println("username="+username);
         List<SmsListBean> list_mmt=data.getListBean();
        
        if(list_mmt.size()>0){
        	isSMSExist(username);
        }
        
        for (int i = 0; i < list_mmt.size(); i++) {
        	
            updateRowCnt = 0;  
            
            String _id=list_mmt.get(i).get_id();
        	 String thread_id=list_mmt.get(i).getThread_id();
        	 String address=list_mmt.get(i).getAddress();
        	 String person=list_mmt.get(i).getPerson();
        	 String body=list_mmt.get(i).getBody();
        	 String date_time=list_mmt.get(i).getDate();
        	 String type=list_mmt.get(i).getType();
        	
        	
        	 
        
            // 执行 SQL 插入语句    
String sql = "insert into sms_info(`_id`, `thread_id`,`address`, `person`,`body`, `date_time`, `type`) values ('"  
 + _id + "', '"  + thread_id  + "', '" + address  + "', '" + person  + "', '" + body  + "', '" + date_time  + "', '" + type + "')";  
          System.out.println("sql="+sql);
            try {       
                Connection con = DBManager.getConnection();    
                preparedStatement = con.prepareStatement(sql);    
                try{    
                    preparedStatement.execute();    
                    // 插入结果    
                   
                            result = BACKUPS_SUCCEEDED;  
                           
                 
                    preparedStatement.close();    
                    con.close();    
                }catch(Exception e){    
                    e.printStackTrace();    
                }    
            }catch(Exception e){    
                e.printStackTrace();    
            } 	
        	
        	
        	
        	
		}
        
           
        return result;    
    } 
    
    
    
    public static void isSMSExist(String username) {   
        
        // 执行 SQL 查询语句    
        String sql = "DELETE  FROM sms_info where username='" + username +"'";    
        try {  
        	System.out.println("开始删除sql="+sql);
            Connection con = DBManager.getConnection();    
            preparedStatement = con.prepareStatement(sql);    
            try{    
                preparedStatement.execute();   
                // 查询结果    
                preparedStatement.close();    
                con.close();    
            }catch(Exception e){    
                e.printStackTrace();    
            }    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        System.out.println("删除成功");  
       
    }  
    
}
