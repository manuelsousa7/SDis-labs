package example.ws.cli;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.Map;

import javax.xml.ws.BindingProvider;

// classes generated by wsimport from WSDL
import example.ws.PingFault_Exception;
import example.ws.PingPortType;
import example.ws.PingService;

public class PingClient {

	public static void main(String[] args) {
		PingService service = new PingService();
		PingPortType port = service.getPingPort();

		BindingProvider bindingProvider = (BindingProvider) port;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();

		// get/set end point address
		Object url = null;
		if (args.length < 1) {
			url = requestContext.get(ENDPOINT_ADDRESS_PROPERTY);
		} else {
			url = args[0];
			requestContext.put(ENDPOINT_ADDRESS_PROPERTY, url);
		}
		System.out.printf("Remote call to %s ...%n", url);

		try {
			String result = port.ping("friend");
			System.out.println(result);

		} catch (PingFault_Exception pfe) {
			System.out.println("Caught: " + pfe);
		}
	}

}
