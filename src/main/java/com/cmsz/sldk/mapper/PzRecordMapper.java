package com.cmsz.sldk.mapper;

import com.cmsz.sldk.model.PzRecord;

public interface PzRecordMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int insert(PzRecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int insertSelective(PzRecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	PzRecord selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int updateByPrimaryKeySelective(PzRecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pz_record
	 * @mbg.generated  Thu Feb 08 14:37:08 CST 2018
	 */
	int updateByPrimaryKey(PzRecord record);
}