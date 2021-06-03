package net.savantly.graphite.controller;

import com.fasterxml.jackson.databind.JsonNode;
import net.savantly.graphite.server.GraphiteServerBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/1.0")
public class GraphiteController {

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

}
