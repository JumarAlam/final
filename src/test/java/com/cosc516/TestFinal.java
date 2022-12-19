package com.cosc516;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosAsyncDatabase;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosDatabaseProperties;
import com.azure.cosmos.models.CosmosContainerProperties;

import org.junit.jupiter.api.BeforeAll;

public class TestFinal {
    /**
     * Class being tested
     */
    private static Final db;

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
     * Requests a connection to the server.
     * 
     * @throws Exception
     *                   if an error occurs
     */
    @BeforeAll
    public static void init() throws Exception {
        db = new Final();
        db.connect();
    }

    /**
     * Tests connect
     * 
     * @throws Exception
     */
    @Test
    public void testConnect() throws Exception {
        System.out.println("Testing connect.");
        try {
            db.connect();
        } catch (Exception e) {
            fail("No connection");
        }
    }

    /**
     * Tests create
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testCreateMethod() {
        // Create a CosmosAsyncClient instance
        CosmosAsyncClient client = new CosmosClientBuilder().endpoint(URI).key(PRIMARY_KEY).buildAsyncClient();

        // Call the create method for the database and container
        //CosmosDatabaseProperties database = client.createDatabase(new CosmosDatabaseProperties("final516")).block().getProperties();
        try{
            CosmosDatabaseProperties databaseDefinition = new CosmosDatabaseProperties("final516");
            CosmosDatabaseProperties database = client.createDatabase(databaseDefinition).block().getProperties();
        } catch(CosmosException e){
            if(e.getStatusCode()== 409 )
            {
                System.out.println("This database already exists");
            }
        }
        CosmosAsyncDatabase database = client.getDatabase("final516");
        CosmosAsyncContainer stateContainer = database.getContainer("state");
        CosmosAsyncContainer eventContainer = database.getContainer("event");

        // Verify that the create method is working correctly
        assertNotNull(database);
        assertNotNull(stateContainer);
        assertNotNull(eventContainer);
        assertEquals("final516", database.getId());
        assertEquals("state", stateContainer.getId());
        assertEquals("event", eventContainer.getId());
    }



    /**
     * Tests load
     */
    @Test
    public void testLoad() throws Exception {
        db.connect();
        System.out.println("Testing load.");

        // SQL query to get number of states
        int numStates = 0;
        String sql = "SELECT COUNT(c.stateid) AS count FROM c";

        try {
            CosmosPagedIterable<Count> response = db.stateContainer.queryItems(sql, new CosmosQueryRequestOptions(),
                    Count.class);
            if (response.iterator().hasNext()) {
                Count state = response.iterator().next();
                numStates = state.count;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.err.println(numStates);

        // Load state if numStates is 0
        if (numStates == 0) {
            try {
                db.load();
                CosmosPagedIterable<Count> response = db.stateContainer.queryItems(sql,
                        new CosmosQueryRequestOptions(), Count.class);
                if (response.iterator().hasNext()) {
                    Count state = response.iterator().next();
                    numStates = state.count;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // Confirm number of states is correct
        assertEquals(1000, numStates);

        // SQL query to get number of events
        int numEvents = 0;
        sql = "SELECT COUNT(c.eventid) AS count FROM c";

        try {
            CosmosPagedIterable<Count> response = db.eventContainer.queryItems(sql, new CosmosQueryRequestOptions(),
                    Count.class);
            if (response.iterator().hasNext()) {
                Count event = response.iterator().next();
                numEvents = event.count;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // Load event if numEvents is 0
        if (numEvents == 0) {
            try {
                db.load();
                CosmosPagedIterable<Count> response = db.eventContainer.queryItems(sql,
                        new CosmosQueryRequestOptions(), Count.class);
                if (response.iterator().hasNext()) {
                    Count event = response.iterator().next();
                    numEvents = event.count;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // Confirm number of event is correct
        assertEquals(10000, numEvents);
    }

    /**
     * Tests query #1
     * 
     * @throws Exception
     */
    @Test
    public void testQuery1() throws Exception {
        db.connect();
        System.out.println("Testing query1.");
        String result = "";
        try {
            result = db.query1(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        String answer = "{\"gold\":56607,\"power\":248393,\"level\":21}";


        assertEquals(answer, result);
    }

    /**
     * Tests query #2
     */
    @Test
    public void testQuery2() throws Exception {
        System.out.println("Testing query2.");
        String result = "";
        try {
            result = db.query2(1);
        } catch (Exception e) {
            System.out.println(e);
        }

        String answer = "{\"stateid\":970,\"level\":49},"
                        + "{\"stateid\":638,\"level\":49},"
                        + "{\"stateid\":855,\"level\":48},"
                        + "{\"stateid\":620,\"level\":48},"
                        + "{\"stateid\":574,\"level\":48},"
                        + "{\"stateid\":547,\"level\":48},"
                        + "{\"stateid\":194,\"level\":48},"
                        + "{\"stateid\":478,\"level\":47},"
                        + "{\"stateid\":322,\"level\":47},"
                        + "{\"stateid\":260,\"level\":47}";

        assertEquals(answer, result);
    }

    /**
     * Tests query #3
     */
    @Test
    public void testQuery3() throws Exception {
        System.out.println("Testing query3.");
        String result = "";
        try {
            result = db.query3();
        } catch (Exception e) {
            System.out.println(e);
        }
        String answer = "[{\"id\":\"59527\",\"o_orderkey\":59527,\"o_custkey\":662,\"o_orderstatus\":\"O\",\"o_totalprice\":225570.4,\"o_orderdate\":\"1998-08-01\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000212\",\"o_shippriority\":0,\"o_comment\":\"PhCLQOj2xS6yQlMB hzOMiM024gz\"},"
                + "{\"id\":\"59143\",\"o_orderkey\":59143,\"o_custkey\":343,\"o_orderstatus\":\"O\",\"o_totalprice\":205778.14,\"o_orderdate\":\"1998-07-26\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000258\",\"o_shippriority\":0,\"o_comment\":\"ghk4xLy0Ql RCCxiNlwNA\"},"
                + "{\"id\":\"14694\",\"o_orderkey\":14694,\"o_custkey\":521,\"o_orderstatus\":\"O\",\"o_totalprice\":197792.44,\"o_orderdate\":\"1998-07-28\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000742\",\"o_shippriority\":0,\"o_comment\":\"jjLkky6Og3P2xMhS6wkyBlmw3jSPRM1zLlQlC6m66NN3n7jjBA72kCy7A2nSzS3P PgCgMSMCRMO5\"},"
                + "{\"id\":\"50884\",\"o_orderkey\":50884,\"o_custkey\":629,\"o_orderstatus\":\"O\",\"o_totalprice\":185488.12,\"o_orderdate\":\"1998-07-30\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000888\",\"o_shippriority\":0,\"o_comment\":\"4BSRkjhxM145LA1hnjmhxjwSCB735h0MLmQAMjw\"},"
                + "{\"id\":\"53377\",\"o_orderkey\":53377,\"o_custkey\":1018,\"o_orderstatus\":\"O\",\"o_totalprice\":181200.27,\"o_orderdate\":\"1998-07-26\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000410\",\"o_shippriority\":0,\"o_comment\":\"QjRL74Q nyCAkxi34li5CMRhQ4ziwkLj20hgi7jMAm7\"},"
                + "{\"id\":\"13220\",\"o_orderkey\":13220,\"o_custkey\":1153,\"o_orderstatus\":\"O\",\"o_totalprice\":172444.6,\"o_orderdate\":\"1998-07-31\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000586\",\"o_shippriority\":0,\"o_comment\":\"QO71h1gLxQhxQN0SNh5mwAl\"},"
                + "{\"id\":\"19269\",\"o_orderkey\":19269,\"o_custkey\":113,\"o_orderstatus\":\"O\",\"o_totalprice\":147326.9,\"o_orderdate\":\"1998-07-28\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000105\",\"o_shippriority\":0,\"o_comment\":\"xLOC7kLjynj32n02A61RA55ngM2Ahi\"},"
                + "{\"id\":\"52935\",\"o_orderkey\":52935,\"o_custkey\":917,\"o_orderstatus\":\"O\",\"o_totalprice\":129823.45,\"o_orderdate\":\"1998-07-27\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000254\",\"o_shippriority\":0,\"o_comment\":\"4AyyOA560xgQSngiwQRhSQPyA55x7NiMiL0mBlng\"},"
                + "{\"id\":\"20195\",\"o_orderkey\":20195,\"o_custkey\":962,\"o_orderstatus\":\"O\",\"o_totalprice\":95554.3,\"o_orderdate\":\"1998-08-02\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000423\",\"o_shippriority\":0,\"o_comment\":\"C55w n4O45hOw45BM5CMkAi2mNxO21\"},"
                + "{\"id\":\"47394\",\"o_orderkey\":47394,\"o_custkey\":1243,\"o_orderstatus\":\"O\",\"o_totalprice\":88572.39,\"o_orderdate\":\"1998-07-28\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000850\",\"o_shippriority\":0,\"o_comment\":\"Bzyj 1wALPg4L73S0nQ1hPwBjQRh5k2O6Az2ixkihiih\"},"
                + "{\"id\":\"19876\",\"o_orderkey\":19876,\"o_custkey\":476,\"o_orderstatus\":\"O\",\"o_totalprice\":64557.42,\"o_orderdate\":\"1998-07-31\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000186\",\"o_shippriority\":0,\"o_comment\":\"nRPP1m2y3g2SC00nOyzjQ67xjLmCRM4 iNN2LMllQQCkSOkR0yh\"},"
                + "{\"id\":\"3909\",\"o_orderkey\":3909,\"o_custkey\":1493,\"o_orderstatus\":\"O\",\"o_totalprice\":46761.33,\"o_orderdate\":\"1998-07-27\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000980\",\"o_shippriority\":0,\"o_comment\":\"nk3h QkQRBAL372BNw 0C0gQlQC4SiQC64ilMOj1j6jQhL06346P35m13y SlyN1j\"},"
                + "{\"id\":\"57637\",\"o_orderkey\":57637,\"o_custkey\":1222,\"o_orderstatus\":\"O\",\"o_totalprice\":30566.24,\"o_orderdate\":\"1998-08-01\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000383\",\"o_shippriority\":0,\"o_comment\":\"kSRyPxS27z70LB 2OxPh3LknM63BSPw6zR10RNkk5M1 5yAO3A2\"}]";

        assertEquals(answer, result);
    }

    /**
     * Tests cquery #4
     */
    @Test
    public void testQuery4() throws Exception {
        System.out.println("Testing query4.");
        String result = "";
        try {
            result = db.query4(515);
        } catch (Exception e) {
            System.out.println(e);
        }

        String answer = "{\"id\":\"515\",\"c_custkey\":515,\"c_name\":\"Customer#000000515\",\"c_acctbal\":3225.07,\"orders\":[{\"id\":\"4231\",\"o_orderkey\":4231,\"o_custkey\":515,\"o_orderstatus\":\"O\",\"o_totalprice\":114927.12,\"o_orderdate\":\"1997-11-20\",\"o_orderpriority\":\"4-NOT SPECIFIED\",\"o_clerk\":\"Clerk#000000630\",\"o_shippriority\":0,\"o_comment\":\"Al3kOy2LL3kLS4gAjNj5i6S5\"},"
                + "{\"id\":\"41223\",\"o_orderkey\":41223,\"o_custkey\":515,\"o_orderstatus\":\"O\",\"o_totalprice\":138923.8,\"o_orderdate\":\"1997-10-31\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000992\",\"o_shippriority\":0,\"o_comment\":\"61NCAg20604NAwQO7j32Sin\"},"
                + "{\"id\":\"47013\",\"o_orderkey\":47013,\"o_custkey\":515,\"o_orderstatus\":\"O\",\"o_totalprice\":27681.81,\"o_orderdate\":\"1996-08-23\",\"o_orderpriority\":\"3-MEDIUM\",\"o_clerk\":\"Clerk#000000156\",\"o_shippriority\":0,\"o_comment\":\"Bgzky6z4gLxxP7A7nAyl\"},"
                + "{\"id\":\"53061\",\"o_orderkey\":53061,\"o_custkey\":515,\"o_orderstatus\":\"O\",\"o_totalprice\":238692.2,\"o_orderdate\":\"1995-07-26\",\"o_orderpriority\":\"3-MEDIUM\",\"o_clerk\":\"Clerk#000000576\",\"o_shippriority\":0,\"o_comment\":\"PRMO4h5gyB7Mx6A3zhjzlO7l7ngx4MymCBCN5016M6xnBiMznyjRnCMM6B0j7iBCBkNw4SL4lOynlh\"},"
                + "{\"id\":\"23940\",\"o_orderkey\":23940,\"o_custkey\":515,\"o_orderstatus\":\"P\",\"o_totalprice\":36991.07,\"o_orderdate\":\"1995-04-09\",\"o_orderpriority\":\"3-MEDIUM\",\"o_clerk\":\"Clerk#000000659\",\"o_shippriority\":0,\"o_comment\":\"gx2 iNwmR35 hS36CLlliR1RiAxinnLh52APk 5CiyORjQ77g2N\"},"
                + "{\"id\":\"37988\",\"o_orderkey\":37988,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":14790.04,\"o_orderdate\":\"1995-04-06\",\"o_orderpriority\":\"4-NOT SPECIFIED\",\"o_clerk\":\"Clerk#000000842\",\"o_shippriority\":0,\"o_comment\":\"RORmOh5Rxg72kilLm0 x7mzC7RxSyghPAh2Bn2LNPigzNm7NLlM06MNiihyNMx4\"},"
                + "{\"id\":\"26342\",\"o_orderkey\":26342,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":208584.2,\"o_orderdate\":\"1994-10-19\",\"o_orderpriority\":\"5-LOW\",\"o_clerk\":\"Clerk#000000394\",\"o_shippriority\":0,\"o_comment\":\"imPmwjl45QL30m6RO6mkN5A 7i3zxnMlCOnhBil4g5mP3yRL2kgy2MSALO\"},"
                + "{\"id\":\"5346\",\"o_orderkey\":5346,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":210118.46,\"o_orderdate\":\"1993-12-26\",\"o_orderpriority\":\"2-HIGH\",\"o_clerk\":\"Clerk#000000220\",\"o_shippriority\":0,\"o_comment\":\"0z23Ay70zRBmMwSkLmxS60SSgP3BMiCx0COn i\"},"
                + "{\"id\":\"39045\",\"o_orderkey\":39045,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":163990.41,\"o_orderdate\":\"1992-09-15\",\"o_orderpriority\":\"1-URGENT\",\"o_clerk\":\"Clerk#000000545\",\"o_shippriority\":0,\"o_comment\":\"lz lQm773x3gMB5AkwwQSki0wAy5y4RLBN 5S2yl2LSQn63lLh563Q3C40A\"},"
                + "{\"id\":\"45126\",\"o_orderkey\":45126,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":89185.57,\"o_orderdate\":\"1992-05-17\",\"o_orderpriority\":\"2-HIGH\",\"o_clerk\":\"Clerk#000000789\",\"o_shippriority\":0,\"o_comment\":\"1n64 10k76 1A20B6k1A5B6OhR0zx3zi7nPiSyknx4i4Q nnigB\"},"
                + "{\"id\":\"53988\",\"o_orderkey\":53988,\"o_custkey\":515,\"o_orderstatus\":\"F\",\"o_totalprice\":79606.02,\"o_orderdate\":\"1992-01-02\",\"o_orderpriority\":\"3-MEDIUM\",\"o_clerk\":\"Clerk#000000182\",\"o_shippriority\":0,\"o_comment\":\"g6hk1i0n6B6NRgBS0h3li3 15yLALzM3x3ABRO3k2glj2w0N\"}]}";

        assertEquals(answer, result);
    }
}