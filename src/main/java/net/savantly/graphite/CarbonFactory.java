package net.savantly.graphite;

import net.savantly.graphite.impl.DefaultCarbonSender;
import net.savantly.graphite.impl.SimpleCarbonMetric;

import java.net.SocketException;
import java.net.UnknownHostException;

public class CarbonFactory {
	
	public static CarbonMetric getDefaultCarbonMetric(String metricName, String value, long epoch){
		return new SimpleCarbonMetric(metricName, value, epoch);
	}
	
	public static CarbonSender getDefaultCarbonSender(String graphiteHost, int carbonPort, String sourceIp) throws UnknownHostException, SocketException{
		return new DefaultCarbonSender(graphiteHost, carbonPort, sourceIp);
	}

}
