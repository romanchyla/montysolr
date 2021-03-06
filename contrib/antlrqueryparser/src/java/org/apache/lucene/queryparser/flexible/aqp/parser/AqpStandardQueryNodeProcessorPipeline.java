package org.apache.lucene.queryparser.flexible.aqp.parser;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.processors.NoChildOptimizationQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorPipeline;
import org.apache.lucene.queryparser.flexible.core.processors.RemoveDeletedQueryNodesProcessor;
import org.apache.lucene.queryparser.flexible.standard.builders.StandardQueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.parser.StandardSyntaxParser;
import org.apache.lucene.queryparser.flexible.standard.processors.AllowLeadingWildcardProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.AnalyzerQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.BooleanSingleChildOptimizationQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.BoostQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.DefaultPhraseSlopQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.FuzzyQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.PointQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.PointRangeQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.MatchAllDocsQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.MultiFieldQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.MultiTermRewriteMethodProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.OpenRangeQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.PhraseSlopQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.RemoveEmptyNonLeafQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.TermRangeQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.WildcardQueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpBOOSTProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpCLAUSEProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpDEFOPProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpFIELDProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpFUZZYProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpFuzzyModifierProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpGroupQueryOptimizerProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpMODIFIERProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpOPERATORProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpOptimizationProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQANYTHINGProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQNORMALProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQPHRASEProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQPHRASETRUNCProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQRANGEEXProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQRANGEINProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpQTRUNCATEDProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpTMODIFIERProcessor;
import org.apache.lucene.queryparser.flexible.aqp.processors.AqpTreeRewriteProcessor;
import org.apache.lucene.search.Query;

/**
 * This is based on the standard/processors
 * 
 * This pipeline has all the processors needed to process a query node tree,
 * generated by {@link StandardSyntaxParser}, already assembled.
 * <p>
 * The order they are assembled affects the results.
 * <p>
 * This processor pipeline was designed to work with
 * {@link StandardQueryConfigHandler}.
 * <p>
 * The result query node tree can be used to build a {@link Query} object using
 * {@link StandardQueryTreeBuilder}.
 * 
 * @see StandardQueryTreeBuilder
 * @see StandardQueryConfigHandler
 * @see StandardSyntaxParser
 */
public class AqpStandardQueryNodeProcessorPipeline extends
    QueryNodeProcessorPipeline {

  public AqpStandardQueryNodeProcessorPipeline(QueryConfigHandler queryConfig) {
    super(queryConfig);

    add(new AqpDEFOPProcessor());
    add(new AqpTreeRewriteProcessor());

    add(new AqpCLAUSEProcessor());
    add(new AqpMODIFIERProcessor());

    add(new AqpOPERATORProcessor());
    add(new AqpTMODIFIERProcessor());
    add(new AqpBOOSTProcessor());
    add(new AqpFUZZYProcessor());

    add(new AqpQRANGEINProcessor());
    add(new AqpQRANGEEXProcessor());
    add(new AqpQNORMALProcessor());
    add(new AqpQPHRASEProcessor());
    add(new AqpQPHRASETRUNCProcessor());
    add(new AqpQTRUNCATEDProcessor());
    add(new AqpQRANGEINProcessor());
    add(new AqpQRANGEEXProcessor());
    add(new AqpQANYTHINGProcessor());
    add(new AqpFIELDProcessor());

    add(new AqpFuzzyModifierProcessor());

    // TODO: remove the processors which are not needed
    // these were the standard guys before AQP ones were added

    add(new WildcardQueryNodeProcessor());
    add(new MultiFieldQueryNodeProcessor());
    add(new FuzzyQueryNodeProcessor());
    add(new MatchAllDocsQueryNodeProcessor());
    add(new OpenRangeQueryNodeProcessor());
    add(new PointQueryNodeProcessor());
    add(new PointRangeQueryNodeProcessor());
    add(new TermRangeQueryNodeProcessor());
    add(new AllowLeadingWildcardProcessor());
    add(new AnalyzerQueryNodeProcessor());
    add(new PhraseSlopQueryNodeProcessor());
    // add(new GroupQueryNodeProcessor());
    add(new NoChildOptimizationQueryNodeProcessor());
    add(new RemoveDeletedQueryNodesProcessor());
    add(new RemoveEmptyNonLeafQueryNodeProcessor());
    add(new BooleanSingleChildOptimizationQueryNodeProcessor());
    add(new DefaultPhraseSlopQueryNodeProcessor());
    add(new BoostQueryNodeProcessor());
    add(new MultiTermRewriteMethodProcessor());

    add(new AqpOptimizationProcessor());
    add(new AqpGroupQueryOptimizerProcessor());
  }

}
