package com.cosc516;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.*;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.ThroughputProperties;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.models.CosmosDatabaseProperties;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
//import com.azure.cosmos.models.CosmosItemProperties;
//import com.azure.cosmos.implementation.CosmosItemProperties;
//import com.azure.data.cosmos.CosmosItemProperties;
import com.azure.cosmos.models.PartitionKey;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;

import javax.lang.model.util.ElementScanner14;


public class Final {
	/**
	 * Cosmos DB endpoint
	 */
	// TODO: Fill in your Cosmos DB URI
	private static final String URI = "https://cosc516finalexam.documents.azure.com:443/";

	/**
	 * Cosmos DB primary key
	 */
	// TODO: Fill in your Cosmos DB primary key
	private static final String PRIMARY_KEY = "s3sBcHP30VUzyfCAmkNVIrtf5fl4r10O0HSBLydkS8oeizZ5XHTmcAnsL5TRhQSAiI6d9mcDAdFKACDbnPNkeg==";

	/**
	 * Cosmos DB client
	 */
	private CosmosClient cosmosClient;

	/**
	 * Cosmos DB database
	 */
	public CosmosDatabase cosmosDatabase;

	/**
	 * Cosmos DB containers
	 */
	public CosmosContainer stateContainer;
	public CosmosContainer eventContainer;

	/**
	 * Main method
	 * 
	 * @param args
	 *             no arguments required
	 */
	public static void main(String[] args) throws Exception {
		Final finaldb = new Final();
		try {
			finaldb.connect();
			//finaldb.create();

			//finaldb.load();
			//finaldb.update();
			//finaldb.query1(949);
			//finaldb.query2(1);
			//finaldb.query3("2022-12-17 05:30:00", "2022-12-17 15:00:00", 2);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			finaldb.close();
		}
	}

	/**
	 * Connects to Azure Cosmos DB and returns the client.
	 * 
	 * @return
	 *         CosmosClient
	 */
	public CosmosClient connect() throws Exception {
		// TODO: Connect to Azure Cosmos DB
		System.out.println("\nConnecting to database.");
		cosmosClient = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildClient();
		cosmosDatabase = cosmosClient.getDatabase("final516");
		//customerContainer = cosmosDatabase.getContainer("customers");
		//ordersContainer = 
		return cosmosClient;
	}


	private void create() throws Exception{
		System.out.println("\nCreating Database and Containers.");
		CosmosAsyncClient client = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildAsyncClient();

		// Create a database
        CosmosDatabaseProperties databaseDefinition = new CosmosDatabaseProperties("final516");
        CosmosDatabaseProperties database = client.createDatabase(databaseDefinition).block().getProperties();
        System.out.println("Created database: " + database.getId());

		// Create a container
        CosmosContainerProperties stateContainerDefinition = new CosmosContainerProperties("state", "/id");
        CosmosContainerResponse stateContainer = client.getDatabase(database.getId()).createContainer(stateContainerDefinition).block();
        System.out.println("Created container: " + stateContainer.getProperties().getId());

		CosmosContainerProperties eventContainerDefinition = new CosmosContainerProperties("event", "/eventid");
        CosmosContainerResponse eventContainer = client.getDatabase(database.getId()).createContainer(eventContainerDefinition).block();
        System.out.println("Created container: " + eventContainer.getProperties().getId());

	}

	/**
	 * Closes connection to the database.
	 */
	public void close() {
		// TODO: Close the connection to the database
		System.out.println("\nClosing connection.");
		cosmosClient.close();
	}


	/**
	 * Loads data into database.
	 * 
	 * @throws FileNotFoundException
	 */
	// public void load() throws Exception {


	// 	System.out.println("\nLoading Data.");

	// 	// Create a CosmosAsyncClient object
	// 	//CosmosAsyncClient client = CosmosClientBuilder.buildAsyncClient();
	// 	CosmosAsyncClient client = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildAsyncClient();



	// 	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 	ArrayList<GameState>states = new ArrayList<>();
		
	// 	try (BufferedReader reader = new BufferedReader(new FileReader("gamestate.csv"))) {
	// 	String line;
	// 	System.out.println("Before while");
	// 	int lineCount = 0;
	// 	while ((line = reader.readLine()) != null) {
	// 		if (lineCount == 0)
	// 			continue;
	// 		else
	// 		{
	// 			String[] values = line.split(",");
	// 			GameState state = new GameState(Integer.parseInt(values[0]), date.parse(values[1]), Integer.parseInt(values[2]), values[3], values[4], Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]));
	// 			//List<String> row = Arrays.asList(values);
	// 			//List<GameState> state 
	// 			//rows.add(row);
	// 			states.add(state);
	// 			lineCount++;
	// 		}
			
	// 	}
	// 	System.out.println("After the while loop");
	// 	System.out.println(lineCount);

	// 	//cosmosDatabase.createContainerIfNotExists("state", "/id", ThroughputProperties.createManualThroughput(400));
	// 	//CosmosAsyncClient client = CosmosClientBuilder.createAsyncClient();
	// 	CosmosContainer stateContainer = cosmosDatabase.getContainer("state");
	// 	//cosmosItemProperties = new CosmosItemProperties();
	// 	//stateContainer.bulkImport(states, new PartitionKey("id")).block();
		
	// 	}

	// 	for (GameState state : states)
	// 	{
	// 		//CosmosItemResponse<GameState> response = stateContainer.createItem(state, new CosmosItemRequestOptions());
	// 		state.setId(state.getId());
	// 		stateContainer.createItem(state);
	// 	}
	// }

	public void load() throws Exception {

	/*
	 * For loading the data, I found that it was way harder to load a csv file in a container than a json file. I tried working with the csv, but it did not work.
	 * Thus I had to manually convert the csv file to a json file and then load the the json file in a normal way. Moreover, while loading the gamestate file, 
	 * I found that the id I have used for partion key conflicts with the system assigned id by cosmosdb. Thus gamestate loading was failing agian and again.
	 * After chaning the id to stateid, the file successfully loaded into containers.
	 * 
	 */
		//cosmosClient = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildClient();
		//cosmosDatabase = cosmosClient.getDatabase("cosmos516");
		
		System.out.println("\nLoading Data.");
		Gson gson = new Gson();

		try {
			// Read event data
			System.out.println("Reading Event data.");
			Reader reader = Files.newBufferedReader(Paths.get("src/data/gameevent.json"));
			GameEvent[] events = gson.fromJson(reader, GameEvent[].class);
			System.out.println("Reading done");

			// Create Event container and load data
			System.out.println("Loading Event data.");
			//cosmosDatabase.createContainerIfNotExists(URI, PRIMARY_KEY, null)
			cosmosDatabase.createContainerIfNotExists("event", "/eventid",
					ThroughputProperties.createManualThroughput(600));
			eventContainer = cosmosDatabase.getContainer("event");
			
			for (GameEvent event : events) {
			
				event.setEventid(event.getEventid());
				eventContainer.createItem(event);
			
			}
			
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			// Read state data
			System.out.println("Reading State data.");
			Reader reader = Files.newBufferedReader(Paths.get("src/data/gamestate.json"));
			GameState[] states = gson.fromJson(reader, GameState[].class);
			System.out.println("Reading done");

			// Create State container and load data
			System.out.println("Loading Event data.");
			//cosmosDatabase.createContainerIfNotExists(URI, PRIMARY_KEY, null)
			cosmosDatabase.createContainerIfNotExists("state", "/stateid",
					ThroughputProperties.createManualThroughput(400));
			stateContainer = cosmosDatabase.getContainer("state");
			
			for (GameState state : states) {
			
				state.setStateid(state.getStateid());
				eventContainer.createItem(state);
			
			}
			
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	/**
	 * Write a method update() that updates the current game state for a player.
	 * 
	 * @throws Exception
	 *                   if an error occurs
	 */
	public String update(Integer id) throws Exception {
		cosmosClient = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildClient();
		cosmosDatabase = cosmosClient.getDatabase("final516");
		// TODO: Write query #1
		
		System.out.println("\nExecuting update.");
		StringBuilder answer = new StringBuilder();
		
		stateContainer = cosmosDatabase.getContainer("state");
		// SQL query to get customer by custkey
		String sql = "SELECT * FROM c WHERE c.stateid = " + id;
		CosmosPagedIterable<GameEvent> response = stateContainer.queryItems(sql, new CosmosQueryRequestOptions(),
				GameEvent.class);
		if (response.iterator().hasNext()) {
			GameEvent order = response.iterator().next();
			answer.append(new Gson().toJson(order).toString());
		}

		// Print out json response and return it to calling function
		System.out.println(answer.toString());
		return answer.toString();
	}
	/**
	 * Write a method query1() that returns the game state for a player given the player id. Return a string with the state information in any format. Test for id=1 and id=949
	 * 
	 * @throws Exception
	 *                   if an error occurs
	 */
	public String query1(Integer id) throws Exception {
		cosmosClient = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildClient();
		cosmosDatabase = cosmosClient.getDatabase("final516");
		// TODO: Write query #1
		
		System.out.println("\nExecuting query1.");
		StringBuilder answer = new StringBuilder();
		
		stateContainer = cosmosDatabase.getContainer("state");
		// SQL query to get customer by custkey
		String sql = "SELECT c.gold, c.power, c.level FROM c WHERE c.stateid = " + id;
		CosmosPagedIterable<GameState> response = stateContainer.queryItems(sql, new CosmosQueryRequestOptions(),
		GameState.class);
		if (response.iterator().hasNext()) {
			GameState state = response.iterator().next();
			answer.append(new Gson().toJson(state).toString());
		}

		// Print out json response and return it to calling function
		System.out.println(answer.toString());
		return answer.toString();
	}

	/**
	 * Write a method query2() that returns the top 10 players by level in a given region.
	 * 
	 * @throws Exception
	 *                   if an error occurs
	 */
	public String query2(Integer region) throws Exception {
		// TODO: Write query #2

		cosmosClient = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildClient();
		cosmosDatabase = cosmosClient.getDatabase("final516");
		System.out.println("\nExecuting query2.");
		StringBuilder answer = new StringBuilder();
		stateContainer = cosmosDatabase.getContainer("state");
		
		String sql = String.format("SELECT TOP 10 c.stateid, c.level FROM c WHERE c.region = %d ORDER BY c.level DESC", region);
		//String sql = "SELECT TOP 10 c.stateid, c.level FROM c WHERE c.region = 1 ORDER BY c.level DESC";

		CosmosPagedIterable<GameState> response = stateContainer.queryItems(sql, new CosmosQueryRequestOptions(),
		GameState.class);
		String topten = "";
		Iterator<GameState> iter = response.iterator();
		while (iter.hasNext()) {
			GameState state = iter.next();
			topten = topten + new Gson().toJson(state).toString()+",";
		
		}
		//System.out.println(topten);
		String substring = topten.substring(0, topten.length() - 1); 
		topten = substring;

		// Print out json response and return it to calling function
		System.out.println(topten);
		return topten;
	}




	/**
	 * Write a method query3() that returns the top 5 most active players between time X and Y in a given region. Active players are determined based on the number of game events that they have in the time range.
	 * 
	 * @throws Exception
	 *                   if an error occurs
	 */
	public String query3(String startTime, String endTime, Integer region) throws Exception {
		// TODO: Write query #4


		/*
		 * Becuase of using Azure Cosmos DB, joining data from two container is not permitted, which makes sense.
		 * Thus I tried to get the top 10 active users who's based on the number of game events in a given time range. But unfortunately, Cosmos DB does not allow
		 * Order by with Group by. I tried to do it several different ways, but it does not work.
		 * 
		 * Thus, for this query, I just collected all the users from event container with the number of game event for each user, and collected all the users
		 * who play in a given region. But I could not complete the part where I could bring this two results together.
		 * 
		 */
		System.out.println("\nExecuting query4.");
		String jsonEvent = "";

		stateContainer = cosmosDatabase.getContainer("state");
		eventContainer = cosmosDatabase.getContainer("event");

		//2022-12-17 05:30:00
		//2022-12-17 15:00:00
		String sql = String.format("SELECT c.userid, count(c.eventtime) as count FROM c WHERE c.eventtime > '%s' and c.eventtime < '%s' Group by c.userid", startTime, endTime);

		CosmosPagedIterable<GameEvent> resp = eventContainer.queryItems(sql, new CosmosQueryRequestOptions(),
		GameEvent.class);
		
		//System.out.println(response.iterator().hasNext());
		Iterator<GameEvent> iter = resp.iterator();
		int count = 0;
		while (iter.hasNext()) {
			count++;

			GameEvent event = iter.next();
			//String single = new Gson().toJson(event);
			//System.out.println(single);
			jsonEvent = jsonEvent + new Gson().toJson(event).toString()+",";
		}
		System.out.println(jsonEvent);
		//System.out.println(count);



		sql = String.format("SELECT c.stateid FROM c WHERE c.region = %d", region);
		CosmosPagedIterable<GameState> response = stateContainer.queryItems(sql, new CosmosQueryRequestOptions(), GameState.class);
		String ids = "";
		Iterator<GameState> iterator = response.iterator();
		while (iterator.hasNext()) {

			GameState state = iterator.next();
			//String single = new Gson().toJson(event);
			//System.out.println(single);
			ids = ids + new Gson().toJson(state).toString()+",";
		}
		System.out.println(ids);
		
		// sql = 

		// GameState state = new GameState();
		// System.out.println("1: Printed");
		// if (iter.hasNext()){
		// 	state = iter.next();
		// 	System.out.println("Inside if");
		// }


		// //sql = String.format("SELECT c.stateid FROM c WHERE c.region = %d", region); 
		// sql = String.format("SELECT Top 5 c.userid FROM c WHERE c.userid > %s AND c.userid < %s ORDER BY c.o_orderdate DESC", startTime, endTime);
		// CosmosPagedIterable<GameEvent> response2 = eventContainer.queryItems(sql, new CosmosQueryRequestOptions(), GameEvent.class);

		// System.out.println("2: Printed");
		// ArrayList<GameEvent> orderList = new ArrayList<GameEvent>();
		// Iterator<GameEvent> iter2 = response2.iterator();




		// // Print out json response and return it to calling function

		// System.out.println("Printing the output");
		return jsonEvent;
	}
}
