package com.api.resources;

import com.async.support.Computation;
import com.async.support.ExecutorsProvider;
import com.context.Context;
import com.context.ContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


@RequestScoped
@Path("/")
public class GraphQLController {
	@Inject
    private ExecutorsProvider executorsProvider;

	@Inject
    GraphQL graphQL;

	@Inject
    private ObjectMapper objectMapper;
	@Inject
    private ContextProvider contextProvider;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("graph")
    public void graphql(Map<String, Object> body, @Suspended AsyncResponse asyncResponse) {

        String query = (String) body.get("query");
        if (query == null) {
            query = "";
        }

        Map<String, Object> variables = (Map<String, Object>) body.get("variables");
        if (variables == null) {
            variables = new LinkedHashMap<>();
        }

        ExecutorService executorService = executorsProvider.getExecutorService();
        String finalQuery = query;
        Map<String, Object> finalVariables = variables;

        Computation.computeAsync(() -> executeGraphqlQuery(finalQuery, finalVariables), executorService)
                .thenApplyAsync(asyncResponse::resume, executorService)
                .exceptionally(error -> asyncResponse.resume(handleException(error)));
    }

    private Response handleException(Throwable ex) {
        return Response.serverError().entity(ex).build();
    }

    private Map<String, Object> executeGraphqlQuery(String query, Map<String, Object> variables) throws IOException {

        Context context = contextProvider.newContext();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .context(context)
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);
        return executionResult.toSpecification();
    }


}
