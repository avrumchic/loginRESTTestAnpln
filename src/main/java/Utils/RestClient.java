package Utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClient {
    Logger logger = Logger.getLogger(RestClient.class.getName());
    OkHttpClient client;

    public RestClient() {
        client = new OkHttpClient();
    }

    public Response post(String url, String reqBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), reqBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed sending post request");
            return null;
        }
    }


    public static void main(String[] args) {
    }
}
