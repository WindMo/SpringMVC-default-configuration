package mo.springmvc.defaultconfiguration.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 * 文件压缩工具类，这里的文件指文件也指文件夹（与File的定义相同）
 * 文件规约：
 * 文件夹的完整路径不应该即以文件分隔符结束，如 C:path1\path2，而不是C:path1\path2\
 * 虽然两者Java都能识别且不报错，但是File类的API中，getParent()方法返回的是 C:path1\path2，所以以规范为主
 * 文件夹与文件名称则没有分隔符
 */

public abstract class BaseZipUtil extends BaseIOUtil {

    /**
     * 压缩文件到指定文件
     * @param zipFilename 压缩后的文件名称
     * @param buffSize 压缩速度，即压缩缓冲区大小
     * @param paths 文件路径数组
     * @throws Exception
     */
    public static void compress(String zipFilename, int buffSize, String... paths) throws Exception {
       try (FileOutputStream fileOutputStream = new FileOutputStream(zipFilename)) {
           compress(fileOutputStream, buffSize,null, paths);
       }

    }

    /**
     * 压缩文件到指定文件，可指定文件过滤器
     * @param zipFilename 压缩后的文件名称
     * @param buffSize 压缩速度，即压缩缓冲区大小
     * @param fileFilter 文件过滤器
     * @param paths 文件路径数组
     * @throws Exception
     */
    public static void compress(String zipFilename, int buffSize, FileFilter fileFilter, String... paths) throws Exception {

        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFilename)) {
            compress(fileOutputStream, buffSize,fileFilter, paths);
        }
    }

    /**
     * 压缩文件到输出流，常用，如压缩下载
     * @param os
     * @param buffSize
     * @param paths
     * @throws Exception
     */
    public static void compress(OutputStream os, int buffSize,FileFilter fileFilter, String... paths) throws Exception {

        ZipOutputStream zos = new ZipOutputStream(os);

        for(String path : paths) {

            if (!"".equals(path)) {
                File file = new File(path);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        zipDirectory(zos, file.getPath(), file.getName() + File.separator, buffSize,fileFilter);
                    } else {
                        zipFile(zos, file.getPath(), "", buffSize);
                    }
                }
            }
        }
        // 压缩工作结束，自己关闭
        zos.close();
    }

    /**
     * 压缩文件夹到文件输出流
     * @param zos 压缩输出流
     * @param dirName 文件夹名称
     * @param basePath 文件夹路径
     * @param buffSize 缓冲区大小
     * @param fileFilter 文件过滤器
     * @throws Exception
     */
    private static void zipDirectory(ZipOutputStream zos, String dirName, String basePath, int buffSize,FileFilter fileFilter) throws Exception {

        File dir = new File(dirName);
        if (dir.exists()) {
            File[] files = dir.listFiles(fileFilter);
            if (files.length > 0) {

                for(File file : files) {

                    if (file.isDirectory()) {
                        zipDirectory(zos, file.getPath(),
                                basePath + file.getName().substring(file.getName().lastIndexOf(File.separator) + 1) + File.separator,
                                buffSize,fileFilter);
                    } else {
                        zipFile(zos, file.getPath(), basePath, buffSize);
                    }
                }
            } else {
                ZipEntry ze = new ZipEntry(basePath);
                zos.putNextEntry(ze);
            }
        }

    }

    /**
     * 压缩文件到压缩输出流
     * @param zos
     * @param filename
     * @param basePath
     * @param buffSize
     * @throws Exception
     */
    public static void zipFile(ZipOutputStream zos, String filename, String basePath, int buffSize) throws Exception {
        File file = new File(filename);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(filename);
            zipInputStream(zos,fis,buffSize,basePath+filename);
            // 到这里 FileInputStream 已经使用结束，直接关闭
            fis.close();
        }
    }

    /**
     * 输入流压缩到压缩输出流
     * @param zos 压缩输出流
     * @param inputStream 输入流
     * @param buffSize 缓冲区大小
     * @param entryName 压缩文件名称
     * @throws Exception
     */
    public static void zipInputStream(ZipOutputStream zos,InputStream inputStream,int buffSize, String entryName) throws Exception {

        ZipEntry ze = new ZipEntry(entryName);
        zos.putNextEntry(ze);
        bufferIOButNotClose(inputStream,zos,buffSize);
        // 这里不关闭流，因为并不知道流是否还有用，这个方法只负责IO操作，不负责关闭
    }

}
