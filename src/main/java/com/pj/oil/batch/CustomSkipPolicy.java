package com.pj.oil.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

public class CustomSkipPolicy implements SkipPolicy {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSkipPolicy.class);
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) {
        if (t instanceof FlatFileParseException && skipCount <= 8) {
            FlatFileParseException ffpe = (FlatFileParseException) t;
            LOGGER.warn(
                    "FlatFileParseException in line: {}, Input to the line: '{}'. Skipping... "
                    , ffpe.getLineNumber()
                    , ffpe.getInput()
            );
            return true;
        }
        return false;
    }
}
