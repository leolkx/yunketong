package org.fzu.ybk.mapper;

import org.apache.ibatis.annotations.*;
import org.fzu.ybk.entity.DataDictionary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DataDictionaryMapper {
    @Insert("INSERT INTO data_dictionary_key " +
            "(dict_name, creation_date, is_deleted) " +
            "VALUES (#{dictName}, #{createDate}, #{isDeleted} )")
    void insertDictKey(String dictName, String createDate, boolean isDeleted);

    @Insert("INSERT INTO data_dictionary_key " +
            "(dict_name, dict_description, creation_date, is_deleted) " +
            "VALUES (#{dictName}, #{dictDescription}, #{createDate}, #{isDeleted} )")
    void insertDictKeyandDes(String dictName, String dictDescription, String createDate, boolean isDeleted);

    @Select("SELECT id " +
            "FROM data_dictionary_key " +
            "WHERE dict_name = #{dictName} AND is_deleted = 0")
    Long getDictKeyByDictName(String dictName);

    @Update("UPDATE data_dictionary_key " +
            "SET dict_name = #{newDictName} " +
            "WHERE id = #{dictId}")
    void updateDictKeyByDictId(Long dictId,String newDictName);

    //    @Delete("DELETE FROM data_dictionary WHERE dict_code = #{dictCode} AND data_code = #{dataCode} ")
    @Update("UPDATE data_dictionary_key " +
            "SET is_deleted = 1 " +
            "WHERE id = #{dictId}")
    void deleteDictKeyByDictId(Long dictId);
    @Update("UPDATE data_dictionary_key " +
            "SET is_deleted = 0 " +
            "WHERE id = #{dictId}")
    void removeDictKeyDeletionMarkByDictId(Long dictId);


//-------- delimeter of key and value

    @Insert("INSERT INTO data_dictionary_value " +
            "(dict_id, data_name, creation_date, is_deleted, data_order) " +
            "VALUES (#{dictId},#{dataName}, #{createDate}, #{isDeleted}, #{dataOrder} )")
    void insertDictValue(Long dictId, String dataName, String createDate, boolean isDeleted, Long dataOrder);

    @Insert("INSERT INTO data_dictionary_value " +
            "(dict_id, data_name, textValue, textDefault, creation_date, is_deleted, data_order) " +
            "VALUES (#{dictId},#{dataName}, #{textValue}, #{textDefault}, #{createDate}, #{isDeleted}, #{dataOrder} )")
    void insertTextDictValue(Long dictId, String dataName, String textValue, String textDefault, String createDate, boolean isDeleted, Long dataOrder);

    @Select("SELECT id " +
            "FROM data_dictionary_value " +
            "WHERE dict_id = #{dictId} AND data_name = #{dataName} AND is_deleted = 0")
    Long getDictValueByDictIdAndDataName(Long dictId, String dataName);

    @Select("SELECT COUNT(1) " +
            "FROM data_dictionary_value " +
            "WHERE dict_id = #{dictId} AND is_deleted = 0")
    Long getDictValueNumber(Long dictId);

//    @Select("SELECT k.dict_description, k.dict_name " +
//            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
//            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
//            "ORDER BY k.id,v.data_order ASC " +
//            "LIMIT #{size} OFFSET #{offset}  ")
//    List<DataDictionary> getAllDataFromDataDictionary(Long size, Long offset);

    @Select("SELECT dict_description, dict_name FROM data_dictionary_key WHERE is_deleted = 0")
    List<DataDictionary> getAllDataFromDataDictionary(Long size, Long offset);


    @Select("SELECT v.textValue, v.data_name, v.textDefault " +
            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
            "ORDER BY k.id ASC " +
            "LIMIT #{size} OFFSET #{offset}  ")
    List<DataDictionary> getAllTextDataFromDataDictionary(Long size, Long offset);

    @Select("SELECT data_order, textValue, data_name, textDefault " +
            "FROM data_dictionary_value " +
            "WHERE dict_id = #{dictId} and is_deleted = 0 " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDictId(Long dictId,Long size, Long offset);

    @Select("SELECT tmp.data_name FROM (SELECT data_name, is_deleted, data_order" +
            "FROM data_dictionary_value WHERE dict_id = #{dictId} ) AS tmp " +
            "WHERE tmp.data_name like '%${dataName}%' AND tmp.is_deleted = 0 " +
            "ORDER BY k.id,v.data_order ASC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDictIdAndDataNameLikely(Long dictId, String dataName,Long size, Long offset);

    @Select("SELECT tmp.textValue, tmp.data_name, tmp.textDefault FROM (SELECT * " +
            "FROM data_dictionary_value WHERE dict_id = #{dictId} ) AS tmp " +
            "WHERE tmp.data_name like '%${dataName}%' AND tmp.is_deleted = 0 ")
    List<DataDictionary> getTextDictValuesByDictIdAndDataNameLikely(Long dictId, String dataName);

    @Select("SELECT k.dict_name, v.data_name " +
            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
            "WHERE v.data_name LIKE '%${dataName}%' " +
            "ORDER BY k.id,v.data_order ASC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDataNameLikely(String dataName,Long size, Long offset);

    @Select("SELECT k.dict_name, v.data_name " +
            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
            "WHERE k.dict_name LIKE '%${dictName}%' " +
            "AND v.data_name LIKE '%${dataName}%' " +
            "ORDER BY k.id ASC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDictNameLikelyAndDataNameLikely(String dictName, String dataName,Long size, Long offset);

    @Select("SELECT k.dict_name, v.data_name " +
            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
            "WHERE v.data_name = #{dataName} " +
            "ORDER BY k.id ASC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDataName(String dataName,Long size, Long offset);

    @Select("SELECT k.dict_name, v.data_name " +
            "FROM data_dictionary_key AS k INNER JOIN data_dictionary_value AS v " +
            "ON k.id = v.dict_id AND k.is_deleted = 0 AND v.is_deleted = 0 " +
            "WHERE k.dict_name = #{dictName} " +
            "AND v.data_name = #{dataName} " +
            "ORDER BY k.id ASC " +
            "LIMIT #{size} OFFSET #{offset}")
    List<DataDictionary> getDictValuesByDictNameLikelyAndDataName(String dictName, String dataName,Long size, Long offset);


    //加不加@param注解似乎都可以，可能是java8以后 默认添加javac -param? 于是方法的反射也可以获得方法名了?
    @Update("UPDATE data_dictionary_value " +
            "SET data_name = #{newDataName} " +
            "WHERE id = #{dataId} ")
//    void updateDictValueByDataId(@Param("dataId") Long dataId, @Param("newDataName") String newDataName);
    void updateDictValueByDataId(Long dataId, String newDataName);

    @Update("UPDATE data_dictionary_value " +
            "SET data_order = #{order} " +
            "WHERE id = #{dataId} ")
    void updateDictValueOrderByDataId(@Param("dataId") Long dataId, @Param("order") Long order);

    @Update("UPDATE data_dictionary_value " +
            "SET data_name = #{dataName} " +
            ", textDefault = #{textDefault} " +
            "WHERE id = #{dataId} ")
//    void updateDictValueByDataId(@Param("dataId") Long dataId, @Param("newDataName") String newDataName);
    void updateDictTextValueByDataId(Long dataId, String dataName, String textDefault);


    @Update("UPDATE data_dictionary_value " +
            "SET is_deleted = 1 " +
            "WHERE id = #{dataId}")
    void deleteDictValueByDataId(Long dataId);

    @Update("UPDATE data_dictionary_value " +
            "SET is_deleted = 0 " +
            "WHERE id = #{dataId} ")
    void removeDictValueDeletionMarkByDataId(Long dataId);


}
