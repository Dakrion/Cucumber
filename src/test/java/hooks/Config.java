package hooks;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Config {
    Browser browser;
    Base base;

    @FieldDefaults(level = AccessLevel.PUBLIC)
    static class Browser {
        String name;
        String version;
        String size;
        Boolean enableVNC;
    }

    @FieldDefaults(level = AccessLevel.PUBLIC)
    static class Base {
        String url;
        Long timeout;
    }
}
