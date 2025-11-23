import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.modrinth {
    exports net.thenextlvl.version.modrinth;
    
    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version;
    
    requires static org.jspecify;
}