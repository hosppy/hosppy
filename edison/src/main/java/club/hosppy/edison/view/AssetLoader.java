package club.hosppy.edison.view;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class AssetLoader {

    @Getter
    private String imageBase64;

    @Getter
    private byte[] faviconBytes;

    public static final String FAVICON_FILE_PATH = "static/assets/favicon.ico";

    @PostConstruct
    public void init() throws IOException {
        InputStream faviconFileInputStream = new ClassPathResource(FAVICON_FILE_PATH).getInputStream();
        faviconBytes = IOUtils.toByteArray(faviconFileInputStream);
    }
}
