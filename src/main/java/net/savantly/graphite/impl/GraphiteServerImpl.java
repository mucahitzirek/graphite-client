package net.savantly.graphite.impl;

import com.fasterxml.jackson.databind.JsonNode;
import net.savantly.graphite.CarbonFactory;
import net.savantly.graphite.CarbonSender;
import net.savantly.graphite.QueryableGraphiteClient;
import net.savantly.graphite.impl.QueryableGraphiteClientImpl;
import net.savantly.graphite.query.GraphiteQuery;
import net.savantly.graphite.query.GraphiteQueryBuilder;
import net.savantly.graphite.query.fomat.JsonFormatter;
import net.savantly.graphite.server.GraphiteServerBase;
import net.savantly.graphite.web.GraphiteWebConfiguration;
import net.savantly.graphite.web.GraphiteWebConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.net.SocketException;
import java.net.UnknownHostException;

public class GraphiteServerImpl extends GraphiteServerBase {

    @Value("${graphite-host}")
    private String graphiteHost;

    @Value("${graphite-port}")
    private int graphitePort;

    @Value("${carbon-port}")
    private int carbonPort;

    @Value("${carbon-server-ip}")
    private String carbonServerIp;

    @Override
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
}
