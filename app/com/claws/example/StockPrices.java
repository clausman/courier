package com.claws.example;

import com.google.common.collect.Lists;
import objects.User;
import objects.parameters.Decimal;
import objects.parameters.Parameter;
import objects.parameters.ParameterMap;
import objects.parameters.Text;
import objects.segments.BaseSegment;
import objects.segments.Entry;

import java.util.List;

/**
 * Created by jclausman on 5/14/14.
 */
public class StockPrices extends BaseSegment {

    private static final Text TICKER = new Text("ticker");
    private static final Decimal VALUE = new Decimal("price");

    public StockPrices() {
        super("Daily stock price", "Fetches the current price for stock tickers");
    }

    @Override
    public List<Parameter> getOutputParameters() {
        return Lists.newArrayList(TICKER, VALUE);
    }

    @Override
    public Iterable<Entry> fetchEntries(ParameterMap segmentParameters) {
        // TODO Fetch stock data from yahoo API
        return Lists.newArrayList(
            new Entry.Builder().forUser(User.NOBODY).with(TICKER, "TRIP").with(VALUE, 84.72).build(),
            new Entry.Builder().forUser(User.NOBODY).with(TICKER, "TRIP").with(VALUE, 85.81).build(),
            new Entry.Builder().forUser(User.NOBODY).with(TICKER, "TRIP").with(VALUE, 83.52).build()
        );
    }
}
