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

package com.groupon.nakala.core;

import com.groupon.nakala.normalization.CaseNormalizer;
import com.groupon.nakala.normalization.StringNormalizer;
import junit.framework.TestCase;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author npendar@groupon.com
 */
public class TFTextRepresenterTest extends TestCase {
    @Test
    public void testRepresent() throws Exception {
        Features features = new Features();
        features.initialize(new StringReader("like\ntest\n"));

        List<StringNormalizer> normalizers = new ArrayList<StringNormalizer>();
        normalizers.add(new CaseNormalizer());
        normalizers.add(new StemmingNormalizer());

        TokenizerStream tokenizer = new RegexpTokenizerStream();

        Parameters params = new Parameters();
        params.set("features", features);
        params.set("normalizers", normalizers);
        params.set("tokenizer", tokenizer);

        TextRepresenter representer = new TFTextRepresenter();
        representer.initialize(params);

        RealVector representation = representer.represent("I like testing with tests.");

        assertEquals(1d, representation.getEntry(0));
        assertEquals(2d, representation.getEntry(1));

        params.set("normalize_by_length", true);
        representer.initialize(params);
        representation = representer.represent("I like testing with tests.");

        assertEquals(0.2, representation.getEntry(0));
        assertEquals(0.4, representation.getEntry(1));
    }

    @Test
    public void testGetTokenizer() throws Exception {
        Features features = new Features();
        features.initialize(new StringReader("like\ntest\n"));

        List<StringNormalizer> normalizers = new ArrayList<StringNormalizer>();
        normalizers.add(new CaseNormalizer());
        normalizers.add(new StemmingNormalizer());

        TokenizerStream tokenizer = new RegexpTokenizerStream();
        Parameters params = new Parameters();
        params.set("features", features);
        params.set("normalizers", normalizers);
        params.set("tokenizer", tokenizer);

        TextRepresenter representer = new TFTextRepresenter();
        representer.initialize(params);
        assertEquals(tokenizer, representer.getTokenizer());
    }

    @Test
    public void testGetNormalizers() throws Exception {
        Features features = new Features();
        features.initialize(new StringReader("like\ntest\n"));

        List<StringNormalizer> normalizers = new ArrayList<StringNormalizer>();
        normalizers.add(new CaseNormalizer());
        normalizers.add(new StemmingNormalizer());

        TokenizerStream tokenizer = new RegexpTokenizerStream();

        Parameters params = new Parameters();
        params.set("features", features);
        params.set("normalizers", normalizers);
        params.set("tokenizer", tokenizer);

        TextRepresenter representer = new TFTextRepresenter();
        representer.initialize(params);

        assertEquals(normalizers, representer.getNormalizers());
    }

    @Test
    public void testGetFeatures() throws Exception {
        Features features = new Features();
        features.initialize(new StringReader("like\ntest\n"));

        List<StringNormalizer> normalizers = new ArrayList<StringNormalizer>();
        normalizers.add(new CaseNormalizer());
        normalizers.add(new StemmingNormalizer());

        TokenizerStream tokenizer = new RegexpTokenizerStream();

        Parameters params = new Parameters();
        params.set("features", features);
        params.set("normalizers", normalizers);
        params.set("tokenizer", tokenizer);

        TextRepresenter representer = new TFTextRepresenter();
        representer.initialize(params);

        assertEquals(features, representer.getFeatures());
    }
}
