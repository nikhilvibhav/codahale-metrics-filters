package com.capgemini.codahale.metrics.filter;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.netflix.config.DynamicPropertyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of a CodaHale @MetricFilter based upon an Archaius DynamicPropertyFactory.
 *
 * To enable this filter, the property 'filter.graphite.metrics' must be set to TRUE.
 *
 * If this is the case, metrics will be filtered unless METRIC_NAME = true is set in
 * the properties.
 *
 *      e.g.: HystrixCommand.IndiciaService.GetIndicia.countFailure = true
 *
 * For detail on how the metric names are constructed, refer to the source of the
 * {@link com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisherCommand} and
 * {@link com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisherThreadPool} classes.
 *
 *  @author Simon Irving
 */
public class ConfigurableCodaHaleMetricFilter implements MetricFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurableCodaHaleMetricFilter.class);

    private DynamicPropertyFactory archaiusPropertyFactory;


    public ConfigurableCodaHaleMetricFilter(DynamicPropertyFactory archaiusPropertyFactory)
    {
        this.archaiusPropertyFactory = archaiusPropertyFactory;
    }

    @Override
    public boolean matches(String s, Metric metric) {

        if(!isGraphiteEnabled()) {
            return false;
        } else if (!isFilterEnabled()) {
            return true;
        }

        boolean matchesFilter = archaiusPropertyFactory.getBooleanProperty(s, false).get();

        LOGGER.debug("Does metric [{}] match filter? [{}]",s,matchesFilter);

        return matchesFilter;
    }

    protected boolean isFilterEnabled() {

        boolean filterEnabled = archaiusPropertyFactory.getBooleanProperty("filter.graphite.metrics", false).get();

        LOGGER.debug("Is filter enabled? [{}]", filterEnabled);

        return filterEnabled;
    }

    protected boolean isGraphiteEnabled() {

        boolean graphiteEnabled = archaiusPropertyFactory.getBooleanProperty("enable.graphite.metrics", true).get();

        LOGGER.debug("Is graphite enabled? [{}]", graphiteEnabled);

        return graphiteEnabled;
    }

}
