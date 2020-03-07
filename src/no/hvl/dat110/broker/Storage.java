package no.hvl.dat110.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from user to set of topics subscribed to by user
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	// data structure for buffer implementation for disconnected users
	protected ConcurrentHashMap<String, Boolean> connected;
	protected ConcurrentHashMap<String, ArrayList<Message>> messageBuffers;
	
	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		//buffer implementation
		connected = new ConcurrentHashMap<String, Boolean>();
		messageBuffers = new ConcurrentHashMap<String, ArrayList<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		
		clients.put(user, new ClientSession(user, connection));
		//buffer implementation
		connected.put(user, true);
		messageBuffers.put(user, new ArrayList<Message>());
		//throw new UnsupportedOperationException(TODO.method());
		
	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage
		
		clients.remove(user);
		
		//throw new UnsupportedOperationException(TODO.method());
		
	}
	
	public void createTopic(String topic) {

		// TODO: create topic in the storage
		if(!subscriptions.containsKey(topic)) {
			Set<String> subscriberSet = ConcurrentHashMap.newKeySet();
		subscriptions.put(topic, subscriberSet);
		}
		//throw new UnsupportedOperationException(TODO.method());
	
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage
		
		subscriptions.remove(topic);

		// throw new UnsupportedOperationException(TODO.method());
		
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic
		if(!subscriptions.get(topic).contains(user)) {
			Set<String> subscriberSet = getSubscribers(topic);
			subscriberSet.add(user);
		}		
		
		// throw new UnsupportedOperationException(TODO.method());
		
	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic

		if(subscriptions.get(topic).contains(user)) {
			Set<String> subscriberSet = getSubscribers(topic);
			subscriberSet.remove(user);
		}
		
		// throw new UnsupportedOperationException(TODO.method());
	}

	// some extra methods to aid buffer implementation
	
	public void disconnectUser(String user) {
		connected.put(user, false);
		clients.get(user).disconnect();
	}

	public void reconnectUser(String user, Connection connection) {
		connected.put(user, true);
		clients.put(user, new ClientSession(user, connection));
	}

	public boolean isConnected(String user) {
		return connected.get(user);
	}

	public void addMessageToBuffer(String user, Message msg) {
		messageBuffers.get(user).add(msg);
	}

	public ArrayList<Message> getMessageBuffer(String user) {
		return messageBuffers.get(user);
	}

	public void emptyMessageBuffer(String user) {
		messageBuffers.get(user).clear();
	}
}
