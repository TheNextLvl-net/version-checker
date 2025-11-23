import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.github.paper {
    exports net.thenextlvl.version.github.paper;

    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version.github;
    requires net.thenextlvl.version.plugin;
    requires net.thenextlvl.version;
    requires org.bukkit;
    requires org.slf4j;

    requires static org.jspecify;
}