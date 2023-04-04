package com.example.msal;

import java.util.Map;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        printEnvVars();
        for (int i = 0; i < 10; i++) {
            try {
                check();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(60000);
        }
    }

    private static void printEnvVars() {
        System.out.println("Env vars:");
        System.getenv().entrySet().stream().forEach(e-> System.out.println("  " + e));
    }

    private static void check() {
        String keyvaultURL = System.getenv().get("KEYVAULT_URL");
        String secretName = System.getenv().get("SECRET_NAME");

        System.out.println("Building secretClient");
        SecretClient secretClient = new SecretClientBuilder()
            .vaultUrl(keyvaultURL)
            .credential(new TokenCredentialBuilder().buildTokenCredential())
            .buildClient();
        System.out.println("Getting secret");
        KeyVaultSecret secret = secretClient.getSecret(secretName);
        System.out.println("Successfully got secret, secret = " + secret.getValue());
    }

}
