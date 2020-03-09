package com.api.resources;

import com.async.support.Computation;
import com.async.support.ExecutorsProvider;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


@Path("/")
public class GraphQLController {
    @Autowired
    private ExecutorsProvider executorsProvider;

    @Inject
    private GraphQL graphQL;

    @POST
    @Consumes({"application/xml,application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("graph")
    public void graphql(Map<String, Object> body, @Suspended AsyncResponse asyncResponse) {
        String query = (String) body.get("query");
        if (query == null) {
            query = "";
        }

        String operationName = (String) body.get("operationName");
        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        if (variables == null) {
            variables = new LinkedHashMap<>();
        }

        ExecutorService executorService = executorsProvider.getExecutorService();
        String finalQuery = query;
        Map<String, Object> finalVariables = variables;

        Computation.computeAsync(() -> executeGraphqlQuery(finalQuery, operationName, finalVariables), executorService)
                .thenApplyAsync(asyncResponse::resume, executorService)
                .exceptionally(error -> asyncResponse.resume(handleException(error)));
    }


    private Response handleException(Throwable ex) {
        return Response.serverError().entity(ex).build();
    }

    private Map<String, Object> executeGraphqlQuery(String query, String operationName, Map<String, Object> variables) throws IOException {

        //Context context = contextProvider.newContext();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                //      .context(context)
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        return executionResult.toSpecification();
    }

    @GET
    @Path("articles")
    public Response getArticles() {
        URI redirectedURL = UriBuilder.fromPath("/articlesInfo.html").build();
        return Response.seeOther(redirectedURL).build();
    }

}
