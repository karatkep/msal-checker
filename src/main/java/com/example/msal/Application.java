package com.example.msal;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

public class Application {

    private TokenCredentialBuilder tokenCredentialBuilder = new TokenCredentialBuilder();

    public static void main(String[] args) throws InterruptedException {
        new Application().run();
    }

    private void run() throws InterruptedException {
        printEnvVars();
        for (int i = 0; i < 100; i++) {
            try {
                check();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.currentThread().sleep(60000);
        }
    }

    private void printEnvVars() {
        System.out.println("Env vars:");
        System.getenv().entrySet().stream().forEach(e-> System.out.println("  " + e));
    }

    private void check() {
        String keyvaultURL = System.getenv().get("KEYVAULT_URL");
        String secretName = System.getenv().get("SECRET_NAME");

        SecretClient secretClient = new SecretClientBuilder()
            .vaultUrl(keyvaultURL)
            .credential(tokenCredentialBuilder.buildTokenCredential())
            .buildClient();
        KeyVaultSecret secret = secretClient.getSecret(secretName);
        System.out.println("Successfully got secret, secret = " + secret.getValue());
    }

}
