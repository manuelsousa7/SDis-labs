package example.ws;

import javax.xml.ws.Endpoint;

public class PingMain {

	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s url%n", PingMain.class.getName());
			return;
		}

		String url = args[0];
		Endpoint endpoint = null;
		try {
			PingPortImpl port = new PingPortImpl();
			endpoint = Endpoint.create(port);

			// publish end point
			System.out.printf("Starting %s%n", url);
			endpoint.publish(url);

			// wait
			System.out.println("Awaiting connections");
			System.out.println("Press enter to shutdown");
			System.in.read();

		} catch (Exception e) {
			System.out.printf("Caught exception: %s%n", e);
			e.printStackTrace();

		} finally {
			try {
				if (endpoint != null) {
					// stop end point
					endpoint.stop();
					System.out.printf("Stopped %s%n", url);
				}
			} catch (Exception e) {
				System.out.printf("Caught exception when stopping: %s%n", e);
			}
		}

	}

}
