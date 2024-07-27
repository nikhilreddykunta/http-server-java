package compression;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SupportedCompressions {

    private static Set<String> compressionTypes = new HashSet<>(List.of("gzip"));

    public static Set<String> getCompressionTypes() {
        return compressionTypes;
    }
}
