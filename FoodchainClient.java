import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class FoodchainClient {
    private String baseUrl;
    private String apiKey;

    public FoodchainClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private JSONObject makeRequest(String endpoint, String method, JSONObject data, Map<String, String> params) throws Exception {
        URL url = new URL(this.baseUrl + endpoint + buildParamsString(params));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        if (this.apiKey != null) {
            conn.setRequestProperty("API-KEY", this.apiKey);
        }
        conn.setDoOutput(true);

        if (data != null && method.equals("POST")) {
            try (OutputStream os = conn.getOutputStream()) {
                os.write(data.toString().getBytes());
                os.flush();
            }
        }

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            return new JSONObject(response.toString());
        }
    }

    private String buildParamsString(Map<String, String> params) {
        if (params == null || params.isEmpty()) return "";
        StringBuilder paramsString = new StringBuilder("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return paramsString.toString();
    }

    public JSONObject createLot(JSONObject lotData) throws Exception {
        return makeRequest("/api/v1/lot/insert/", "POST", lotData, null);
    }

    public JSONObject createOrder(JSONObject orderData) throws Exception {
        return makeRequest("/api/v1/order/insert/", "POST", orderData, null);
    }

    public JSONObject getProcedure(String procedureId, Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/procedure/" + procedureId + "/", "GET", null, params);
    }

    public JSONObject getLotsByProduct(String productId, Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/lot/product/" + productId + "/", "GET", null, params);
    }

    public JSONObject createChainedProcessing(String procedureId, JSONObject processData) throws Exception {
        return makeRequest("/api/v1/procedure/" + procedureId + "/", "POST", processData, null);
    }

    public JSONObject listOrders(String status, Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/order/list/" + status + "/", "GET", null, params);
    }

    public JSONObject listWarehouses(Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/warehouse/list/", "GET", null, params);
    }

    public JSONObject confirmOrder(JSONObject confirmationData) throws Exception {
        return makeRequest("/api/v1/order/confirm/", "POST", confirmationData, null);
    }

    public JSONObject createLabels(JSONObject labelData) throws Exception {
        return makeRequest("/api/v1/label/insert/", "POST", labelData, null);
    }

    public JSONObject listLabels(Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/label/list/", "GET", null, params);
    }

    public JSONObject listProducts(Map<String, String> params) throws Exception {
        return makeRequest("/api/v1/product/list/", "GET", null, params);
    }
}

