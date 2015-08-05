package services.local;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import objects.segments.Segment;
import services.Store;

import java.util.Collection;
import java.util.List;

/**
 * Created by jclausman on 5/10/14.
 */
@Singleton
public class LocalSegmentStore implements Store<Segment> {
    private final List<Segment> segments = Lists.newArrayList();

    @Override
    public Collection<Segment> all() {
        return ImmutableList.copyOf(segments);
    }

    @Override
    public boolean register(Segment segment) {
        if(segments.contains(segment)) return false;
        segments.add(segment);
        return true;
    }

    @Override
    public Segment findByName(String name) {
        for (Segment seg : segments)
        {
            if (seg.getName().equalsIgnoreCase(name)) return seg;
        }
        return null;
    }
}
