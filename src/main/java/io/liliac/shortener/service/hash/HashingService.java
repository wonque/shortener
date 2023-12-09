package io.liliac.shortener.service.hash;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.digest.DigestUtils;

@ApplicationScoped
public class HashingService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateBase62Hash(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Unable to get hash for negative numerical id value");
        }
        long localId = id;
        StringBuilder sb = new StringBuilder();
        int base = BASE62.length();
        do {
            int ind = (int) (localId % base);
            sb.insert(0, BASE62.charAt(ind));
            localId /= base;
        } while (localId > 0);
        return sb.toString();
    }

    public String generateMD5HexString(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("Unable to get hash for blank string");
        }
        return DigestUtils.md5Hex(input);
    }
}
