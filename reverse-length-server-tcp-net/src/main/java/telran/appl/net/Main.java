package telran.appl.net;

import telran.net.*;

public class Main {
    private static final int PORT = 4000;
    public static void main(String[] args) {
        Protocol protocol = new Protocol() {
            @Override
            public Response getResponse(Request request) {
                String requestData = request.requestData();
                String requestType = request.requestType();
                ResponseCode responseCode = ResponseCode.OK;
                String responseData = switch (requestType) {
                    case "length" -> String.valueOf(requestData.length());
                    case "reverse" -> new StringBuilder(requestData).reverse().toString();
                    default -> {
                        responseCode = ResponseCode.WRONG_TYPE;
                        yield responseCode.toString();
                    }
                };
                return new Response(responseCode, responseData);

            }
        };

        TCPServer server = new TCPServer(protocol, PORT);
        server.run();
    }
}