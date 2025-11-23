import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.github {
    exports net.thenextlvl.version.github;

    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version;

    requires static org.jspecify;
}