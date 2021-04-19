package org.fzu.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.fzu.ybk.entity.PublishedActivity;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/12 20:06
 */

@Repository
public interface PublishedActivityMapper extends BaseMapper<PublishedActivity> {
    @Select( "SELECT id FROM published_activity order by id DESC limit 1" )
    Long getLastActivityId();
}
