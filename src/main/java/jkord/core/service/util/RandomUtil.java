package jkord.core.service.util;

import org.apache.commons.lang.RandomStringUtils;

import java.util.UUID;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;
    private static int idCounter = 1;

    private RandomUtil() {
    }

    /**
     * Generates a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generates an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
    * Generates a reset key.
    *
    * @return the generated reset key
    */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generates a game key.
     *
     * @return the generated game key
     */
    public static String generateKeyGame() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a ID.
     *
     * @return the generated ID
     */
    public static synchronized int generateId() {
        return idCounter++;
    }
}
