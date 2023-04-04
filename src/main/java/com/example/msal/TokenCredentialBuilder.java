package com.example.msal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientAssertionCredentialBuilder;

public class TokenCredentialBuilder {

    public TokenCredential buildTokenCredential() {
        return new ClientAssertionCredentialBuilder()
            .clientId(getClientId())
            .tenantId(getTenantId())
            .clientAssertion(getClientAssertionSupplier())
            .build();
    }

    private String getTenantId() {
        return System.getenv().get("AZURE_TENANT_ID");
    }

    private String getClientId() {
        return System.getenv().get("AZURE_CLIENT_ID");
    }

    private Supplier<String> getClientAssertionSupplier() {
        return () -> getClientAssertion();
    }

    private String getClientAssertion() {
        try {
            return new String(
                Files.readAllBytes(Paths.get(System.getenv().get("AZURE_FEDERATED_TOKEN_FILE"))),
                StandardCharsets.UTF_8
            );
        } catch (Exception e) {
            throw new ClientAssertionNotFoundException(e);
        }
    }
}
