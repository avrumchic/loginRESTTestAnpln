package Utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClient {
    Logger logger = Logger.getLogger(RestClient.class.getName());

    private OkHttpClient client;
    private String stringBody;

    public RestClient() {
        client = new OkHttpClient();
    }

    public String getStringBody() {
        return stringBody;
    }

    /**
     * sends a post call to the required URL
     * @param url
     * @param reqBody
     * @return
     */
    public Response post(String url, String reqBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), reqBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //because response is closed after return, need to get body before and save it
            stringBody = response.peekBody(2048).string();
            return response;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed sending post request", e);
            return null;
        }
    }


    public static void main(String[] args) {
    }
}
