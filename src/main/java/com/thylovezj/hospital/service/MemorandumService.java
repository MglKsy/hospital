package com.thylovezj.hospital.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.thylovezj.hospital.doc.MemorandumDoc;
import com.thylovezj.hospital.dto.MemorandumSimpleVo;
import com.thylovezj.hospital.pojo.Memorandum;
import com.thylovezj.hospital.request.MemorandumReq;

import java.io.IOException;
import java.util.List;

/**
 * @author thylovezj
 */

public interface MemorandumService extends IService<Memorandum> {
    /**
     * 根据message在Elasticsearch中搜索备忘录
     *
     * @param message
     * @return
     * @throws IOException
     */
    List<MemorandumDoc> searchItems(String message) throws IOException;

    /**
     * 添加备忘录到ElasticSearch中
     * 添加索引到ES库
     *
     * @param memorandum
     */
    void addItemsToIndex(Memorandum memorandum, int insert) throws IOException;

    /**
     * 添加数据到数据库中
     *
     * @param memorandum
     * @return
     */
    int addItemsToDatabase(Memorandum memorandum);

    /**
     * 从索引库中删除recordId的文档
     *
     * @param recordId
     */
    void deleteIndex(int recordId) throws IOException;
}
