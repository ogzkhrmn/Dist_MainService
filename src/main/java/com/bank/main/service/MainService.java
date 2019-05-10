package com.bank.main.service;

import com.bank.main.model.ResponseModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/main")
public class MainService {

    @POST
    @Path("/control")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseModel isAlive() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setSuccess(true);
        return responseModel;
    }

}
