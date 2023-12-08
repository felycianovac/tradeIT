package org.example.Images;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.checkerframework.checker.units.qual.A;
import org.example.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ProductRepository productRepository;
    public String uploadFile(MultipartFile file,  Integer productId){
        try {
            String bucketName = "images";
            String objectName = file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            Images img = new Images();
            img.setProduct(productRepository.findById(productId).get());
            img.setImage_url(presignedUrl);
            imagesRepository.save(img);
            return "Image saved successful";
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
