package com.thylovezj.hospital;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
public class JsoupTest {
    @Test
    void testJsoup() throws IOException {
        // 京东搜索请求
        String url = "https://search.jd.com/Search?keyword=java";

        // Jsoup返回Document就是浏览器Document对象
        Document document = Jsoup.parse(new URL(url), 30000);
        // 所有在JS中的方法都可以使用
        Element element = document.getElementById("J_goodsList");
        // 获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        // 获取元素中的内容
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            System.out.println("==========================================================");
            System.out.println(img);
            System.out.println(price);
            System.out.println(title);
        }
    }
}
