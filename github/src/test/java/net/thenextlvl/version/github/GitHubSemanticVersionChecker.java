package net.thenextlvl.version.github;

import net.thenextlvl.version.SemanticVersion;

final class GitHubSemanticVersionChecker extends GitHubVersionChecker<SemanticVersion> {
    public GitHubSemanticVersionChecker(String owner, String repository) {
        super(owner, repository);
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
    public boolean isSupported(Release version) {
        return true;
    }
}
