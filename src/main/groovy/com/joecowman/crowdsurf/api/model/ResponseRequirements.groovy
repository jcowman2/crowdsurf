package com.joecowman.crowdsurf.api.model

class ResponseRequirements {
    boolean autoCallbackAfterTimeout
    int timeoutSeconds
    String timeoutCommand //The command to be returned after the timeout interval has occurred
}
