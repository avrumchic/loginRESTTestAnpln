import Utils.RestClient;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class LoginRestTest {
    private final String URL = "https://app.mintigo.com/api/jwt-login/";
    private final String CUSTOMER_NAME = "Test24";
    private final String EMAIL = "mintigo20@gmail.com";
    private final String PASSWORD = "Anaplan21";

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
        client = new RestClient();
        String body = new JSONObject()
                .put("customerName", customer)
                .put("email", email)
                .put("password", password)
                .toString();
        response = client.post(URL, body);
        Assert.assertEquals(response.code(), status);
        Assert.assertEquals(response.message(), message);

        if (status == 200) {
            JSONObject jsonObject = new JSONObject(response.peekBody(5000).string());
            verifyJsonField(jsonObject, "user_email", email);
            verifyJsonField(jsonObject, "customer_name", customer);
        }
    }

    public void verifyJsonField(JSONObject jsonObject, String key, String value) throws IOException {
        Assert.assertEquals(jsonObject.get(key), value);


    }
}
