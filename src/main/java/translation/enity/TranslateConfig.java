package translation.enity;

import lombok.Data;

import java.util.Date;

@Data
public class TranslateConfig {
    private String IG;
    private String IID;
    private String cookie;
    private Long key;
    private String token;
    private Long tokenTs;
    private Long tokenExpiryInterval;
    private Integer count;

    public boolean isTokenExpired() {
        return new Date().getTime() - tokenTs > tokenExpiryInterval;
    }
}
