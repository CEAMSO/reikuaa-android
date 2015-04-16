package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by willtallpear on 23/02/15.
 */
public class AccessToken {
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
