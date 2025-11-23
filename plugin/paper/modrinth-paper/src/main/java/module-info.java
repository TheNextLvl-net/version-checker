import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.modrinth.paper {
    exports net.thenextlvl.version.modrinth.paper;
    
    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version.modrinth;
    requires net.thenextlvl.version.plugin;
    requires net.thenextlvl.version;
    requires org.bukkit;
    requires org.slf4j;
    
    requires static org.jspecify;
}