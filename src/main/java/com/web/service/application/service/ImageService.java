package com.web.service.application.service;

import com.web.service.presentation.viewModel.ImageResourceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageService {

    private static final String PASTA_UPLOAD = System.getProperty("user.dir") + "/src/main/resources/storage/";

    public String upload(MultipartFile imagem) throws IOException {
        // Ensure the upload directory exists
        this.createDirectoryIfNotExists();

        // Generate a new name for the image
        String novoNome = this.gerarNovoNome(Objects.requireNonNull(imagem.getOriginalFilename()));

        // Full path to store the image
        String caminhoCompletoDoArquivo = PASTA_UPLOAD + novoNome;

        // Save the image to the file system
        imagem.transferTo(new File(caminhoCompletoDoArquivo));


        return novoNome;
    }

    private void createDirectoryIfNotExists() {
        // Create the directory if it doesn't exist
        File directory = new File(PASTA_UPLOAD);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Upload directory created at: " + PASTA_UPLOAD);
        }
    }

    private String gerarNovoNome(String nomeOriginal) {
        // Generate a new name using UUID and keep the file extension
        String[] nomeSplit = nomeOriginal.split("\\.");
        String extensao = "." + nomeSplit[1];

        return UUID.randomUUID() + extensao;
    }

//    // Method to delete an image by its name
//    public boolean deleteImage(String imageName) {
//        // Construct the full path of the image
//        String filePath = PASTA_UPLOAD + imageName;
//        File file = new File(filePath);
//
//        // Check if the file exists and delete it
//        if (file.exists()) {
//            return file.delete();
//        }
//
//        // Return false if the file does not exist
//        return false;
//    }

    public void deleteImage(String imageName) {
        // Construct the full path of the image
        String filePath = PASTA_UPLOAD + File.separator + imageName;
        File file = new File(filePath);

        try {
            // Check if the file exists
            if (file.exists()) {
                // Attempt to delete the file and handle the result
                boolean isDeleted = file.delete();
                if (!isDeleted) {
                    // Handle the case where the file could not be deleted
                    throw new RuntimeException("Failed to delete file: " + filePath);
                }
            }
        } catch (SecurityException e) {
            // Handle potential security exceptions
            throw new RuntimeException("Permission denied to delete file: " + filePath, e);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            throw new RuntimeException("An unexpected error occurred while deleting the file: " + filePath, e);
        }
    }

    public String updateImage(MultipartFile imagem, String oldImageName) throws IOException {
        deleteImage(oldImageName);
        return upload(imagem);
    }


    // Method to retrieve the image as a Resource (file stream) by its name
    public ImageResourceDTO retrieveImage(String filename) throws IOException {
        // Construct the full path of the image
        Path filePath = Paths.get(PASTA_UPLOAD).toAbsolutePath().normalize().resolve(filename);

        // Check if the file exists
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }

        // Return the image as a Resource (file stream)
        UrlResource imageResource =  new UrlResource(filePath.toUri());
        return new ImageResourceDTO(imageResource, filePath);
    }

}


//// The name of the RabbitMQ queue
//private static final String IMAGE_QUEUE = "imageQueue";
//
//@Autowired
//private RabbitTemplate rabbitTemplate;
//
//public void upload(MultipartFile imagem) throws IOException {
//    // Generate a new name for the image
//    String novoNome = gerarNovoNome(imagem.getOriginalFilename());
//
//    // Send the image (MultipartFile) and the new name to the image service via RabbitMQ
//    sendToQueue(imagem, novoNome);
//}
//
//private String gerarNovoNome(String nomeOriginal) {
//    // Generate a new name using UUID and keep the file extension
//    String[] nomeSplit = nomeOriginal.split("\\.");
//    String extensao = "." + nomeSplit[1];
//
//    return UUID.randomUUID() + extensao;
//}
//
//private void sendToQueue(MultipartFile imagem, String novoNome) throws IOException {
//    // Send the MultipartFile image along with the new image name to the queue
//    // Wrap the file and the new name into a custom object or just send the data
//    ImageMessage message = new ImageMessage(novoNome, imagem.getBytes());
//
//    // Create a message for RabbitMQ
//    Message<ImageMessage> rabbitMessage = MessageBuilder
//            .withPayload(message)
//            .build();
//
//    // Send the message to RabbitMQ
//    rabbitTemplate.convertAndSend(IMAGE_QUEUE, rabbitMessage);
//
//    System.out.println("Image message sent to queue with new name: " + novoNome);
//}
