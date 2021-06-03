package net.savantly.graphite.server;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class GraphiteServerBase {
    public abstract JsonNode query(String target, String from) throws SocketException, UnknownHostException;
}
