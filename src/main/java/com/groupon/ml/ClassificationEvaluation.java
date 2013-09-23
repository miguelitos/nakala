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

package com.groupon.ml;

import com.groupon.nakala.analysis.Analysis;
import com.groupon.nakala.analysis.AnalysisCollector;
import com.groupon.nakala.core.Id;
import com.groupon.nakala.db.DataStore;
import com.groupon.nakala.db.FlatFileStore;
import com.groupon.nakala.exceptions.StoreException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author npendar@groupon.com
 */

public class ClassificationEvaluation implements AnalysisCollector {

    private List<String> labels;
    private Map<Id, Set<String>> id2trueLabels;
    private ClassificationAnalysisCollector id2predictions;
    private double minThreshold = 0d;
    private double maxThreshold = 1d;
    private double step = 0.1;

    public ClassificationEvaluation(List<String> labels,
                                    Map<Id, Set<String>> id2trueLabels,
                                    ClassificationAnalysisCollector id2predictions) {
        this.labels = labels;
        this.id2trueLabels = id2trueLabels;
        this.id2predictions = id2predictions;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public void setMaxThreshold(double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public void setStep(double step) {
        this.step = step;
    }

    @Override
    public void addAnalysis(Analysis a) {
    }

    @Override
    public void store(DataStore ds) throws StoreException {
        if (ds instanceof FlatFileStore) {
            Workbook workbook = new HSSFWorkbook();
            for (double threshold = minThreshold; threshold <= maxThreshold; threshold += step) {
                ClassificationMetrics metrics = new ClassificationMetrics(threshold, labels, id2trueLabels, id2predictions);
                ClassificationReportWriter.writeStats(workbook.createSheet(String.format("stats%6.5f", threshold)), metrics);
            }
            ClassificationReportWriter.writeDetails(workbook.createSheet("details"), labels, id2trueLabels, id2predictions);
            try {
                workbook.write(((FlatFileStore) ds).getOutputStream());
            } catch (IOException e) {
                throw new StoreException("Failed to write workbook.", e);
            }
        } else {
            throw new StoreException("Unsupported data store " + ds.getClass().getName());
        }
    }
}
