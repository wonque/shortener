package io.liliac.shortener.service.hash;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.*;

@ApplicationScoped
public class HashingService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateBase62alias(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Unable to generate alias for negative numerical id value");
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
        if (Objects.isNull(input) || input.isBlank()) {
            throw new IllegalArgumentException("Invalid input parameter to generate hash");
        }
        return DigestUtils.md5Hex(input);
    }
}
