package compression;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompression implements Compress{
    @Override
    public byte[] compress(String data) {
        if(data == null || data.length() == 0)
            return data.getBytes();

        ByteArrayOutputStream byteArrayOutputStream = null;
        GZIPOutputStream gzip = null;


        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(byteArrayOutputStream);

            gzip.write(data.getBytes("UTF-8"));
            gzip.close();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

        }

    }

    @Override
    public String decompress(byte[] data) {

        if (data == null || data.length == 0) {
            return data.toString();
        }
        System.out.println("Input String length : " + data.length);

        GZIPInputStream gis = null;
        BufferedReader bf = null;
        try {
            gis = new GZIPInputStream(new ByteArrayInputStream(data));
            bf = new BufferedReader(new InputStreamReader(gis));
            StringBuilder outStr = new StringBuilder();
            String line;
            while ((line=bf.readLine())!=null) {
                outStr.append(line);
            }
            System.out.println("Output String length : " + outStr.length());
            return outStr.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
