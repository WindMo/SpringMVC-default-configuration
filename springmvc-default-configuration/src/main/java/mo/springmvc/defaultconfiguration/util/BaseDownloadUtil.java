package mo.springmvc.defaultconfiguration.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 * todo
 */

public abstract class BaseDownloadUtil {

    private static final DownloadConfig DEFAULT_DOWNLOAD_CONFIG = (response) -> {

        response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
    };

    /**
     * 输入流下载支持，使用默认下载配置
     * @param inputStream 输入流
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param downloadFileName 下载到客户端显示的文件名
     * @throws IOException
     */
    public static void downloadFromInputStreamSupport(InputStream inputStream, HttpServletResponse response, int bufferSize, String downloadFileName) throws IOException {

        setDownloadFileName(response,downloadFileName);
        downloadFromInputStream(inputStream,response,bufferSize,DEFAULT_DOWNLOAD_CONFIG);
    }

    /**
     * 下载指定文件
     * @param file 文件
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param config 下载配置
     * @throws IOException
     */
    public static void downloadFromFile(File file,HttpServletResponse response, int bufferSize, DownloadConfig config) throws IOException {

        downloadFromInputStream(new FileInputStream(file),response,bufferSize,config);
    }

    /**
     * 下载指定文件支持
     * @param file 文件
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param downloadFileName 下载到客户端显示的文件名
     * @throws IOException
     */
    public static void downloadFromFileSupport(File file,HttpServletResponse response, int bufferSize, String downloadFileName) throws IOException {

        setDownloadFileName(response,downloadFileName);
        downloadFromFile(file,response,bufferSize,DEFAULT_DOWNLOAD_CONFIG);
    }

    /**
     * 压缩下载文件或文件夹
     * @param path 文件路径
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param config 下载配置
     * @param fileFilter 文件过滤器
     * @param path 文件路径数组
     * @throws IOException
     */
    public static void downloadToZipFromFile(HttpServletResponse response, int bufferSize, DownloadConfig config,FileFilter fileFilter,String... path) throws Exception {

        config.setResponseHead(response);
        OutputStream outputStream = response.getOutputStream();
        BaseZipUtil.compress(outputStream, bufferSize,fileFilter,path);
        outputStream.close();
    }

    /**
     * 压缩下载文件或文件夹，不支持文件过滤器，即全部压缩下载
     * @param path 文件路径
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param config 下载配置
     * @param path 文件路径数组
     * @throws IOException
     */
    public static void downloadToZipFromFile(HttpServletResponse response, int bufferSize, DownloadConfig config,String... path) throws Exception {

        downloadToZipFromFile(response,bufferSize,config,null,path);
    }

    /**
     * 压缩下载文件或文件夹，支持文件过滤器
     * @param path 文件路径
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param fileFilter 文件过滤器
     * @param downloadFileName 下载到客户端显示的文件名
     * @param path 文件路径数组
     * @throws Exception
     */
    public static void downloadToZipFromFileSupport(HttpServletResponse response, int bufferSize, String downloadFileName, FileFilter fileFilter, String... path) throws Exception {

        setDownloadFileName(response,downloadFileName);
        downloadToZipFromFile(response,bufferSize,DEFAULT_DOWNLOAD_CONFIG,fileFilter,path);
    }

    /**
     * 压缩下载文件或文件夹支持，不支持文件过滤器，即全部压缩下载
     * @param path 文件路径
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param downloadFileName 下载到客户端显示的文件名
     * @param path 文件路径数组
     * @throws Exception
     */
    public static void downloadToZipFromFileSupport(HttpServletResponse response, int bufferSize, String downloadFileName, String... path) throws Exception {

        downloadToZipFromFileSupport(response,bufferSize,downloadFileName,null,path);
    }

    public static void downloadExcelXlsx() {


    }

    public static void downloadExcelXls() {


    }

    /**
     * 输入流下载
     * @param inputStream 输入流
     * @param response 响应对象
     * @param bufferSize 下载缓冲区大小
     * @param config 下载配置
     * @throws IOException
     */
    public static void downloadFromInputStream(InputStream inputStream, HttpServletResponse response, int bufferSize, DownloadConfig config) throws IOException {

        if (config != null) {

            config.setResponseHead(response);
        }
        byte[] buffer = new byte[bufferSize];
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
            output = new BufferedOutputStream(response.getOutputStream());
            input = new BufferedInputStream(inputStream);
            boolean var6 = true;

            int n;
            while((n = input.read(buffer, 0, bufferSize)) > -1) {
                output.write(buffer, 0, n);
            }
            output.flush();
            response.flushBuffer();
        } catch (IOException e) {
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }


    /**
     * 设置下载的文件名到响应头
     * @param response
     * @param downloadFileName
     */
    private static void setDownloadFileName(HttpServletResponse response, String downloadFileName ) {

        try {
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(downloadFileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public interface DownloadConfig {

        /**
         * 设置下载响应头
         * @param response
         */
        void setResponseHead(HttpServletResponse response);
    }
}
