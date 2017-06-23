package ro.upb.cs.thesis.icarte.utils;

import ro.upb.cs.thesis.icarte.jsonEntityObjects.Client;
import ro.upb.cs.thesis.icarte.jsonEntityObjects.Response;

public class PaymentResponseObject {

    private Client client;
    private Response response;
    private String response_type;

    public PaymentResponseObject(Client client, Response response, String response_type) {
        this.client = client;
        this.response = response;
        this.response_type = response_type;
    }
    public Client getClient() {
        return client;
    }
    public Response getResponse() {
        return response;
    }
    public String getResponse_type() {
        return response_type;
    }
}
