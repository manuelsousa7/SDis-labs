package ttt;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TTTServer {

	public static void main(String[] args){
		int registryPort = 1099;
        System.out.println("Main OK");
        TTTService tickTackToe;
		try {
			tickTackToe = new TTT();
			Registry reg = LocateRegistry.createRegistry(registryPort);
	        reg.rebind("Tick_Tack_Toe", tickTackToe);
	        System.out.println("ShapeList server ready");
	        System.out.println("Awaiting connections");
	        System.out.println("Press enter to shutdown");
	        System.in.read();
	        System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
