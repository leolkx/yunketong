package org.fzu.ybk.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Mapper
//@Repository
public interface RichTextMapper {
    @Insert("INSERT INTO rich_text (rich_text, is_deleted) VALUES ( #{text}, 0 ) ")
    void insertText(String text);

    @Select("SELECT rich_text FROM rich_text WHERE id = #{textId}")
    String getText(Long textId);

    @Update("UPDATE rich_text SET rich_text = #{text} WHERE id = #{textId}")
    void updateText(Long textId, String text);

    @Delete("DELETE FROM rich_text WHERE id = #{textId} ")
    void deleteText(Long textId);

    @Select("SELECT LAST_INSERT_ID()")
    Long LAST_INSERT_ID();

    @Select( "SELECT id from rich_text order by id DESC limit 1" )
    Long getLastId();
}
