package com.thylovezj.hospital.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.doc.MemorandumDoc;
import com.thylovezj.hospital.dto.MemorandumSimpleVo;
import com.thylovezj.hospital.mapper.MemorandumMapper;
import com.thylovezj.hospital.pojo.Memorandum;
import com.thylovezj.hospital.request.MemorandumReq;
import com.thylovezj.hospital.service.MemorandumService;
import com.thylovezj.hospital.util.UserHolder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author thylovezj
 */

@Service
public class MemorandumServiceImpl extends ServiceImpl<MemorandumMapper, Memorandum> implements MemorandumService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Resource
    MemorandumMapper memorandumMapper;

    /**
     * 利用ES对备忘录进行搜索
     *
     * @param message
     * @return
     */

    @Override
    public List<MemorandumDoc> searchItems(String message) throws IOException {
        List<MemorandumDoc> result = new ArrayList<>();
        SearchRequest request = new SearchRequest("minder");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("openId", UserHolder.getId()))
                .must(QueryBuilders.termQuery("title", message));
        request.source().query(boolQueryBuilder);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("一共搜索到" + total + "条数据");
        // 4.2文档数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            // 4.3反序列化
            MemorandumDoc memorandumDoc = JSON.parseObject(json, MemorandumDoc.class);
            result.add(memorandumDoc);
        }
        return result;
    }

    @Override
    public void addItemsToIndex(Memorandum memorandum, int insert) throws IOException {
        // 1. 准备request对象
        IndexRequest request = new IndexRequest("minder").id(insert + "");
        // 2. 准备JSON对象
        MemorandumDoc memorandumDoc = new MemorandumDoc(memorandum, insert);
        request.source(JSON.toJSONString(memorandumDoc), XContentType.JSON);
        // 3. 发送请求
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    @Override
    public int addItemsToDatabase(Memorandum memorandum) {
        memorandumMapper.insert(memorandum);
        return memorandum.getId();
    }


    @Override
    public void deleteIndex(int recordId) throws IOException {
        DeleteRequest request = new DeleteRequest("minder", recordId + "");
        restHighLevelClient.delete(request,RequestOptions.DEFAULT);
    }
}
