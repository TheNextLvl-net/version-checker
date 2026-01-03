package net.thenextlvl.version.modrinth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ModrinthTest {
    private static final ModrinthSemanticVersionChecker versionChecker = new ModrinthSemanticVersionChecker("gBIw3Gvy");

    @Test
    void testLatestVersion() {
        Assertions.assertNotNull(versionChecker.retrieveLatestVersion().join());
        Assertions.assertTrue(versionChecker.getLatestVersion().isPresent());
        System.out.printf("Latest version: %s%n", versionChecker.getLatestVersion().get());
    }

    @Test
    void testVersions() {
        Assertions.assertNotNull(versionChecker.retrieveVersions().join());
        Assertions.assertFalse(versionChecker.getVersions().isEmpty());
        System.out.printf("Versions: %s%n", versionChecker.getVersions());
    }
}
