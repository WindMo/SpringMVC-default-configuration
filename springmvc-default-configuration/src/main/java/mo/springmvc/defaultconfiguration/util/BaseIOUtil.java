package mo.springmvc.defaultconfiguration.util;

import java.io.*;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 */

public abstract class BaseIOUtil {

    /**
     * 使用缓冲区进行IO操作，但不关闭流
     * @param inputStream
     * @param outputStream
     * @param bufferSize
     * @throws Exception
     */
    public static void bufferIOButNotClose(InputStream inputStream, OutputStream outputStream, int bufferSize) throws Exception {

        byte[] buffer = new byte[bufferSize];
        try {
            BufferedOutputStream output = new BufferedOutputStream(outputStream);
            BufferedInputStream input = new BufferedInputStream(inputStream);
            int n;
            while ((n = input.read(buffer, 0, bufferSize)) > -1) {
                output.write(buffer, 0, n);
            }
            output.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 使用缓冲区进行IO操作，自动关闭流
     * @param inputStream
     * @param outputStream
     * @param bufferSize
     */
    public static void  bufferIOAutoClose(InputStream inputStream, OutputStream outputStream, int bufferSize) throws Exception {

        try {
            bufferIOButNotClose(inputStream,outputStream,bufferSize);
        }catch (Exception e){
            throw e;
        }finally {
            if (inputStream != null) {

                inputStream.close();
            }
            if (outputStream != null) {

                outputStream.close();
            }
        }
    }
}
