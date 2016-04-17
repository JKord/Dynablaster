package jkord.dynablaster.util;

import org.jinstagram.utils.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {
    public StreamUtils() {
    }

    public static String getStreamContents(InputStream is) {
        Preconditions.checkNotNull(is, "Cannot get String from a null object");

        try {
            char[] ioe = new char[65536];
            StringBuilder out = new StringBuilder();
            InputStreamReader in = new InputStreamReader(is, "UTF-8");

            int read;
            do {
                read = in.read(ioe, 0, ioe.length);
                if(read > 0) {
                    out.append(ioe, 0, read);
                }
            } while(read >= 0);

            in.close();
            return out.toString();
        } catch (IOException var5) {
            throw new IllegalStateException("Error while reading response body", var5);
        }
    }
}
