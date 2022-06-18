package com.thylovezj.hospital;

import com.alibaba.fastjson.JSON;
import com.thylovezj.hospital.doc.MemorandumDoc;
import com.thylovezj.hospital.pojo.Memorandum;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 */

@SpringBootTest
public class ElasticSearchTest {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 测试索引的创建
     * @throws IOException
     */
    @Test
    void contextLoader() throws IOException {
        // 1. 创建索引请求
        CreateIndexRequest kuang_index = new CreateIndexRequest("kuang_index");
        // 2. 执行请求 indicesClient,请求后获得相应
        CreateIndexResponse createIndexResponse = restHighLevelClient
                .indices().create(kuang_index, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 测试其索引是否存在
     * @throws IOException
     */
    @Test
    void testExist() throws IOException {
        GetIndexRequest request = new GetIndexRequest("kuang_index");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    void testDelete() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("kuang_index");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * 测试添加文档
     * @throws IOException
     */
    @Test
    void testAddDocument() throws IOException {
        //创建添加对象
        Memorandum memorandum = new Memorandum();
        memorandum.setOpenId("ofPvB5a88BN9XyyYIrens6bavgmo");
        memorandum.setUpdateTime(new Date());
        memorandum.setTitle("fdsa");
        memorandum.setContent("fdas");
        //创建请求
        IndexRequest request = new IndexRequest("kuang_index");
        //规则 put/kuang_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        //将数据放入请求 json
        IndexRequest source = request.source(JSON.toJSONString(memorandum), XContentType.JSON);

        //客户端发送请求,获取响应的结果
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    /**
     * 测试是否存在
     * @throws IOException
     */
    @Test
    void testIsExists() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index", "1");
        // 不获取返回 _source 的上下文了
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 取得文档信息
     * @throws IOException
     */
    @Test
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index", "1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());//打印文档的内容
        System.out.println(getResponse); // 返回的全部内容和命令行是一样的
    }

    /**
     * 更新文档信息
     * @throws IOException
     */
    @Test
    void testUpdateRequest() throws IOException {
        UpdateRequest request = new UpdateRequest("kuang_index", "1");
        request.timeout("1s");
        //创建更新对象
        Memorandum memorandum = new Memorandum();
        memorandum.setOpenId("ofPvB5a88BN9XyyYIrens6bavgmo");
        memorandum.setUpdateTime(new Date());
        memorandum.setTitle("ffff");
        memorandum.setContent("fdas");
        request.doc(JSON.toJSONString(memorandum),XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update);
    }

    /**
     * 删除文档
     * @throws IOException
     */
    @Test
    void testDeleteRequest() throws IOException {
        DeleteRequest request = new DeleteRequest("kuang_index", "1");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }


    /**
     * HighlightBuilder 构建高亮
     * @throws IOException
     */
    @Test
    void SearchRequest() throws IOException {
        SearchRequest request = new SearchRequest("kuang_index");
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         * 查询条件，我们可以使用QueryBuilder工具类来实现
         * termQuery 精确匹配
         *
         */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("content", "fda");
        sourceBuilder.query(termQueryBuilder);

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("============================================");
        for (SearchHit documentFields: searchResponse.getHits().getHits()){
            System.out.println(documentFields.getSourceAsMap());
        }
    }


    /**
     * 查询所有文件
     * @throws IOException
     */
    @Test
    void testMatch() throws IOException {
        SearchRequest request = new SearchRequest("minder");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("一共搜索到"+total+"条数据");
        // 4.2文档数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit:hits){
            String json = hit.getSourceAsString();
            // 4.3反序列化
            MemorandumDoc memorandumDoc = JSON.parseObject(json, MemorandumDoc.class);
            System.out.println(memorandumDoc);
        }
    }

    /**
     *  单查询条件
     */
    @Test
    void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("minder");

        request.source().query(QueryBuilders.matchQuery("openId", "dd"));
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("一共搜索到"+total+"条数据");
        // 4.2文档数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit:hits){
            String json = hit.getSourceAsString();
            // 4.3反序列化
            MemorandumDoc memorandumDoc = JSON.parseObject(json, MemorandumDoc.class);
            System.out.println(memorandumDoc);
        }
    }


    @Test
    void testBoolSearch() throws IOException {
        SearchRequest request = new SearchRequest("minder");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("openId","ddd"))
                .should(QueryBuilders.termQuery("title","哈哈"))
                .should(QueryBuilders.termQuery("content","天气"));
        request.source().query(boolQueryBuilder);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("一共搜索到"+total+"条数据");
        // 4.2文档数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit:hits){
            String json = hit.getSourceAsString();
            // 4.3反序列化
            MemorandumDoc memorandumDoc = JSON.parseObject(json, MemorandumDoc.class);
            System.out.println(memorandumDoc);
        }
    }
}
