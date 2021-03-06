# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# By default the script will use JAVA_HOME to determine which java
# to use, but you can set a specific path for Solr to use without
# affecting other Java applications on your server/workstation.
#SOLR_JAVA_HOME=""

# Increase Java Heap as needed to support your indexing / query needs
#SOLR_HEAP="512m"

# Expert: If you want finer control over memory options, specify them directly
# Comment out SOLR_HEAP if you are using this though, that takes precedence
SOLR_JAVA_MEM="-Xmx${SOLR_MEMORY_MX:-1024m} -Xms${SOLR_MEMORY_MS:-1024m}"

# Enable verbose GC logging
GC_LOG_OPTS=${GC_LOG_OPTS:-"-verbose:gc"}

# These GC settings have shown to work well for a number of common Solr workloads
GC_TUNE=${GC_TUNE:-""}

# Set the ZooKeeper connection string if using an external ZooKeeper ensemble
# e.g. host1:2181,host2:2181/chroot
# Leave empty if not using SolrCloud
#ZK_HOST=""

# Set the ZooKeeper client timeout (for SolrCloud mode)
#ZK_CLIENT_TIMEOUT="15000"

# By default the start script uses "localhost"; override the hostname here
# for production SolrCloud environments to control the hostname exposed to cluster state
#SOLR_HOST="192.168.1.1"

# By default the start script uses UTC; override the timezone if needed
#SOLR_TIMEZONE="UTC"

# Set to true to activate the JMX RMI connector to allow remote JMX client applications
# to monitor the JVM hosting Solr; set to "false" to disable that behavior
# (false is recommended in production environments)
ENABLE_REMOTE_JMX_OPTS=${ENABLE_REMOTE_JMX_OPTS:-"false"}

# The script will use SOLR_PORT+10000 for the RMI_PORT or you can set it here
# RMI_PORT=18983

# Location where Solr should write logs to; should agree with the file appender
# settings in server/resources/log4j.properties
SOLR_LOGS_DIR=${MONTYSOLR_SHARED_DIR:-./server}/logs

# Sets the port Solr binds to, default is 8983
SOLR_PORT=${MONTYSOLR_JETTY_PORT:-9983}


# Set the thread stack size
SOLR_OPTS="${SOLR_OPTS} -Xss256k \
-Dsolr.data.dir=${MONTYSOLR_INDEX_DIR:-./server/solr/collection1/data} \
-Dpython.path=${MONTYSOLR_PYTHON_PATH:-./server/resources} \
-Dmontysolr.reuseCache=${MONTYSOLR_REUSE_CACHE:-false} \
-Dmontysolr.batch.workdir=${MONTYSOLR_INDEX_DIR:-./server/solr/collection1/data/}/batch-handler \
-Dmontysolr.enable.write=${MONTYSOLR_ENABLE_WRITE:-false} \
-Dmontysolr.warmSearcher=${MONTYSOLR_WARM_SEARCHER:-false} \
-Dmontysolr.load.citation_cache=${MONTYSOLR_LOAD_CITATION_CACHE:-true} \
-Dmontysolr.autoCommit.maxDocs=${MONTYSOLR_MAX_DOCS:-40000} \
-Dmontysolr.autoCommit.maxTime=${MONTYSOLR_MAX_TIME:-1800000} \
-Dmontysolr.autoSoftCommit.maxTime=${MONTYSOLR_SOFT_MAX_TIME:-1} \
-Dmontysolr.coordinate=${MONTYSOLR_COORDINATE:-true} \
-Dmontysolr.stagger.maxDelay=${MONTYSOLR_STAGGER_MAX_DELAY:-2700} \
-Dmontysolr.stagger.numInstances=${MONTYSOLR_STAGGER_NUM_INSTANCES:-3} \
-Dmontysolr.stagger.numTop=${MONTYSOLR_STAGGER_NUM_TOP:-1} \
-Dmontysolr.stagger.numBottom=${MONTYSOLR_STAGGER_NUM_BOTTOM:-1} \
-Dmontysolr.replication.confFiles=${MONTYSOLR_REPLICATION_CONF_FILES:-citation_cache.gen,citation_cache.dict,citation_cache.refs,citation_cache.cit,ads_text_multi.synonyms,ads_text_simple.synonyms,ads_text.kill,ads_text.kill_sens,author_curated.synonyms,author_generated.translit,stopwords.txt} \
-Dsolr.filterCache.size=${SOLR_FILTER_CACHE_SIZE:-${SOLR_CACHE_SIZE:-512}} \
-Dsolr.filterCache.initialSize=${SOLR_FILTER_CACHE_INITIAL_SIZE:-${SOLR_CACHE_INITIAL_SIZE:-512}} \
-Dsolr.filterCache.autowarmCount=${SOLR_FILTER_CACHE_AUTOWARM_COUNT:-${SOLR_CACHE_AUTOWARM_COUNT:-128}} \
-Dsolr.queryResultCache.size=${SOLR_QUERY_RESULT_CACHE_SIZE:-${SOLR_CACHE_SIZE:-512}} \
-Dsolr.queryResultCache.initialSize=${SOLR_QUERY_RESULT_CACHE_INITIAL_SIZE:-${SOLR_CACHE_INITIAL_SIZE:-512}} \
-Dsolr.queryResultCache.autowarmCount=${SOLR_QUERY_RESULT_CACHE_AUTOWARM_COUNT:-${SOLR_CACHE_AUTOWARM_COUNT:-128}} \
-Dsolr.queryResultCache.maxRamMB=${SOLR_QUERY_RESULT_CACHE_MAX_RAM_MB:-512} \
-Dsolr.queryResultMaxDocsCached=${SOLR_QUERY_RESULT_MAX_DOCS_CACHED:-200} \
-Dsolr.queryResultWindowSize=${SOLR_QUERY_RESULT_WINDOW_SIZE:-25} \
-Dsolr.documentCache.size=${SOLR_DOCUMENT_CACHE_SIZE:-${SOLR_CACHE_SIZE:-512}} \
-Dsolr.documentCache.initialSize=${SOLR_DOCUMENT_CACHE_INITIAL_SIZE:-${SOLR_CACHE_INITIAL_SIZE:-512}} \
-Dsolr.documentCache.autowarmCount=${SOLR_DOCUMENT_CACHE_AUTOWARM_COUNT:-${SOLR_CACHE_AUTOWARM_COUNT:-128}} \
-Dsolr.citationCache.size=${SOLR_CITATION_CACHE_SIZE:-${SOLR_CACHE_SIZE:-512}} \
-Dsolr.citationCache.initialSize=${SOLR_CITATION_CACHE_INITIAL_SIZE:-${SOLR_CACHE_INITIAL_SIZE:-512}} \
-Dsolr.citationCache.autowarmCount=${SOLR_CITATION_CACHE_AUTOWARM_COUNT:-${SOLR_CACHE_AUTOWARM_COUNT:-128}} \
-Dsolr.ramBufferSize=${SOLR_RAM_BUFFER_SIZE:-1000} \
-Dsolr.maxBufferedDocs=${SOLR_MAX_BUFFERED_DOCS:-50000} \
-Dsolr.mergePolicy.maxMergeAtOnce=${SOLR_MERGE_POLICY_MAX_MERGE_AT_ONCE:-10} \
-Dsolr.mergePolicy.segmentsPerTier=${SOLR_MERGE_POLICY_SEGMENTS_PER_TIER:-10} \
-Dsolr.useCompoundFile=${SOLR_USE_COMPOUND_FILE:-false} \
-Dmontysolr.logdir=${SOLR_LOGS_DIR} \
-DhostContext=${SOLR_HOST_CONTEXT:-solr} \
${MONTYSOLR_EXTRA_JAVA_OPTS}
"

# Anything you add to the SOLR_OPTS variable will be included in the java
# start command line as-is, in ADDITION to other options. If you specify the
# -a option on start script, those options will be appended as well. Examples:
#SOLR_OPTS="$SOLR_OPTS -Dsolr.autoSoftCommit.maxTime=3000"
#SOLR_OPTS="$SOLR_OPTS -Dsolr.autoCommit.maxTime=60000"
#SOLR_OPTS="$SOLR_OPTS -Dsolr.clustering.enabled=true"

# Location where the bin/solr script will save PID files for running instances
# If not set, the script will create PID files in $SOLR_TIP/bin
#SOLR_PID_DIR=

# Path to a directory for Solr to store cores and their data. By default, Solr will use server/solr
# If solr.xml is not stored in ZooKeeper, this directory needs to contain solr.xml
SOLR_HOME=${SOLR_HOME:-${DEFAULT_SERVER_DIR}/solr}

# Solr provides a default Log4J configuration properties file in server/resources
# however, you may want to customize the log settings and file appender location
# so you can point the script to use a different log4j.properties file
LOG4J_PROPS=${LOG4J_PROPS:-${DEFAULT_SERVER_DIR}/resources/log4j.properties}

# Uncomment to set SSL-related system properties
# Be sure to update the paths to the correct keystore for your environment
#SOLR_SSL_KEY_STORE=/home/shalin/work/oss/shalin-lusolr/solr/server/etc/solr-ssl.keystore.jks
#SOLR_SSL_KEY_STORE_PASSWORD=secret
#SOLR_SSL_TRUST_STORE=/home/shalin/work/oss/shalin-lusolr/solr/server/etc/solr-ssl.keystore.jks
#SOLR_SSL_TRUST_STORE_PASSWORD=secret
#SOLR_SSL_NEED_CLIENT_AUTH=false
#SOLR_SSL_WANT_CLIENT_AUTH=false

# Uncomment if you want to override previously defined SSL values for HTTP client
# otherwise keep them commented and the above values will automatically be set for HTTP clients
#SOLR_SSL_CLIENT_KEY_STORE=
#SOLR_SSL_CLIENT_KEY_STORE_PASSWORD=
#SOLR_SSL_CLIENT_TRUST_STORE=
#SOLR_SSL_CLIENT_TRUST_STORE_PASSWORD=

# Settings for authentication
#SOLR_AUTHENTICATION_CLIENT_CONFIGURER=
#SOLR_AUTHENTICATION_OPTS=

# Settings for ZK ACL
#SOLR_ZK_CREDS_AND_ACLS="-DzkACLProvider=org.apache.solr.common.cloud.VMParamsAllAndReadonlyDigestZkACLProvider \
#  -DzkCredentialsProvider=org.apache.solr.common.cloud.VMParamsSingleSetCredentialsDigestZkCredentialsProvider \
#  -DzkDigestUsername=admin-user -DzkDigestPassword=CHANGEME-ADMIN-PASSWORD \
#  -DzkDigestReadonlyUsername=readonly-user -DzkDigestReadonlyPassword=CHANGEME-READONLY-PASSWORD"
#SOLR_OPTS="$SOLR_OPTS $SOLR_ZK_CREDS_AND_ACLS"
