package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Team;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    @Select("select count(*) from team where team_name = #{name}")
    Integer selectName(String name);

    @Select("select * from team where team_id=#{id}")
    Team selectByTeamId(Integer id);

    @Update("update team set team_name=#{team.teamName} where team_id = #{id}")
    Integer updateTeam(Integer id,Team team);

    @Delete("delete from team where team_id=#{id}")
    void deleteTeamById(Integer id);

    @Update("update team set virtual_coins=#{coinNew} where team_id=#{teamId}")
    void updateCoinById(Integer teamId, Double coinNew);

    @Select("select team_id from team where team_name=#{name}")
    Integer getByTeamName(String name);

    @Select("select team_name from team where team_id=#{teamId}")
    String getNameByteamId(Integer teamId);

    @Select("select virtual_coins from team where team_id=#{team.teamId}")
    Double getCoin(@Param("team") Team team);
}
