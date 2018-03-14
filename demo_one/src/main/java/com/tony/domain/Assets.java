package com.tony.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jy.medusa.stuff.annotation.Column;
import com.jy.medusa.stuff.annotation.Table;
import com.jy.medusa.stuff.annotation.Id;

import java.util.Date;

/**
 * Created by liam on 2018-03-13 19:57:53
 */
@Table(name = "assets")
@JsonIgnoreProperties(value={"handler"})
public class Assets {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Integer userId;

	/*资产名称*/
	@Column(name = "asset_name")
	private String assetName;

	/*型号*/
	@Column(name = "model")
	private String model;

	/*资产编号*/
	@Column(name = "asset_number")
	private String assetNumber;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	/*新添加的字段*/
	@Column(name = "read_flag")
	private Byte readFlag = 0;


	public void setId(Long id) {
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Integer userId) {
		this.userId=userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setAssetName(String assetName) {
		this.assetName=assetName;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setModel(String model) {
		this.model=model;
	}

	public String getModel() {
		return model;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber=assetNumber;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt=createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt=updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setReadFlag(Byte readFlag) {
		this.readFlag=readFlag;
	}

	public Byte getReadFlag() {
		return readFlag;
	}

	@Override
	public String toString() {
		return "Assets{" +
				"id=" + id +
				", userId=" + userId +
				", assetName='" + assetName + '\'' +
				", model='" + model + '\'' +
				", assetNumber='" + assetNumber + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", readFlag=" + readFlag +
				'}';
	}
}
