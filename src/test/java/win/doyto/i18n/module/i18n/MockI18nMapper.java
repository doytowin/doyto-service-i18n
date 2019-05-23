//package win.doyto.i18n.service;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.session.RowBounds;
//import win.doyto.i18n.module.i18n.I18nMapper;
//import win.doyto.i18n.module.i18n.I18nQuery;
//import win.doyto.i18n.module.i18n.Lang;
//
///**
// * MockI18nMapper
// *
// * @author f0rb on 2018-08-17.
// */
//public class MockI18nMapper implements I18nMapper {
//    @Override
//    public List<LinkedHashMap<String, ?>> langByGroup(String user, String group) {
//        return null;
//    }
//
//    @Override
//    public List<LinkedHashMap<String, ?>> pageLangByGroup(String user, String group, RowBounds rowBounds) {
//        return null;
//    }
//
//    @Override
//    public List<LinkedHashMap<String, ?>> query(I18nQuery i18n) {
//        return null;
//    }
//
//    @Override
//    public long count(I18nQuery i18n) {
//        return 0;
//    }
//
//    @Override
//    public List<Lang> langByGroupAndLocale(String user, String group, String locale) {
//        return null;
//    }
//
//    @Override
//    public List<Lang> langWithDefaultsByGroupAndLocale(String user, String group, String locale) {
//        return null;
//    }
//
//    @Override
//    public int saveTranslation(String user, String group, String locale, Map<String, String> langMap) {
//        return 0;
//    }
//
//    @Override
//    public void addLocaleOnGroup(String user, String group, String locale) {
//
//    }
//
//    @Override
//    public Object existGroup(String user, String group) {
//        return null;
//    }
//
//    @Override
//    public Object existLocaleOnGroup(String user, String group, String locale) {
//        return null;
//    }
//
//    @Override
//    public void createGroupTable(String owner, String name) {
//
//    }
//
//    @Override
//    public void deleteGroupTable(String owner, String name) {
//
//    }
//}
