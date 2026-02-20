package com.fileManager.file.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fileManager.file.Repository.UserRepository;
import com.fileManager.file.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final Path root = Paths.get("storage");
    
    // On définit le dossier de stockage (peut être configuré dans application.properties)
    private final String storageLocation = "storage/";

    public String saveProfilePicture(MultipartFile file, Long userId, String userName) throws IOException {
        // 1. On nettoie le nom (ex: "Jean Dupont" -> "jean_dupont")
        String cleanName = userName.replaceAll("\\s+", "_").toLowerCase();
        
        // 2. On récupère l'extension d'origine (.jpg, .png)
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        
        // 3. On crée le nouveau nom : "1_jean_dupont.jpg"
        String fileName = userId + "_" + cleanName + "." + extension;
        
        // 4. On enregistre physiquement le fichier
        Path targetLocation = Paths.get(storageLocation).resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        // 5. ON RETOURNE L'URL (pas le chemin absolu !)
        return "/api/users/photos/" + fileName; 
    }

    public User createUserWithPhoto(User user, MultipartFile file) {
        try {
            // 1. Sauvegarde initiale pour générer l'ID
            User temporaryUser = userRepository.save(user);
            Long id = temporaryUser.getId();

            // 2. Préparation du nom du fichier : "ID_NOM.extension"
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = id + "_" + temporaryUser.getName().replaceAll("\\s+", "_") + "." + extension;

            // 3. Stockage physique du fichier dans le dossier 'storage'
            Files.copy(file.getInputStream(), this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // 4. Création de l'URL relative (celle que le frontend utilisera)
            String photoUrl = "/api/users/photos/" + fileName;

            // 5. Mise à jour de l'utilisateur avec son URL de photo
            temporaryUser.setPhotoProfile(photoUrl);
            return userRepository.save(temporaryUser);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du stockage du fichier", e);
        }
    }
}