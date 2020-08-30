package rr.labs.elastic;

public class ElasticsearchContainer extends org.testcontainers.elasticsearch.ElasticsearchContainer {

    private static final String ELASTIC_SEARCH_DOCKER = "elasticsearch:7.9.0";

    private static final String CLUSTER_NAME = "cluster.name";

    private static final String ELASTIC_SEARCH = "elasticsearch";

    public ElasticsearchContainer() {
        super(ELASTIC_SEARCH_DOCKER);
        this.addFixedExposedPort(9200, 9200);
        this.addFixedExposedPort(9300, 9300);
        this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
