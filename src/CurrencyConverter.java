import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class CurrencyConverter {
    //TODO maybe handle exceptions
    private static Double getCurrencyRate(String currency, boolean isBaseCurrency) throws Exception {
        if (currency.equals("PLN")){
            return 1d;
        }
        String table = isBaseCurrency ? "a" : "c";
        String url = "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + currency + "/?format=json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest currencyRateRequest = HttpRequest.newBuilder(URI.create(url)).build();

        String currencyResponse = client.send(currencyRateRequest, HttpResponse.BodyHandlers.ofString()).body();
        JSONParser parser = new JSONParser();
        JSONObject responseJSON = (JSONObject) parser.parse(currencyResponse);
        JSONArray responseJSONArray = (JSONArray) responseJSON.get("rates");
        JSONObject subResponseJSON = (JSONObject) responseJSONArray.get(0);
        String whichWay = isBaseCurrency ? "mid" : "ask";
        return (Double) subResponseJSON.get(whichWay);
    }

    public static Double getCurrenciesExchangeRate(String currencyFrom, String currencyTo) throws Exception {
        Double rateFrom = getCurrencyRate(currencyFrom, true);
        Double rateTo = getCurrencyRate(currencyTo, false);
        return  rateFrom / rateTo;
    }

    public static Double exchangeMoney(String currencyFrom, String currencyTo, Double value) throws Exception {
        return getCurrenciesExchangeRate(currencyFrom, currencyTo) * value;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getCurrencyRate("EUR", false));
        Double a = exchangeMoney("EUR", "USD", 2987.5);
        System.out.println((a));
    }
}
