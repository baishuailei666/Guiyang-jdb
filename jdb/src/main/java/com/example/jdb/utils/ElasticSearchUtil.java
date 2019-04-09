package com.example.jdb.utils;


import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.*;

/**
 * ElasticSearch工具类
 * 创建连接、写入数据、释放资源
 *
 * @author 白帅雷
 * @date 2019-01-17
 */
public class ElasticSearchUtil {

    private static TransportClient client = null;

    private static Logger logger = Logger.getLogger(ElasticSearchUtil.class);
    // 读取配置文件
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("es");

    private ElasticSearchUtil() {
        try {
            // ElasticSearch集群和节点名称
            String cluster = resourceBundle.getString("es.cluster.name");
            String node = resourceBundle.getString("es.node.name");
            Settings settings = Settings.builder()
                    .put("cluster.name", cluster)
                    .put("node.name",node).build();
            // ElasticSearch ip
            String host = resourceBundle.getString("es.Ip");
            // ElasticSearch port
            int port = Integer.parseInt(resourceBundle.getString("es.Port"));
            // 连接ES服务端
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            logger.info("ElasticSearch connect success!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Err.ElasticSearch connect failure!" + e);
        }
    }

    /**
     * 建立连接
     *
     * @return TransportClient
     */
    private static TransportClient getConnection(){

        if (client == null){
            synchronized (ElasticSearchUtil.class){
                if (client == null){
                    new ElasticSearchUtil();
                }
            }
        }
        return  client;

    }

    /**
     * 释放资源
     */
    public static void release() {
        logger.info("ElasticSearch release!");
        Objects.requireNonNull(client).close();
    }


    /**
     * 写入数据给ElasticSearch
     *
     * @param map map
     */
    public static void writeToEs(Map<String, Object> map) {
        logger.info("写数据给ElasticSearch! map." + map);
        Map<String, Object> json = new HashMap<>(1);
        json.put("tId", map.get("tId"));
        json.put("date", map.get("time"));
        // cpu
        List<Object> listCpu = new ArrayList<>(1);
        listCpu.add( map.get("sysCpu"));
        listCpu.add( map.get("useCpu"));
        json.put("cpu", listCpu);
        // mem
        List<Object> listMem = new ArrayList<>(1);
        listMem.add( map.get("sysMem"));
        listMem.add( map.get("useMem"));
        json.put("mem", listMem);
        // disk
        List<Object> listDisk = new ArrayList<>(1);
        listDisk.add( map.get("sysDisk"));
        listDisk.add( map.get("useDisk"));
        json.put("disk", listDisk);

        // ElasticSearch Index
        String esIndex = resourceBundle.getString("es.Index");
        // ElasticSearch Type
        String esType = resourceBundle.getString("es.Type");
        client = getConnection();
        IndexResponse response = client.prepareIndex(esIndex, esType)
                .setSource(json)
                .get();
        logger.info("ElasticSearch create index! result." + response.getResult() + "; index." + esIndex + "; type." + esType);
    }

    /**
     * 从ElasticSearch中查询日志数据
     *
     * @param tiId 终端ID
     * @return Map
     */
    public static Map<String, Object> queryLogFromEs(String tiId, int page, int size) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<>(1);
            client = getConnection();
            SearchResponse searchResponse = client.prepareSearch(resourceBundle.getString("es.index_log"))
                    .setTypes(tiId)
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    // 分页:setFrom即页码数 默认从0开始、setSize即一页显示多少条
                    .setFrom(page - 1).setSize(size)
                    .get();
            SearchHits hits = searchResponse.getHits();
            long count = hits.getTotalHits();
            SearchHit[] searchHits = hits.getHits();
            List<Object> list = new ArrayList<>(1);
            for (SearchHit searchHit :
                    searchHits) {
                list.add(searchHit.getSource().get("message"));
            }

            map.put("data", list);
            map.put("count", count);
            map.put("code", 0);
            map.put("msg", "成功");
        } catch (Exception e) {
            logger.error("Err.从ElasticSearch中查询日志数据异常!" + e);
            map.put("code", 1);
            map.put("msg", "失败");
            map.put("err", "Err.从ElasticSearch中查询日志数据异常!" + e);
        }
        return map;
    }

    /**
     * 使用es的帮助类
     */
    public static XContentBuilder createJson() throws Exception {
        // 创建json对象, 其中一个创建json的方式
        return XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "baishuailei")
                .field("postDate", new Date())
                .field("message", "trying to out ElasticSearch")
                .endObject();
    }


    public static void insertIndex() throws Exception {
        XContentBuilder source = createJson();
        // 存json入索引中
        IndexResponse response = client.prepareIndex("", "", "").setSource(source).get();
        // 结果获取
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        boolean created = response.isFragment();
        System.out.println(index + " : " + type + ": " + id + ": " + version + ": " + created);
    }

    public static void deleteIndex(String index) {
        System.out.println("deleteIndex" + index);
        client = getConnection();
        DeleteIndexResponse deleteResponse = client.admin().indices().prepareDelete(new String[] {index}).execute().actionGet();

        System.out.println(deleteResponse.isAcknowledged());
    }

}
