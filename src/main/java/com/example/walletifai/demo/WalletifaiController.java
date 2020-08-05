package com.example.walletifai.demo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;

@RestController
public class WalletifaiController {

    @PostMapping("/decrypt")
    public String decryptUserId(@RequestParam("userId") String encryptedId) {

        BasicAWSCredentials bas = new BasicAWSCredentials(Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_KEY);
        AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(bas);
        AWSKMS kmsClient = AWSKMSClientBuilder.standard().withCredentials(provider).withRegion(Regions.CA_CENTRAL_1).build();
        ByteBuffer buffer = getByteBuffer(encryptedId);
        DecryptRequest decryptRequest = new DecryptRequest().withCiphertextBlob(buffer);
        DecryptResult decryptResult = kmsClient.decrypt(decryptRequest);
        String plaintext = getString(decryptResult.getPlaintext());
        return plaintext;
    }

    private String getString(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return new String(bytes);
    }

    private ByteBuffer getByteBuffer(String string) {
        byte[] bytes = java.util.Base64.getDecoder().decode(string);
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        return byteBuffer;
    }
}
