package com.sharp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * <p>Title: FileUtil</p>
 * <p>Description: 文件操作工具类</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author yuanhongwei
 * @version 1.0 2019-6-30 下午6:48:33 【初版】
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 新建目录
     *
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    public static String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            logger.error("createFolder fail!", e);
        }
        return txt;
    }

    /**
     * 多级目录创建
     *
     * @param folderPath 准备要在本级目录下创建新目录的目录路径 例如 c:myf
     * @param paths      无限级目录参数，各级目录以单数线区分 例如 a|b|c
     * @return 返回创建文件后的路径 例如 c:myf/a/b/c
     */
    public static String createFolders(String folderPath, String paths) {
        String txts = folderPath;
        try {
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "|");
            for (int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1) {
                    txts = createFolder(txts + txt);
                } else {
                    txts = createFolder(txts + txt + "/");
                }
            }
        } catch (Exception e) {
            logger.error("创建目录操作出错！", e);
        }
        return txts;
    }

    /**
     * 新建文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @return
     */
    public static void createFile(String filePathAndName, String fileContent) {

        try {
            String filePath = String.valueOf(filePathAndName);
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
            resultFile.close();
        } catch (Exception e) {
            logger.error("创建目录操作出错！", e);
        }
    }

    /**
     * 有编码方式的文件创建
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @param encoding        编码方式 例如 GBK 或者 UTF-8
     * @return
     */
    public static void createFile(String filePathAndName, String fileContent, String encoding) {

        try {
            String filePath = String.valueOf(filePathAndName);
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            PrintWriter myFile = new PrintWriter(myFilePath, encoding);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
        } catch (Exception e) {
            logger.error("创建目录操作出错！", e);
        }
    }

    /**
     * 追加文件：使用FileOutputStream
     *
     * @param filePathAndName
     * @param content
     */
    public static void addContentByFile(String filePathAndName, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathAndName, true)));
            out.write(content);
        } catch (Exception e) {
            logger.error("addContentByFile fail!", e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("addContentByFile fail!", e);
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param filePathAndName
     * @param content
     */
    public static void addContentByFileName(String filePathAndName, String content) {
        try {
            // 打开一个写文件器,第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(filePathAndName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            logger.error("addContentByFileName fail!", e);
        }
    }

    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encodingStr        文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public static String readTxt(String filePathAndName, String encodingStr) throws IOException {
        String encoding = encodingStr.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data).append(" ");
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                int line = 1;
                logger.debug("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            logger.error("readFileByLines fail!", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("readFileByLines fail!", e1);
                    ;
                }
            }
        }
    }

    /**
     * 读取文件指定行
     *
     * @param sourceFile
     * @param lineNumber
     * @throws IOException
     */
    static void readAppointedLineNumber(File sourceFile, int lineNumber) throws IOException {
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();

        if (lineNumber < 0 || lineNumber > getTotalLines(sourceFile)) {
            logger.debug("不在文件的行数范围之内。");
        }
        {
            while (s != null) {
                logger.debug("当前行号为:" + reader.getLineNumber());
                System.exit(0);
                s = reader.readLine();
            }
        }
        reader.close();
        in.close();
    }

    /**
     * 文件内容的总行数
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public static boolean delFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
            }
        } catch (Exception e) {
            logger.error(filePathAndName + "删除文件操作出错！", e);
        }
        return bea;
    }

    /**
     * 删除文件
     *
     * @param myDelFile 文件
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public static boolean delFile(File myDelFile) {
        boolean bea = false;
        try {
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
            }
        } catch (Exception e) {
            logger.error("删除文件操作出错！", e);
        }
        return bea;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     * @return
     */
    public static void delFolder(String folderPath) {
        try {
            // 删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            if ("".endsWith(filePath)) {
                filePath = filePath.toString();
            }
            File myFilePath = new File(filePath);
            // 删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            logger.error("创建目录操作出错！", e);
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
                delFolder(path + "/" + tempList[i]);
                bea = true;
            }
        }
        return bea;
    }

    /**
     * 复制单个文件
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return
     */
    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            // 文件存在时
            if (oldfile.exists()) {
                // 读入原文件
                InputStream inStream = new FileInputStream(oldPathFile);
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    // 字节数 文件大小
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            logger.error("复制单个文件操作出错!", e);
        }
    }


    /**
     * 复制单个文件
     *
     * @param oldFile 准备复制的文件
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return
     */
    public static void copyFile(File oldFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            // 文件存在时
            if (oldFile.exists()) {
                // 读入原文件
                InputStream inStream = new FileInputStream(oldFile);
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    // 字节数 文件大小
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            logger.error("复制单个文件操作出错!", e);
        }
    }

    /**
     * copy file
     *
     * @param oldPathFile
     * @param newPathFile
     * @param filename
     */
    public static void copyFile(String oldPathFile, String newPathFile, String filename) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            String newPathFileName = newPathFile + filename;
            // 文件存在时
            if (oldfile.exists()) {
                // 读入原文件
                InputStream inStream = new FileInputStream(oldPathFile);
                FileOutputStream fs = new FileOutputStream(newPathFileName);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            logger.error("复制单个文件操作出错!", e);
        }
    }

    /**
     * 复制整个文件夹的内容
     *
     * @param oldPath 准备拷贝的目录
     * @param newPath 指定绝对路径的新目录
     * @return
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            // 如果文件夹不存在 则建立新文件夹
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                // 如果是子文件夹
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            logger.error("复制整个文件夹内容操作出错!", e);
        }
    }

    /**
     * 移动文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动文件
     *
     * @param oldFile
     * @param newPath
     * @return
     */
    public static void moveFile(File oldFile, String newPath) {
        copyFile(oldFile,newPath);
        delFile(oldFile);
    }

    /**
     * 移动目录
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /***
     * 读取文件夹下的所有文件路径
     *
     * @param filepath 文件夹绝对路径
     * @return 所有文件的绝对路径集合
     * @author tangchen
     * @since 2012-11-09
     */
    public static List<String> readfile(String filepath) {
        List<String> list = new ArrayList();
        File file = new File(filepath);
        if (!file.isDirectory()) {
            list.add(file.getPath());

        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "/" + filelist[i]);
                if (!readfile.isDirectory()) {
                    list.add(readfile.getPath());
                } else if (readfile.isDirectory()) {
                    readfile(filepath + "/" + filelist[i]);
                }
            }
        }
        return list;
    }

    public static String convertFileSize(long filesize) {
        String strUnit = "Bytes";
        String strAfterComma = "";

        int intDivisor = 1;

        if (filesize >= 1024 * 1024) {
            strUnit = "MB";
            intDivisor = 1024 * 1024;

        } else if (filesize >= 1024) {
            strUnit = "KB";
            intDivisor = 1024;

        }

        if (intDivisor == 1) {
            return filesize + " " + strUnit;
        }

        strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor;

        if ("".endsWith(strAfterComma)) {
            strAfterComma = ".0";
        }

        return filesize / intDivisor + "." + strAfterComma + " " + strUnit;

    }

}