package compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class GzipCompression implements Compress{
    @Override
    public String compress(String data) {
        if(data == null || data.length() == 0)
            return data;

        ByteArrayOutputStream byteArrayOutputStream = null;
        GZIPOutputStream gzip = null;


        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(byteArrayOutputStream);

            gzip.write(data.getBytes());
            gzip.close();

            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

        }

    }

    @Override
    public String decompress(String data) {

        return null;
    }
}
