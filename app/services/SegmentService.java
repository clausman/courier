package services;

import objects.parameters.ParameterMap;
import objects.segments.Segment;

/**
 * TODO Describe something...
 *
 * @author jclausman
 * @since 4/29/14
 */
public interface SegmentService {
    /**
     * Fetch all the data for a provided segment
     * @param segment to get the data for
     * @return iterable over the data, iterable may page data
     */
    Iterable<ParameterMap> fetchSegment(Segment segment);
}
