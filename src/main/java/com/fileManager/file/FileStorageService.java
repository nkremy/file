package com.fileManager.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private static final String STORAGE_DIRECTORY = "./storage";

    private final Path rootLocation;
    // quelle est le role de la classe Path dans cette exemple
    // quelle sont les operation qu'on peut effectuer avec la classe path

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        System.out.println("###" + uploadDir);
        this.rootLocation = Paths.get(uploadDir);
        // quelle est difference entrer Paths et Path
        // je me dit que la difference vien au niveau des action que chaqu'un peut
        // effectuer

        if (!Files.exists(rootLocation)) {
            // Files ca ce vois que ici ont verifie si queque chose existe
            // quelle sont les autres operation que l'on peut effectuer avec cette classe
            Files.createDirectories(rootLocation);
        }
    }

    public void saveFile2(MultipartFile fileToSave) throws IOException {

        // y a 'til une diffence entre fileToSave == null et fileToSave.isEmpty
        if (fileToSave.isEmpty()) {
            throw new IOException("Impossible de stocker un fichier vide.");
        }
        System.out.println("le fichier n'est pas vide ");
        String filename = StringUtils.cleanPath(Objects.requireNonNull(fileToSave.getOriginalFilename()));
        // je ne connais pas la classe Objects en java
        // quelle est le role de la methode requireNonNull
        System.out.println("le nom du fichier netoyer est : " + filename);

        /**
         * # 4. Résolution du chemin complet
         * # .normalize() permet d'éviter les attaques de type "../" (Path Traversal)
         * Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
         * .normalize()
         * .toAbsolutePath();
         * 
         * # je ne vois pas l'interet de mettre normalize car si j'ai bien compris java
         * ne creer pas automatique les repertoires
         * normalise est utilise uniquement si la personne qui veut hacker connais la
         * disposition de repertoire dans le projet je me dit
         * est ce que mon raisonnement est juste ?
         */

        Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                .normalize()
                .toAbsolutePath();
        System.out.println("la destination du fichier est : " + destinationFile);
        System.out.println("la destination du fichier a ete normaliser ");
        System.out.println("%%%###$$$");
        System.out.println(destinationFile.getParent());
        System.out.println(this.rootLocation.toAbsolutePath());
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new SecurityException("Attemp to store beyong the repository autorise");
        }
        System.err.println(" la destination du fichier est definitivement correct");
        // try (var inputStream = fileToSave.getInputStream()) {
        Files.copy(fileToSave.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        // }

    }

    public void saveFile(MultipartFile fileToSave) throws IOException {
        if (fileToSave == null) {
            throw new NullPointerException("fileToSave is null");
        }
        System.out.println("le file n'est pas null");
        var targetFile = new File(STORAGE_DIRECTORY + File.separator + fileToSave.getOriginalFilename());
        if (!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
            System.out.println("the file name is Uncorrect");
            throw new SecurityException("Unsupported filename!");
        }
        System.out.println("le nom du  file est correct ");
        Files.copy(fileToSave.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public void save(MultipartFile file) throws IOException{
        String name = StringUtils.cleanPath(file.getOriginalFilename());

        Path destination = this.rootLocation.resolve(name);

        Files.copy(file.getInputStream(),destination,StandardCopyOption.REPLACE_EXISTING);
    }

    public Resource load(String filename) throws MalformedURLException{
        Path file = rootLocation.resolve(filename);

        return new UrlResource(file.toUri());
    }

    public void delete(String filename)throws IOException{
        Path file = rootLocation.resolve(filename);

        Files.deleteIfExists(file);
    }

    public void rename(String oldName,String newName)throws IOException{
        Path source = rootLocation.resolve(oldName);

        Path target = rootLocation.resolve(newName);

        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

}

/*
 * this.rootLocation.toAbsolutePath()
 * 
 */