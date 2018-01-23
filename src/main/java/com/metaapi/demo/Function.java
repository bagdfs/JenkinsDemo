package com.metaapi.demo;

import java.sql.SQLException;
import java.util.*;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.genericapi.dbadaptor.DbAdaptor;
import com.genericapi.dbadaptor.DbAdaptorFactory;
import com.microsoft.azure.serverless.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/hello". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/hello
     * 2. curl {your host}/api/hello?name=HTTP%20Query
     */
	public static DbAdaptorFactory s = new DbAdaptorFactory(); 
	public static DbAdaptor f = s.getdbtype("D:\\home\\site\\wwwroot\\ConfigFile.properties"); 
	//changed below fn name
    @FunctionName("getdoc")
    //changed the method name 
    
    public HttpResponseMessage<String> getdoc(
            @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws ClassNotFoundException, SQLException {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);
        
        String abc = f.getdocbyid(f, name);
        

        if (name == null) {
            return request.createResponse(400, "Please pass a name on the query string or in the request body");
        } else {
            return request.createResponse(200, "Hello, " + abc);
        }
    }
}
