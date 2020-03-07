package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

import static java.lang.Thread.sleep;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		Client client = new Client("temperature sensor", Common.BROKERHOST,Common.BROKERPORT);
		
		client.connect();
		
		
        for (int i = 0; i < COUNT; i++) {
            try {
                client.publish(Common.TEMPTOPIC, Integer.toString(sn.read()));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
		client.disconnect();
		// TODO - start

		// create a client object and use it to // client object is already created on line 17, only need to use it
		// - connect to the broker
		client.connect();
		// - publish the temperature(s)
		for(int i = 0; i < COUNT; i++) {
			
			client.publish(Common.TEMPTOPIC, String.valueOf(sn.read()));
			
		} try {
			Thread.sleep(1000);
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// - disconnect from the broker
		client.disconnect();

		// TODO - end

		System.out.println("Temperature device stopping ... ");

		// throw new UnsupportedOperationException(TODO.method());

	}
}
