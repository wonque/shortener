package io.liliac.shortener.service.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashingServiceTest {

    private final HashingService service = new HashingService();

    @Test
    public void generateBase62Hash_throwExceptionIfIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> service.generateBase62alias(-11157L));
    }

    @Test
    public void generateBase62Hash_shouldReturnValidHashValueIfValidInputReceived() {
        // 11157 - value is taken from Alex Su book System design interview
        var input = 11157L;
        var expected = "2TX";
        var result = service.generateBase62alias(input);
        assertEquals(expected, result);
    }

    @Test
    public void generateBase62Hash_shouldReturnSameHashValueIfInvokedWithSameInputMultipleTimes() {
        // 11157 - value is taken from Alex Su book System design interview
        var input = 11157L;
        var expected = "2TX";
        var result = service.generateBase62alias(input);
        assertEquals(expected, result);

        result = service.generateBase62alias(input);
        assertEquals(expected, result);
    }

    @Test
    public void generateMD5HexString_shouldReturnSameLengthMD5HashValueForDifferentUrlLength() {
        var url1 = "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/";
        var url2 = "https://theathletic.com/4994583/2023/10/26/when-does-a-striker-peak/";

        var url1Hash = service.generateMD5HexString(url1);
        var url2hash = service.generateMD5HexString(url2);

        assertEquals(url1Hash.length(), url2hash.length());
    }
}