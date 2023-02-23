package com.reseau.fb.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {
	
	
	
	
	private final String directoryPath="C:\\Users\\chris\\Pictures\\Saved Pictures";
	
	
	
	@PostMapping("/image")
	public ResponseEntity<Void>saveImage(@RequestParam("file") MultipartFile file)
	{
		try {
			String filename=file.getOriginalFilename();
			File targetFile = new File(directoryPath ,filename);
			file.transferTo(targetFile);
			return ResponseEntity.ok().build();
		}catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImageByName(@PathVariable String imageName) {
        try {
            // Récupération du fichier image correspondant
            Path imagePath = Paths.get(directoryPath, imageName);
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
