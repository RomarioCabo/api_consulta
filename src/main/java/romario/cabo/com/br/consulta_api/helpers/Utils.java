package romario.cabo.com.br.consulta_api.helpers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Component
public class Utils {

    private static final String folderImages = "state_photos/";

    public static void moveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Não foi possível salvar a imegem: " + fileName, ioe);
        }
    }

    public static void deleteFile(String uploadDir) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        FileUtils.deleteDirectory(uploadPath.toFile());
    }

    public static byte[] getImageWithMediaType(Long id, String imageName) {
        try {
            if (imageName != null) {
                String path = folderImages + id + "/";

                Path destination = Paths.get(path + imageName);

                return IOUtils.toByteArray(destination.toUri());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
