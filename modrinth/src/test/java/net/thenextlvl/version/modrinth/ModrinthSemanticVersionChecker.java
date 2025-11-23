package net.thenextlvl.version.modrinth;

import net.thenextlvl.version.SemanticVersion;

final class ModrinthSemanticVersionChecker extends ModrinthVersionChecker<SemanticVersion> {
    public ModrinthSemanticVersionChecker(String id) {
        super(id);
    }

    @Override
    public SemanticVersion getVersionRunning() {
        return new SemanticVersion(0, 0, 0, null);
    }

    @Override
    public SemanticVersion parseVersion(String version) {
        return SemanticVersion.parse(version.startsWith("v") ? version.substring(1) : version);
    }

    @Override
    public boolean isSupported(ModrinthVersion version) {
        return true;
    }
}
