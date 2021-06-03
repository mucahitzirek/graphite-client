package net.savantly.graphite.impl;

import net.savantly.graphite.CarbonMetric;
import net.savantly.graphite.CarbonSender;
import net.savantly.graphite.GraphiteClient;

import java.util.Collection;

public class DefaultGraphiteClient implements GraphiteClient {
	private CarbonSender carbonSender;

	/**
	 * @param carbonSender
	 */
	public DefaultGraphiteClient(CarbonSender carbonSender) {
		this.carbonSender = carbonSender;
	}

	public void saveCarbonMetrics(CarbonMetric carbonMetric) {
		carbonSender.sendToCarbon(carbonMetric);
	}

	public void saveCarbonMetrics(Collection<CarbonMetric> carbonMetrics) {
		carbonSender.sendToCarbon(carbonMetrics);
	}
}
