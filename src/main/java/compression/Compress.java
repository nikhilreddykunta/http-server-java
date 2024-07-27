package compression;

public interface Compress {

    public byte[] compress(String data);

    public String decompress(byte[] data);
}
