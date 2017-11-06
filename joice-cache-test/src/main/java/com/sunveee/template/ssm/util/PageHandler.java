package com.sunveee.template.ssm.util;

import org.springframework.ui.Model;

public class PageHandler {

    /**
     * 分页列表页面处理
     * 
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @param infoCount 总数
     * @param model
     */
    public static void handlePage(Integer pageNo, Integer pageSize, Integer infoCount, Model model) {
        pageNo = pageNo != null ? pageNo : 0;
        pageSize = pageSize != null ? pageSize : 10;
        int pageCount = infoCount / pageSize;
        if (infoCount % pageSize != 0) {
            pageCount++;
        }
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("infoCount", infoCount);
        model.addAttribute("pageCount", pageCount); // 总共页数
    }
}
