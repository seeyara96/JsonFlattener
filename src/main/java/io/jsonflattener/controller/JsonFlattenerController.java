package io.jsonflattener.controller;

import io.jsonflattener.service.FlattenerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/json")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JsonFlattenerController {
    private FlattenerService flattenerService;

    public JsonFlattenerController(FlattenerService service) {
        flattenerService = service;
    }

    @POST
    @Path("/flatten")
    public Response flattenJson(String input) throws Exception {
        return Response.ok(flattenerService.flattenJson(input)).build();
    }

    @POST
    @Path("/unflatten")
    public Response unflattenJson(String input) throws Exception {
        return Response.ok(flattenerService.unflattenJson(input)).build();
    }
}
