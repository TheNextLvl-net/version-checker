package net.thenextlvl.version;

import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface VersionChecker<N, V extends Version> {
    /**
     * Retrieves the version of the currently running software.
     *
     * @return the version of the currently running software
     */
    @Contract(pure = true)
    V getVersionRunning();

    /**
     * Parses a version of type N into a version of type V.
     *
     * @param version the version to parse
     * @return the parsed version of type V
     */
    @Contract(value = "_ -> new", pure = true)
    V parseVersion(N version);

    /**
     * Parses a version of type String into a version of type V.
     *
     * @param version the version to parse
     * @return the parsed version of type V
     */
    @Contract(value = "_ -> new", pure = true)
    V parseVersion(String version);

    /**
     * Checks if a version is supported.
     *
     * @param version the version to check
     * @return true if the version is supported, false otherwise
     */
    @Contract(pure = true)
    boolean isSupported(N version);

    /**
     * Asynchronously retrieves the latest available version.
     *
     * @return a CompletableFuture containing the latest version
     */
    @CheckReturnValue
    CompletableFuture<V> retrieveLatestVersion();

    /**
     * Retrieves the latest supported version of the software asynchronously.
     *
     * @return a CompletableFuture containing the latest supported version
     */
    @CheckReturnValue
    CompletableFuture<Optional<V>> retrieveLatestSupportedVersion();

    /**
     * Retrieves all available versions asynchronously.
     *
     * @return a CompletableFuture containing a Set of all versions
     */
    @CheckReturnValue
    CompletableFuture<Set<V>> retrieveVersions();

    /**
     * Retrieves a set of all supported versions that have been queried up to this point.
     *
     * @return an unmodifiable {@code Set} of supported versions
     */
    @CheckReturnValue
    Set<V> getSupportedVersions();

    /**
     * Retrieves all available versions that have been queried up to this point.
     *
     * @return an unmodifiable {@code Set} of all available versions
     */
    @CheckReturnValue
    Set<V> getVersions();

    /**
     * Retrieves the latest supported version of the software, if available.
     *
     * @return an {@code Optional} containing the latest supported version
     * if available, or an empty {@code Optional} if no supported version
     * is found or if it has not been queried yet.
     */
    @CheckReturnValue
    Optional<V> getLatestSupportedVersion();

    /**
     * Retrieves the latest available version of the software.
     *
     * @return an {@code Optional} containing the latest version if available,
     * or an empty {@code Optional} if there is no available version
     * or if it has not been queried yet.
     */
    @CheckReturnValue
    Optional<V> getLatestVersion();
}
