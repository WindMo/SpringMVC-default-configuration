package mo.demo.controller;

import mo.demo.service.DemoService;
import mo.demo.util.ExcelAndCsvDownload;
import mo.demo.util.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WindShadow
 * @verion 2020/8/16.
 */

@Controller
public class DemoController {

    @Autowired
    DemoService service;

    @RequestMapping("/demo/test")
    @ResponseBody
    public String sayDemo() {

        return service.getDemoString();
    }

    @RequestMapping("/demo/xlsx")
    @ResponseBody
    public void down(HttpServletResponse response) {

        //导出文件路径
        //文件名
        String[] title = {"序号", "姓名", "学号", "性别", "入学日期"};
        String sheelName = "学生信息";
        //需要导出的数据
        List<String[]> dataList = new ArrayList<String[]>();
        dataList.add(new String[]{"1","东邪", "17232401001", "男", "2015年9月"});
        dataList.add(new String[]{"2","西毒", "17232401002", "女", "2016年9月"});
        dataList.add(new String[]{"3","南帝", "17232401003", "男", "2017年9月"});
        dataList.add(new String[]{"4","北丐", "17232401004", "男", "2015年9月"});
        dataList.add(new String[]{"5","中神通", "17232401005", "女", "2017年9月"});

        XSSFWorkbook workbook = ExcelUtils.createXSSFWorkbook(title,sheelName,dataList);
        try {
            ExcelAndCsvDownload.downLoadXlsxFile("myFile.xlsx",workbook,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
