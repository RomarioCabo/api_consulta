package romario.cabo.com.br.consulta_api.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.io.*;
import java.nio.file.*;

@Component
public class Utils {

    private static final String folderImages = "state_photos/";

    public static URI getUri(String fromHttpUrl, String queryParam, long id) {
        return ServletUriComponentsBuilder.fromHttpUrl(fromHttpUrl).
                queryParam(queryParam).buildAndExpand(id).toUri();
    }

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
    
    public static boolean passwordMatch(String passwordEnteredByUser, String passwordEnteredByDB) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(passwordEnteredByUser, passwordEnteredByDB);
    }
}
