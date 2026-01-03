package net.thenextlvl.version.modrinth.paper;

import io.papermc.paper.ServerBuildInfo;
import net.thenextlvl.version.Version;
import net.thenextlvl.version.modrinth.ModrinthVersion;
import net.thenextlvl.version.modrinth.ModrinthVersionChecker;
import net.thenextlvl.version.plugin.PluginVersionChecker;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

/**
 * The PaperModrinthVersionChecker class is an abstract class that provides methods
 * for retrieving and checking the latest supported version of a specific plugin in a Paper Modrinth project.
 *
 * @param <V> the type parameter for the version
 */
public abstract class PaperModrinthVersionChecker<V extends Version> extends ModrinthVersionChecker<V> implements PluginVersionChecker {
    private final V versionRunning;
    private final Plugin plugin;

    @SuppressWarnings("UnstableApiUsage")
    public PaperModrinthVersionChecker(Plugin plugin, String id) {
        super(id);
        this.plugin = plugin;
        this.versionRunning = Objects.requireNonNull(
                parseVersion(plugin.getPluginMeta().getVersion()),
                "Failed to parse plugin version running"
        );
    }

    @Override
    public void checkVersion() {
        retrieveLatestSupportedVersion().thenAccept(optional -> optional.ifPresentOrElse(this::printVersionInfo,
                () -> retrieveLatestVersion().thenAccept(this::printUnsupportedInfo).exceptionally(throwable -> {
                    plugin.getComponentLogger().warn("There are no compatible releases of this plugin for your server version");
                    return null;
                })
        )).exceptionally(throwable -> {
            var t = throwable.getCause() != null ? throwable.getCause() : throwable;
            plugin.getComponentLogger().error("Version check failed", t);
            return null;
        });
    }

    @Override
    public void checkLatestVersion() {
        retrieveLatestVersion().thenAccept(this::printVersionInfo).exceptionally(throwable -> {
            plugin.getComponentLogger().warn("There are no compatible releases for this plugin for your server version");
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
            logger.warn("Update at https://modrinth.com/project/{}", getId());
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
            logger.warn("Update at https://modrinth.com/project/{}/?version={}&loader={}#download", getId(),
                    plugin.getServer().getMinecraftVersion(), getLoader());
            logger.warn("Do not test in production and always make backups before updating");
        } else logger.warn("You are running a snapshot version of {}", plugin.getName());
    }

    /**
     * Retrieves the loader type associated with this checker.
     *
     * @return the server loader type
     */
    public String getLoader() {
        return "paper";
    }

    @Override
    protected String getVersionQuery() {
        return "version?loaders=[\"" + getLoader() + "\"]&game_versions=[\"" + plugin.getServer().getMinecraftVersion() + "\"]";
    }

    @Override
    public boolean isSupported(ModrinthVersion version) {
        return version.gameVersions().contains(plugin.getServer().getMinecraftVersion())
                && version.loaders().contains(getLoader());
    }

    @Override
    public V getVersionRunning() {
        return versionRunning;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
