import Utils.RestClient;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginRestTest {
    Logger logger = Logger.getLogger(LoginRestTest.class.getName());

    private final String URL = "https://app.mintigo.com/api/jwt-login/";

    private RestClient client;
    private Response response;


    @DataProvider(name = "data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {"Test24", "mintigo20@gmail.com", "Anaplan21", 200, "OK"},
                {"Test241", "mintigo20@gmail.com", "Anaplan21", 400, "Bad Request"},
                {"Test24", "mintigo20@gmail.com1", "Anaplan21", 400, "Bad Request"},
                {"Test24", "mintigo20@gmail.com", "Anaplan211", 400, "Bad Request"},
                {"Test24", "mintigo20@gmail.com", "Anaplan21", 200, "OK"}
        };
    }

    @Test(dataProvider = "data-provider")
    public void LoginTest(String customer, String email, String password, int status, String message) throws IOException {
        logger.log(Level.INFO, "Login to: " + URL);
        client = new RestClient();
        String body = new JSONObject()
                .put("customerName", customer)
                .put("email", email)
                .put("password", password)
                .toString();
        response = client.post(URL, body);
        JSONObject jsonObject = new JSONObject(client.getStringBody());
        Assert.assertEquals(response.code(), status);
        Assert.assertEquals(response.message(), message);

        if (status == 200) {
            verifyJsonField(jsonObject, "user_email", email);
            verifyJsonField(jsonObject, "customer_name", customer);
            logger.log(Level.INFO, String.format("Login test passed with credentials: {%s, %s, %s}", customer, email, password));
        } else {
            logger.log(Level.INFO, String.format("Login test passed wrong credentials: {%s, %s, %s}", customer, email, password));
        }
    }

    public void verifyJsonField(JSONObject jsonObject, String key, String value) throws IOException {
        Assert.assertEquals(jsonObject.getJSONObject("user").get(key), value);


    }
}
