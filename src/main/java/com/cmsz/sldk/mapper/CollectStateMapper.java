package com.cmsz.sldk.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cmsz.sldk.model.CollectState;

public interface CollectStateMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int deleteByPrimaryKey(String openid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int insert(CollectState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int insertSelective(CollectState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	CollectState selectByPrimaryKey(String openid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int updateByPrimaryKeySelective(CollectState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table collect_state
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int updateByPrimaryKey(CollectState record);

	@Select("SELECT * FROM collect_state WHERE openid=#{openid};")
    CollectState selectByOpenid(@Param("openid") String openId);
    
    @Update("UPDATE collect_state SET current_num=#{currentNum} WHERE openid=#{openid};")
    boolean updateCollectState(@Param("openid") String openid, @Param("currentNum") Integer currentNum);

}