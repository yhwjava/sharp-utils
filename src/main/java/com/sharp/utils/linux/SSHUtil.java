package com.sharp.utils.linux;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.sharp.utils.ObjUtil;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.util.zip.GZIPOutputStream;

/**
 * <p>Title: PrintParamAutoConfigure</p>
 * <p>Description</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author yuanhongwei
 * @version 1.0
 */
public class SSHUtil {
    private static final Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    private static final ThreadLocal<Connection> conn = new ThreadLocal<>();
    private static final ThreadLocal<Session> session = new ThreadLocal<>();

    public static void main(String[] args) {
        try {
            long a1 = System.currentTimeMillis();
            Connection connection = getConn("47.105.200.130", 22, "test", "yhw123");
            logger.info("user time:" + (System.currentTimeMillis() - a1));
            String cmd = LinuxCmd.cpuMax;
            String a = doCommond(connection, cmd);
            logger.info("user time:" + (System.currentTimeMillis() - a1));
            logger.info("ttttttttttttttt" + a);
            if ("df -hlP".equals(cmd)) {
                String[] arrayRow = a.split("</br>");
                long use = 0;
                for (int j = 0; j < arrayRow.length; j++) {
                    String[] arrayRank = arrayRow[j].split("\\s+");
                    for (int i = 0; i < arrayRank.length; i++) {
                        if (j != 0 && i == 4) {
                            use = use + Long.parseLong(arrayRank[i].replace("%", ""));
//							logger.info("------------"+arrayRank[i]);
                        }
                    }
                }
                logger.info("ttttttttttttttt" + use);
            } else if ("mpstat -P ALL 1 3".equals(cmd)) {
                String[] arrayRow = a.split("</br>");
                Double use = 0.0;
                for (int j = 0; j < arrayRow.length; j++) {
                    String[] arrayRank = arrayRow[j].split("\\s+");
                    if (j == 1) {
                        use = Double.parseDouble(arrayRank[2].replace("%", "")) + Double.parseDouble(arrayRank[4].replace("%", ""));
                    }

                }
                logger.info("ttttttttttttttt" + use);
            } else if ("free -m".equals(cmd)) {
                String[] arrayRow = a.split("</br>");
                Double use = 0.0;
                String used = "";
                for (int j = 0; j < arrayRow.length; j++) {
                    String[] arrayRank = arrayRow[j].split("\\s+");
                    if (j == 1) {
                        float num = (float) Long.parseLong(arrayRank[2]) / Long.parseLong(arrayRank[1]);
                        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                        used = df.format(num);//返回的是String类型
                    }

                }
                logger.info("ttttttttttttttt" + used);
            }


        } catch (Exception e) {
            logger.error("main", e);
        }

    }

    /**
     * 获取服务器链接
     *
     * @param username
     * @param port
     * @param password
     * @return
     */
    public static Connection getConn(String hostIp, int port, String username, String password) {
        try {
            Connection conn = new Connection(hostIp, port);
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                logger.warn("用户名称或者是密码不正确");
            } else {
                return conn;
            }
        } catch (IOException e) {
            logger.error("获取服务器链接出现异常：", e);
            return null;
        }
        return null;
    }

    /**
     * 远程执行命令
     *
     * @param conn
     * @param cmd
     * @return
     */
    public static String doCommond(Connection conn, String cmd) throws Exception {
        String result = "";

        BufferedReader stdoutReader = null;
        InputStream stdout = null;
        try {
            if (conn == null) {
                logger.info("请先链接服务器");
            } else {
                session.set(conn.openSession());
                session.get().execCommand(cmd);
                stdout = new StreamGobbler(session.get().getStdout());
                stdoutReader = new BufferedReader(new InputStreamReader(stdout));
                while (true) {
                    String line = stdoutReader.readLine();
                    if (line == null) {
                        break;
                    }
                    result += line + StaticKeys.SPLIT_BR;
                }
                //连接的Session和Connection对象都需要关闭
                stdoutReader.close();
            }
        } catch (IOException e) {
            logger.error("执行linux命令错误：", e);
        } finally {
            if (!ObjUtil.isEmpty(stdoutReader)) {
                stdoutReader.close();
            }
            if (!ObjUtil.isEmpty(stdout)) {
                stdout.close();
            }
        }
        if (result.endsWith(StaticKeys.SPLIT_BR)) {
            result = result.substring(0, result.length() - StaticKeys.SPLIT_BR.length());
        }

        if (!ObjUtil.isEmpty(result)) {
            if (cmd.contains("DEV") || cmd.contains("iostat")) {
                if (result.contains("</br></br>")) {
                    result = result.substring(result.lastIndexOf("</br></br>") + 10);
                }
            }
            if (cmd.contains("mpstat")) {
                if (result.contains("</br></br>")) {
                    result = result.substring(result.lastIndexOf("</br></br>") + 10);
                    int s = result.indexOf("</br>") + 5;
                    s = result.indexOf("</br>", s);
                    result = result.substring(0, s);
                }
            }
        }

        return result;
    }

    /**
     * 释放资源
     */
    public static void clear() {
        if (!ObjUtil.isEmpty(session.get())) {
            session.get().close();
            session.remove();
        }

        if (!ObjUtil.isEmpty(conn.get())) {
            conn.get().close();
            conn.remove();
        }

    }


    /**
     * 压缩字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (ObjUtil.isEmpty(str)) {
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
