package services.local;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import objects.parameters.Parameter;
import objects.parameters.ParameterMap;
import objects.segments.Segment;
import services.SegmentService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jclausman on 5/10/14.
 */
public class LocalSegmentService implements SegmentService {
    @Override
    public Iterable<ParameterMap> fetchSegment(final Segment segment) {
        // The name of a segment is actually the name of a tsv file
        try {
            final List<Parameter> parameters = segment.getOutputParameters();
            return Files.readLines(
                    new File(segment.getName()),
                    Charset.defaultCharset(),
                    new LineProcessor<List<ParameterMap>>() {
                        private final List<ParameterMap> entries = Lists.newArrayList();
                        @Override
                        public boolean processLine(String line) throws IOException {
                            // If the line is invalid, skip it and continue
                            if (line == null || line.length() == 0) return true;
                            String[] values = line.split("\t");
                            // If there is an entry which is improperly sized, there is a problem so bail
                            if (values.length != segment.getOutputParameters().size()) return false;
                            ParameterMap.Builder builder = new ParameterMap.Builder();
                            for (int i=0; i<parameters.size();i++)
                            {
                                builder.with(parameters.get(i), values[i]);
                            }
                            entries.add(builder.build());
                            return true;
                        }

                        @Override
                        public List<ParameterMap> getResult() {
                            return entries;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }
}
