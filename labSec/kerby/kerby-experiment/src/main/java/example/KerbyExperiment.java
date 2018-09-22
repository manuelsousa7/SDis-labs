package example;

import pt.ulisboa.tecnico.sdis.kerby.CipheredView;
import pt.ulisboa.tecnico.sdis.kerby.SecurityHelper;
import pt.ulisboa.tecnico.sdis.kerby.SessionKey;
import pt.ulisboa.tecnico.sdis.kerby.SessionKeyAndTicketView;
import pt.ulisboa.tecnico.sdis.kerby.cli.KerbyClient;
import sun.security.krb5.internal.Ticket;
import sun.security.util.AuthResources_ko;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;


public class KerbyExperiment {
    private static final String VALID_CLIENT_NAME = "alice@CXX.binas.org";
    private static final String VALID_CLIENT_PASSWORD = "Zd8hqDu23t";
    private static final String VALID_SERVER_NAME = "binas@CXX.binas.org";
    private static final String VALID_SERVER_PASSWORD = "MTbvC3";
    private static final int VALID_DURATION = 30;
    private static SecureRandom randomGenerator = new SecureRandom();
    public static void main(String[] args) throws Exception {
        System.out.println("Hi!");

        System.out.println();

        // receive arguments
        System.out.printf("Received %d arguments%n", args.length);

        System.out.println();

        // load configuration properties
        try {
            InputStream inputStream = KerbyExperiment.class.getClassLoader().getResourceAsStream("config.properties");
            // variant for non-static methods:
            // InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            System.out.printf("Loaded %d properties%n", properties.size());

        } catch (IOException e) {
            System.out.printf("Failed to load configuration: %s%n", e);
        }


        System.out.println();


        final Key clientKey = SecurityHelper.generateKeyFromPassword(VALID_CLIENT_PASSWORD);
        final Key serverKey = SecurityHelper.generateKeyFromPassword(VALID_SERVER_PASSWORD);
        long nounce = randomGenerator.nextLong();

        Properties testProps = new Properties();
        try {
            testProps.load(KerbyExperiment.class.getResourceAsStream("/test.properties"));
            System.out.println("Loaded test properties:");
            System.out.println(testProps);
        } catch (IOException e) {
            throw e;
        }
        String wsURL = testProps.getProperty("ws.url");
        KerbyClient client = new KerbyClient(wsURL);
        SessionKeyAndTicketView result = client.requestTicket(VALID_CLIENT_NAME, VALID_SERVER_NAME, nounce, VALID_DURATION);

        CipheredView cipheredSessionKey = result.getSessionKey();
        CipheredView cipheredTicket = result.getTicket();

        SessionKey sessionKey = new SessionKey(cipheredSessionKey, clientKey);

        pt.ulisboa.tecnico.sdis.kerby.Ticket ticket = new pt.ulisboa.tecnico.sdis.kerby.Ticket(cipheredTicket, serverKey);
        //long timeDiff = ticket.getTime2().getTime() - ticket.getTime1().getTime();


        System.out.println("Bye!");
    }




    private Key getKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return SecurityHelper.generateKeyFromPassword(password);
    }
}
