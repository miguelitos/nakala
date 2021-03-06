/*
Copyright (c) 2013, Groupon, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

Neither the name of GROUPON nor the names of its contributors may be
used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.groupon.nakala.analysis;

import com.groupon.nakala.core.BlockFilter;
import com.groupon.nakala.core.Constants;
import com.groupon.nakala.core.Parameters;
import com.groupon.nakala.core.PassFilter;
import com.groupon.nakala.exceptions.ResourceInitializationException;
import org.apache.log4j.Logger;

/**
 * @author npendar@groupon.com
 */

public abstract class AbstractCollectionAnalyzer implements CollectionAnalyzer {
    protected static final Logger logger = Logger.getLogger(AbstractCollectionAnalyzer.class);
    protected PassFilter passFilter;
    protected BlockFilter blockFilter;

    @Override
    public void initialize(Parameters params) throws ResourceInitializationException {
        if (params.contains(Constants.PASS_FILTER))
            passFilter = (PassFilter) params.get(Constants.PASS_FILTER);
        if (params.contains(Constants.BLOCK_FILTER))
            blockFilter = (BlockFilter) params.get(Constants.BLOCK_FILTER);

        logger.debug("passFilter: " + (passFilter == null ? "null" : passFilter.getClass().getName()));
        logger.debug("blockFilter: " + (blockFilter == null ? "null" : blockFilter.getClass().getName()));
    }

    @Override
    public void setPassFilter(PassFilter passFilter) {
        this.passFilter = passFilter;
    }

    @Override
    public void setBlockFilter(BlockFilter blockFilter) {
        this.blockFilter = blockFilter;
    }

    protected Logger getLogger() {
        return logger;
    }
}
