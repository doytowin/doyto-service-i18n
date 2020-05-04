package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * I18nXlsxView
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@Component
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class I18nXlsxView extends AbstractXlsxView {

    private static final int TITLE_ROW_IDX = 0;

    private String[] ignoreColumns = new String[] {"id", "valid", "createUserId", "createTime", "updateUserId", "updateTime"};

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {

        String groupName = (String) model.get("group");
        Sheet sheet = workbook.createSheet(groupName);
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short) 300);

        List<Map<String, Object>> data = (List<Map<String, Object>>) model.get("data");
        // 移除不需要导出的列
        data.forEach(rowData -> Arrays.stream(ignoreColumns).forEach(rowData::remove));

        int rowCnt = 0;
        for (Map<String, Object> result : data) {
            if (rowCnt % 100 == 0 && log.isDebugEnabled()) {
                log.debug("已处理{}行数据", rowCnt);
            }

            rowCnt++;//数据从第一行开始填充
            Row row = sheet.createRow(rowCnt);
            Object[] dataRow = result.values().toArray();
            for (int col = 0; col < dataRow.length; col++) {
                if (dataRow[col] != null) {
                    row.createCell(col).setCellValue(String.valueOf(dataRow[col]));
                }
            }

            // 最后填充标题并且设置自动列宽
            if (rowCnt == data.size()) {
                String[] titles = result.keySet().toArray(EMPTY_STRING_ARRAY);
                sheet.createRow(TITLE_ROW_IDX);
                for (int col = 0; col < titles.length; col++) {
                    sheet.getRow(TITLE_ROW_IDX).createCell(col).setCellValue(titles[col].replace("locale_", ""));
                    sheet.autoSizeColumn(col);
                }
            }
        }

        String filename = "i18n_" + groupName + ".xlsx";
        setResponseFilename(filename, request, response);
    }

    private void setResponseFilename(String filename, HttpServletRequest request, HttpServletResponse response) {
        String userAgent = request.getHeader("User-Agent");
        byte[] bytes = userAgent.contains("MSIE") ? filename.getBytes() : filename.getBytes(StandardCharsets.UTF_8); // filename.getBytes("UTF-8")处理safari的乱码问题
        filename = new String(bytes, StandardCharsets.ISO_8859_1); // 各浏览器基本都支持ISO编码
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename)); // 文件名外的双引号处理firefox的空格截断问题
    }
}

