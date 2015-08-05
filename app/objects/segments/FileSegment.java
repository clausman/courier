package objects.segments;

import akka.event.Logging;
import objects.parameters.ParameterMap;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by jclausman on 5/27/14.
 */
public class FileSegment<T> extends BaseSegment<T> {

    private final File datafile;
    public FileSegment(String name, String description, File datafile)
    {
        super(name, description);
        this.datafile = datafile;
    }

    @Override
    public Iterable<Entry<T>> fetchEntries(ParameterMap segmentParameters) {

        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(datafile))){
            //TODO Make iterable?
            //TODO Read file

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }
}
