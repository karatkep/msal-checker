package com.example.msal;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;

public class TokenCredentialBuilder {
    public TokenCredential buildTokenCredential() {
        return new ManagedIdentityCredentialBuilder().build();
    }
}
