package com.luoyu.webmagic.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/26 上午11:50
 * @Version: 1.0.0
 */
public class CustomPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {

        // 解析页面 获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();

        // 判断获取到的集合是否为空 如果为空表示这是招聘详情页 如果不为空表示这是列表页
        // 如果为空 表示这是招聘详情页 解析页面获取招聘信息 保存数据
        // 如果不为空 解析详情页的url地址 放到任务队列中
        if (list.size() == 0) {
            this.saveJobInfo(page);
        } else {
            for (Selectable selectable:list){
                String jobInfoUrl = selectable.links().toString();
                // 把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            // 获取下一页的url
            String bkurl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            // 把url放到任务队列中
            page.addTargetRequest(bkurl);
        }

/*        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://search.51job\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://search.51job\\.com/[\\w\\-]+/[\\w\\-]+)").all());*/
    }

    //解析页面获取招聘信息 保存数据
    private void saveJobInfo(Page page) {

        // 创建对象
//        Job job = new Job();

        // 解析页面
        Html html = page.getHtml();

        // 获取数据
//        job.setCompanyName(String.valueOf(html.css("div.cn p.cname a","text")));
//        job.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text() );
//        job.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
//        job.setJobName(html.css("div.cn h1","text").toString());
//        job.setJobAddr(html.css("div.cn p.msg","title").toString());
//        job.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
//        job.setUrl(page.getUrl().toString());
//        job.setSalaryMax(Jsoup.parse(html.css("div.cn strong").toString()).text());
//        job.setSalaryMin("");
//        String time = html.css("div.cn p.msg","title").toString();
//        job.setTime(time.substring(time.length()-8));

        System.out.println(html.css("div.cn h1","text").toString() + html.css("div.cn p.msg","title").toString());
        // 把结果保存起来
//        page.putField("jobinfo",job);

    }

    @Override
    public Site getSite() {
        return site;
    }

}
