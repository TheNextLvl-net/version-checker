import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.plugin {
    exports net.thenextlvl.version.plugin;

    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version;
    requires org.bukkit;
    requires org.slf4j;

    requires static org.jspecify;
}