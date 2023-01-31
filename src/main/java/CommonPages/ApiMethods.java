package CommonPages;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Const;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiMethods {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    static String baseUrl = "";


    public static String getSingleApiResponseBodyMethod(String eventId, String getResultData) {

        Response response = null;
        String data = null;

        try {
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());
            response = httpRequest.get(Const.getAPIParameter() + eventId);
            log.debug(String.valueOf(response.statusCode()));
            List<String> listData = response.jsonPath().get("results." + getResultData);
            data = listData.toString().substring(1, listData.toString().length() - 1);
            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ArrayList<String> getFirstTenApiDataMethod(String getData) {

        Response response = null;
        try {
            ArrayList<String> testArrayList = new ArrayList<>();
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());
            response = httpRequest.get(Const.getAPIParameter());

            log.debug(String.valueOf(response.statusCode()));
            List<HashMap<String, Object>> resultsList = response.jsonPath().getList("results");

            for (int i = 0; i < 10; i++) {
                HashMap<String, Object> resultIndx = resultsList.get(i);
                String strResultList = (String) resultIndx.get(getData);
                testArrayList.add(strResultList);
            }
            return testArrayList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static int getAllApiDataResponseCode() {

        Response response = null;

        try {
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());
            response = httpRequest.get(Const.getAPIParameter());
            int statusCode = response.getStatusCode();
            return statusCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getAllApiCountWithParam(String param,String paramValue) {

        Response response = null;
        try {
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());

            response = httpRequest.queryParam(param, paramValue).get(Const.getAPIParameter());

            int getTotal= response.jsonPath().getInt("meta.pagination.total_elements");
            return  getTotal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
    public static ArrayList<String> getSpecificNumberOfApiDataWithParamMethod(int apiCount,String getData,String param,String paramValue) {

        Response response = null;
        try {
            ArrayList<String> testArrayList = new ArrayList<>();
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());
            response = httpRequest.queryParam(param, paramValue).get(Const.getAPIParameter());

            log.debug(String.valueOf(response.statusCode()));
            List<HashMap<String, Object>> resultsList = response.jsonPath().getList("results");

            for (int i = 0; i < apiCount; i++) {
                HashMap<String, Object> resultIndx = resultsList.get(i);
                String strResultList = (String) resultIndx.get(getData);
                testArrayList.add(strResultList);
            }
            return testArrayList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static ArrayList<String> getSpecificNumberOfApiDataWithParamMethod(int apiCount,
       String getData,String param,String paramValue,String param2,String paramValue2) {

        Response response = null;
        try {
            ArrayList<String> testArrayList = new ArrayList<>();
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());
            response = httpRequest.queryParam(param, paramValue)
                        .queryParam(param2,paramValue2)
                    .get(Const.getAPIParameter());

            log.debug(String.valueOf(response.statusCode()));
            List<HashMap<String, Object>> resultsList = response.jsonPath().getList("results");

            for (int i = 0; i < apiCount; i++) {
                HashMap<String, Object> resultIndx = resultsList.get(i);
                String strResultList = (String) resultIndx.get(getData);
                testArrayList.add(strResultList);
            }
            return testArrayList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static int getAllApiCount() {

        Response response = null;
        try {
            RestAssured.baseURI = baseUrl;
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.contentType(Const.getAppType());
            httpRequest.header(Const.getAuth(), Const.getAccessToken());

            response = httpRequest.get(Const.getAPIParameter());

            return response.jsonPath().getInt("meta.pagination.total_elements");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
