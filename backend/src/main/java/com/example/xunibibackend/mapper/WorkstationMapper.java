package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Workstation;
import com.example.xunibibackend.response.MyResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WorkstationMapper extends BaseMapper<Workstation> {
    @Select("select * from workstation where type = #{type2}")
    Workstation selectByType(String type2);

    @Update("update workstation set is_available = #{i} ,rented_team_id = #{teamId} where type = #{type}")
    void updateRental(Integer i, Integer teamId,String type);

    @Select("select * from workstation")
    List<Workstation> selectList();
}
