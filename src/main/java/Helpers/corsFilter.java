package Helpers;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;

public class corsFilter {
    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();

    public corsFilter() {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");

    }

    public final void apply() {
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                corsHeaders.forEach(response::header);
            }
        };
        Spark.after(filter);
    }
}
