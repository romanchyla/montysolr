package org.apache.lucene.queryparser.flexible.aqp.processors;

import java.util.List;

import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.FuzzyQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QuotedFieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.SlopQueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.nodes.WildcardQueryNode;
import org.apache.lucene.queryparser.flexible.aqp.config.AqpFeedback;
import org.apache.lucene.queryparser.flexible.aqp.config.AqpStandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.aqp.nodes.AqpFuzzyModifierNode;
import org.apache.lucene.queryparser.flexible.aqp.nodes.SlowFuzzyQueryNode;

/**
 * Rewrites the query node which is below the {@link AqpFuzzyModifierNode}
 * 
 * The actions are:
 * 		FieldQueryNode 
 * 			- query is turned into FuzzyQueryNode
 * 			- invalid syntax is raised if not 0.0 > fuzzy < 1.0
 *  	WildcardQueryNode
 *  	QuotedFieldQueryNode
 *  		- wrapped with SlopQueryNode
 * 			
 *
 */
public class AqpFuzzyModifierProcessor extends QueryNodeProcessorImpl implements
		QueryNodeProcessor {

	@Override
	protected QueryNode preProcessNode(QueryNode node)
			throws QueryNodeException {
		if (node instanceof AqpFuzzyModifierNode) {
			QueryNode child = ((AqpFuzzyModifierNode) node).getChild();
			Float fuzzy = ((AqpFuzzyModifierNode) node).getFuzzyValue();
			
			QueryConfigHandler config = getQueryConfigHandler();
			
			if (child instanceof QuotedFieldQueryNode ||
					child instanceof WildcardQueryNode) {
				
				if (fuzzy.intValue() < fuzzy) {
					
					if (config.has(AqpStandardQueryConfigHandler.ConfigurationKeys.FEEDBACK)) {
						AqpFeedback feedback = config.get(AqpStandardQueryConfigHandler.ConfigurationKeys.FEEDBACK);
						feedback.sendEvent(feedback.createEvent(AqpFeedback.TYPE.WARN, 
								this.getClass(), node, 
								"For phrases and wildcard queries the float attribute " + fuzzy + 
								" is automatically converted to: " + fuzzy.intValue()));
					}
				}
				return new SlopQueryNode(child, fuzzy.intValue());
			}
			else if (child instanceof FieldQueryNode) {
				
				FieldQueryNode fn = (FieldQueryNode) child;
				
				if (config.has(AqpStandardQueryConfigHandler.ConfigurationKeys.ALLOW_SLOW_FUZZY) != false &&
						config.get(AqpStandardQueryConfigHandler.ConfigurationKeys.ALLOW_SLOW_FUZZY) == true) {
					
					if (fuzzy<0.0f || fuzzy>1.0f) {
						throw new QueryNodeException(new MessageImpl(
							QueryParserMessages.INVALID_SYNTAX,
							node.toString() + "\nSimilarity s must be 0.0 > s < 1.0"));
					}
					
					return new SlowFuzzyQueryNode(fn.getFieldAsString(), fn.getTextAsString(), fuzzy,
							fn.getBegin(), fn.getEnd());
				}
				else {
					return new FuzzyQueryNode(fn.getFieldAsString(), fn.getTextAsString(), fuzzy,
						fn.getBegin(), fn.getEnd());
				}
			}
			else  {
				throw new QueryNodeException(new MessageImpl(
						QueryParserMessages.INVALID_SYNTAX,
						node.toString() + "\nUse of ~ is not allowed here"));
			}
			
		}
		return node;
	}

	@Override
	protected QueryNode postProcessNode(QueryNode node)
			throws QueryNodeException {
		return node;
	}

	@Override
	protected List<QueryNode> setChildrenOrder(List<QueryNode> children)
			throws QueryNodeException {
		return children;
	}

}
