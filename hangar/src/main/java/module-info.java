import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.version.hangar {
    exports net.thenextlvl.version.hangar;
    
    requires com.google.gson;
    requires java.net.http;
    requires net.thenextlvl.version;
    
    requires static org.jspecify;
}