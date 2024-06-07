package cegep.management.system.api.page;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class StaticResourcesController {

    private static final Resource ASSETS_DIRECTORY = new ClassPathResource("templates/assets/");
    private static final MediaType JAVASCRIPT_MEDIA_TYPE = MediaType.parseMediaType("application/javascript");
    private static final MediaType TEXT_CSS_MEDIA_TYPE = MediaType.parseMediaType("text/css");

    @GetMapping("/assets/{filename}")
    public ResponseEntity<byte[]> serveAsset(@PathVariable String filename) throws IOException {
        Path filePath = Path.of(ASSETS_DIRECTORY.getFile().getAbsolutePath(), filename);

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = Files.readAllBytes(filePath);
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (filename.endsWith(".js")) {
            mediaType = JAVASCRIPT_MEDIA_TYPE;
        } else if (filename.endsWith(".css")) {
            mediaType = TEXT_CSS_MEDIA_TYPE;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(mediaType)
                .body(fileContent);
    }
}
