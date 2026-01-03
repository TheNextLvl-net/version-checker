package net.thenextlvl.version.github.paper;

import io.papermc.paper.ServerBuildInfo;
import net.thenextlvl.version.Version;
import net.thenextlvl.version.github.GitHubVersionChecker;
import net.thenextlvl.version.github.Release;
import net.thenextlvl.version.plugin.PluginVersionChecker;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public abstract class PaperGitHubVersionChecker<V extends Version> extends GitHubVersionChecker<V> implements PluginVersionChecker {
    private final V versionRunning;
    private final Plugin plugin;

    @SuppressWarnings("UnstableApiUsage")
    public PaperGitHubVersionChecker(Plugin plugin, String owner, String repository) {
        super(owner, repository);
        this.versionRunning = Objects.requireNonNull(
                parseVersion(plugin.getPluginMeta().getVersion()),
                "Failed to parse plugin version running"
        );
        this.plugin = plugin;
    }

    @Override
    public void checkLatestVersion() {
        retrieveLatestVersion().thenAccept(this::printVersionInfo).exceptionally(throwable -> {
            plugin.getComponentLogger().warn("There are no compatible releases of this plugin for your server version");
            return null;
        });
    }

    @Override
    public void checkVersion() {
        retrieveLatestSupportedVersion().thenAccept(optional -> optional.ifPresentOrElse(this::printVersionInfo,
                () -> retrieveLatestVersion().thenAccept(this::printUnsupportedInfo).exceptionally(throwable -> {
                    plugin.getComponentLogger().warn("There are no compatible releases of this plugin for your server version");
                    return null;
                })
        )).exceptionally(throwable -> {
            plugin.getComponentLogger().error("Version check failed", throwable);
            return null;
        });
    }

    private void printUnsupportedInfo(V version) {
        var logger = plugin.getComponentLogger();
        var buildInfo = ServerBuildInfo.buildInfo();
        if (version.equals(versionRunning)) {
            logger.warn("{} seems to be unsupported by {} version {}",
                    buildInfo.minecraftVersionId(), plugin.getName(), versionRunning);
        } else if (version.compareTo(versionRunning) > 0) {
            logger.warn("A new version for {} is available but {} seems to be unsupported",
                    plugin.getName(), buildInfo.minecraftVersionId());
            logger.warn("You are running version {}, the latest version is {}", versionRunning, version);
            logger.warn("Update at https://github.com/{}/{}", getOwner(), getRepository());
            logger.warn("Do not test in production and always make backups before updating");
        } else {
            logger.warn("You are running a snapshot version of {}", plugin.getName());
        }
    }

    private void printVersionInfo(V version) {
        var logger = plugin.getComponentLogger();
        if (version.equals(versionRunning)) {
            logger.info("You are running the latest version of {}", plugin.getName());
        } else if (version.compareTo(versionRunning) > 0) {
            logger.warn("An update for {} is available", plugin.getName());
            logger.warn("You are running version {}, the latest version is {}", versionRunning, version);
            logger.warn("Update at https://github.com/{}/{}", getOwner(), getRepository());
            logger.warn("Do not test in production and always make backups before updating");
        } else logger.warn("You are running a snapshot version of {}", plugin.getName());
    }

    @Override
    public boolean isSupported(Release version) {
        return true;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public V getVersionRunning() {
        return versionRunning;
    }
}
