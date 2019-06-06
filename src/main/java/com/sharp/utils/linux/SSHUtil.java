package com.sharp.utils.linux;

import ch.ethz.ssh2.Connection; 
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.sharp.utils.ObjUtils;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Title: PrintParamAutoConfigure</p>
 * <p>Description</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author yuanhongwei
 * @version 1.0
 */
public class SSHUtil {
	private static final Logger logger = LoggerFactory.getLogger(SSHUtil.class);

	public static void main(String[] args) {
		try{
			Connection connection = getConn("47.105.200.130",22,"test","yhw123");

			logger.info("+++++++++++++{}",doCommond(connection,"ls"));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 获取服务器链接
	 * @param username
	 * @param port
	 * @param password
	 * @return
	 */
	public static Connection getConn(String hostIp,int port,String username,String password){
        try {
        	Connection conn = new Connection(hostIp,port);
    		//连接到主机
			conn.connect();
			//使用用户名和密码校验
	        boolean isconn = conn.authenticateWithPassword(username, password);
	        if(!isconn){
				logger.warn("用户名称或者是密码不正确");
	        }else{
	        	return conn;
	        }
		} catch (IOException e) {
			logger.error("获取服务器链接出现异常：",e);
			return null;
		}
        return null;
	}
	
	/**
	 * 远程执行命令
	 * @param conn
	 * @param cmd
	 * @return
	 */
	public static String doCommond(Connection conn,String cmd) throws Exception{
		String result = "";
		Session session = null;

		BufferedReader stdoutReader = null;
		InputStream stdout = null;
		try {
            if(conn==null){
            	logger.info("请先链接服务器");
            }else{
				session = conn.openSession();
				session.execCommand(cmd);
                stdout = new StreamGobbler(session.getStdout());
    			stdoutReader = new BufferedReader(new InputStreamReader(stdout));
    			while(true){
    				String line = stdoutReader.readLine();
    				if (line == null)
    					break;
    				result+=line+StaticKeys.SPLIT_BR;
    			}
    			//连接的Session和Connection对象都需要关闭 
    			stdoutReader.close();
            }
        } catch (IOException e) {
			logger.error("执行linux命令错误：",e);
        }finally {
        	if(!ObjUtils.isEmpty(session)){
				session.close();
			}
        	if(!ObjUtils.isEmpty(conn)){
        		conn.close();
			}
        	if(!ObjUtils.isEmpty(stdoutReader)){
				stdoutReader.close();
			}
        	if(!ObjUtils.isEmpty(stdout)){
				stdout.close();
			}
		}
        if(result.endsWith(StaticKeys.SPLIT_BR)){
        	result =  result.substring(0, result.length()-StaticKeys.SPLIT_BR.length());
        }
        
        if(!ObjUtils.isEmpty(result)){
        	if(cmd.contains("DEV")||cmd.contains("iostat")){
        		if(result.contains("</br></br>")){
        			result = result.substring(result.lastIndexOf("</br></br>")+10);
        		}
            }
        	if(cmd.contains("mpstat")){
        		if(result.contains("</br></br>")){
        			result = result.substring(result.lastIndexOf("</br></br>")+10);
        			int s = result.indexOf("</br>")+5;
        			s = result.indexOf("</br>",s);
        			result = result.substring(0,s);
        		}
            }
        }
        
        return result;
	}
	
	/**
	  * 压缩字符串 
	  * @param str
	  * @return
	  * @throws IOException
	  */
	 public static String compress(String str) throws IOException {   
	     if(ObjUtils.isEmpty(str)) {
	        return "";   
	     }   
	    ByteArrayOutputStream bos  = new ByteArrayOutputStream();  
	    Base64OutputStream b64os = new Base64OutputStream(bos);
	    GZIPOutputStream gout = new GZIPOutputStream(b64os);  
	    gout.write(str.getBytes("UTF-8"));  
	    gout.close();  
	    b64os.close();
	    byte b1[] = bos.toByteArray();  
	    bos.close();
	    return new String(b1);
	  } 
	
	
	
}
