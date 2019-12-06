package com.capgemini.codahale.metrics.filter;

import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.servlet.AbstractInstrumentedFilter;

/**
 * Customized version of {@link com.codahale.metrics.servlet.AbstractInstrumentedFilter} class which provides metrics
 * about the HTTP response codes.
 *
 * @author Ganga Aloori
 * @see <a href=https://metrics.dropwizard.io/4.1.1/manual/servlet.html/>
 */
public class HttpResponseCodeMetricsFilter extends AbstractInstrumentedFilter {

    public static final String REGISTRY_ATTRIBUTE = com.codahale.metrics.servlet.InstrumentedFilter.REGISTRY_ATTRIBUTE;

    private static final String NAME_PREFIX = "responseCodes.";
    private static final int OK = 200;
    private static final int UNAUTHORIZED = 401;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;
    private static final int SERVER_ERROR = 500;
    private static final int CREATED = 201;
    private static final int NO_CONTENT = 204;

    /**
     * Creates a new instance of the filter.
     */
    public HttpResponseCodeMetricsFilter() {
        super(REGISTRY_ATTRIBUTE, createMeterNamesByStatusCode(), NAME_PREFIX + "other");
    }

    private static Map<Integer, String> createMeterNamesByStatusCode() {
        final Map<Integer, String> meterNamesByStatusCode = new HashMap<>(10);
        meterNamesByStatusCode.put(OK, NAME_PREFIX + "ok");
        meterNamesByStatusCode.put(UNAUTHORIZED, NAME_PREFIX + "unauthorized");
        meterNamesByStatusCode.put(BAD_REQUEST, NAME_PREFIX + "badRequest");
        meterNamesByStatusCode.put(NOT_FOUND, NAME_PREFIX + "notFound");
        meterNamesByStatusCode.put(SERVER_ERROR, NAME_PREFIX + "serverError");
        meterNamesByStatusCode.put(CREATED, NAME_PREFIX + "created");
        meterNamesByStatusCode.put(NO_CONTENT, NAME_PREFIX + "noContent");
        return meterNamesByStatusCode;
    }
}
