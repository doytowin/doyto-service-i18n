package win.doyto.i18n.view;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * I18nXlsxView
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
public class I18nXlsxView extends AbstractXlsxView {

    private static final int TITLE_ROW_IDX = 0;

    @Override
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<LinkedHashMap<String, Object>> data = (List<LinkedHashMap<String, Object>>) model.get("data");

        String groupＮame = (String) model.get("group");
        String sheetname = groupＮame;
        String filename = "国际化_" + groupＮame;
        Sheet sheet = workbook.createSheet(sheetname);
        sheet.setDefaultColumnWidth(10);
        sheet.setDefaultRowHeight((short) 300);

        int row = 0;
        for (LinkedHashMap<String, Object> result : data) {
            if (row % 100 == 0 && log.isDebugEnabled()) {
                log.debug("已处理{}行数据", row );
            }

            row++;//数据从第一行开始填充
            Row hssfRow = sheet.createRow(row);
            Object[] dataRow = result.values().toArray();
            for (int col = 0; col < dataRow.length; col++) {
                if (dataRow[col] != null) {
                    if (dataRow[col] instanceof Number) {
                        if (dataRow[col] instanceof Double) {
                            hssfRow.createCell(col).setCellValue((Double) dataRow[col]);
                        } else {
                            hssfRow.createCell(col).setCellValue(Double.valueOf(String.valueOf(dataRow[col])));
                        }
                    } else {
                        hssfRow.createCell(col).setCellValue(String.valueOf(dataRow[col]));
                    }
                }
            }

            // 最后填充标题并且设置自动列宽
            if (row == data.size()) {
                String[] titles = result.keySet().toArray(EMPTY_STRING_ARRAY);
                sheet.createRow(TITLE_ROW_IDX);
                for (int col = 0; col < titles.length; col++) {
                    sheet.getRow(TITLE_ROW_IDX).createCell(col).setCellValue(titles[col].replace("locale_", ""));
                    sheet.autoSizeColumn(col);
                }
            }
        }

        setResponseFilename(filename, request, response);
    }

    private void setResponseFilename(String filename, HttpServletRequest request, HttpServletResponse response) {
        //String filename = String.valueOf(model.get(FILENAME));
        if (filename != null && filename.length() > 0) {
            if (!filename.endsWith(".xls") || !filename.endsWith(".xlsx")) {
                filename = filename + ".xlsx";
            }
            try {
                String userAgent = request.getHeader("User-Agent");
                byte[] bytes = userAgent.contains("MSIE") ? filename.getBytes() : filename.getBytes("UTF-8"); // filename.getBytes("UTF-8")处理safari的乱码问题
                filename = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
                response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename)); // 文件名外的双引号处理firefox的空格截断问题
            } catch (UnsupportedEncodingException e) {//忽略
            }
        }
    }
}

