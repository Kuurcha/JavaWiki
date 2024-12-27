package com.university.wiki.apigateway.service;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import jakarta.annotation.PostConstruct;
import org.apache.hc.client5.http.HttpResponseException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class PublicKeyService implements ApplicationListener<ApplicationReadyEvent> {
    private final String publicKeyEndpoint = "http://localhost:8082/api/auth/public-key";


    private volatile PublicKey publicKey;

    @Cacheable("publicKeyCache")
    public PublicKey getPublicKey() {
        if (this.publicKey == null) {
            throw new IllegalStateException("Public key is not initialized!");
        }
        return this.publicKey;
    }

//    @Cacheable("publicKeyCache")
//    public PublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {
//        return fetchPublicKey();
//    }

    private PublicKey fetchPublicKey() throws IOException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException {

        HttpClient client = HttpClient.newHttpClient();
        URI publiccKeyUri = URI.create(publicKeyEndpoint);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(publiccKeyUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401){
            System.out.println("Публичный ключ не был получен из-за 401!");
            return null;
        }

        // add generic error message???

        if (response.statusCode() == 404){
            System.out.println("Публичный ключ не был получен из-за 404! ЧЕГО ПРОИСХОИДТЬ НЕ ДОЛЖННО");
            return null;
        }

        String publicKeyPEM = response.body().replaceAll("\\n|\\r", "");



        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            this.publicKey = fetchPublicKey();
            System.out.println("Public key successfully fetched at startup.");
        } catch (Exception e) {
            System.err.println("Failed to fetch public key during startup: " + e.getMessage());
        }
    }
}
