[![Build Status](https://travis-ci.org/savantly-net/graphite-client.svg?branch=master)](https://travis-ci.org/savantly-net/graphite-client)

# Graphite Client


Use in Maven project -

```
   	<parent>
        	<groupId>org.springframework.boot</groupId>
        	<artifactId>spring-boot-starter-parent</artifactId>
       	 	<version>2.4.3</version>
       	 	<relativePath/> 
    	</parent>
	
	<dependency>
		<groupId>net.savantly</groupId>
		<artifactId>graphite-client</artifactId>
		<version>2.0.0-RELEASE</version>
	</dependency>
	
	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```


## Reporting Example 


    public JsonNode query(String queryGraphite, String from) throws SocketException, UnknownHostException {
        // To create carbon dynamically
        CarbonSender carbonSender = CarbonFactory.getDefaultCarbonSender(graphiteHost, carbonPort, carbonServerIp);
        //To overwrite the default value graphiteHost and graphitePort must be entered.
        // default -> localhost - 8080
        GraphiteWebConfiguration webConfig = new GraphiteWebConfigurationBuilder()
                .setWebHost(graphiteHost)
                .setWebPort(graphitePort)
                .build();
        QueryableGraphiteClient client = new QueryableGraphiteClientImpl(carbonSender,
                webConfig);

        JsonFormatter formatter = new JsonFormatter();
        GraphiteQueryBuilder<JsonNode> builder = new
                GraphiteQueryBuilder<>(formatter);
        String finalFrom = from;
        builder.setFrom(() -> {
            return finalFrom; //default 24h, GraphiteTimeUnit set ->  s, min, h, d, w, mon, y
        });
        // the query must be entered correctly
        // If wrong query is entered,JsonNode it turns empty
        GraphiteQuery<JsonNode> query = builder.setTarget(queryGraphite).build();
        JsonNode results = client.query(query);
        return results;
    }



## Query Example -  
	
    @Autowired
    GraphiteServerBase grafite;

    @GetMapping("/graphite")
    public JsonNode getGraphite(@RequestParam(required = false) String from,
                                @RequestParam(required = false) String queryGraphite
    ) throws SocketException, UnknownHostException {
        // If there is no (selected) value given from the parameter
        if (StringUtils.isBlank(from)) {
            from = "-1minutes";  // the desired default value can be given
        }
        if (StringUtils.isBlank(queryGraphite)) {
            queryGraphite = "stats.kong.posts.request.count";  // the desired default value can be given
        }

        return grafite.query(queryGraphite, from);
    }
