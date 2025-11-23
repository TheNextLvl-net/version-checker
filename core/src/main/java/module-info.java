import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version {
    exports net.thenextlvl.version;

    requires com.google.gson;
    requires java.net.http;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}