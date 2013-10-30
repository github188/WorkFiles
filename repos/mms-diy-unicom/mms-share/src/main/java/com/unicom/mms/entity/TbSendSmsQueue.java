package com.unicom.mms.entity;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * 功能描述:短信待发送队列实体类
 * 
 * @author chenliang 新增日期：2013-1-31
 * @since mms-cms-unicom
 */
@Entity
@SequenceGenerator(name="SEQ_SEND_SMS_QUEUE_ID",sequenceName="SEQ_SEND_SMS_QUEUE_ID",allocationSize=1)
@Table(name = "tb_send_sms_queue")
public class TbSendSmsQueue implements java.io.Serializable {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_SEND_SMS_QUEUE_ID")
	@Column(name = "id", updatable = false, nullable = false, insertable = false, length = 32)
	private Integer id;
	
	/**
	 * 消息id(发送给网关的消息id)
	 */
	@Column(name = "msgSn", updatable = true, nullable = true, insertable = true, length = 200)
	private String msgSn;
	
	/**
	 * 发送状态(发送给网关的，网关返回的状态,1、成功，2、失败)
	 */
	@Column(name = "msg_status", updatable = true, nullable = true, insertable = true, length = 150)
	private String msgStatus;
	
	/**
	 * 发送报告(网关发送给用户返回的状态)
	 */
	@Column(name = "msg_report", updatable = true, nullable = true, insertable = true, length = 150)
	private String msgReport;
	
	/**
	 * 发送时间
	 */
	@Column(name = "send_time", updatable = true, nullable = false, insertable = true)
	private Date sendTime;
	
	
	/**
	 * 文本(短信)内容
	 */
	@Column(name = "msg_body", updatable = true, nullable = false, insertable = true, length = 150)
	private String msgBody; 
	
	/**
	 * 接收人号码
	 */
	@Column(name = "receive_mobile", updatable = true, nullable = false, insertable = true, length = 150)
	private String receiveMobile;
	
	/***
	 * 创建时间
	 */
	@Column(name = "create_time", updatable = true, nullable = false, insertable = true)
	private Date createTime;
	
	/**
	 * 发送等级(1、优先，2、其次，3、普通)
	 */
	@Column(name = "send_level", updatable = true, nullable = false, insertable = true, length = 32)
	private Integer sendLevel;
	
	
	/**
	 * 渠道 1、web， 2、cms， 3、其它，  默认为3
	 */
	@Column(name = "channel", updatable = true, nullable = false, insertable = true, length = 32)
	private Integer channel;
	
	/**
	 * 发起人 (用户发个用户，发起人即是用户的手机号码，群发，发起人即是sp)
	 */
	@Column(name = "sponsor", updatable = true, nullable = true, insertable = true, length = 150)
	private String sponsor;
	
	/**
	 * 任务id
	 */
	@Column(name = "job_id", updatable = true, nullable = true, insertable = true, length = 32)
	private Integer jobId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMsgSn() {
		return msgSn;
	}

	public void setMsgSn(String msgSn) {
		this.msgSn = msgSn;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getMsgReport() {
		return msgReport;
	}

	public void setMsgReport(String msgReport) {
		this.msgReport = msgReport;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSendLevel() {
		return sendLevel;
	}

	public void setSendLevel(Integer sendLevel) {
		this.sendLevel = sendLevel;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Override
	public String toString() {
		return "TbSendSmsQueue [id=" + id + ", msgSn=" + msgSn + ", msgStatus="
				+ msgStatus + ", msgReport=" + msgReport + ", sendTime="
				+ sendTime + ", msgBody=" + msgBody + ", receiveMobile="
				+ receiveMobile + ", createTime=" + createTime + ", sendLevel="
				+ sendLevel + ", channel=" + channel + ", sponsor=" + sponsor
				+ ", jobId=" + jobId + "]";
	}

	public TbSendSmsQueue(Integer id, String msgSn, String msgStatus,
			String msgReport, Date sendTime, String msgBody,
			String receiveMobile, Date createTime, Integer sendLevel,
			Integer channel, String sponsor, Integer jobId) {
		super();
		this.id = id;
		this.msgSn = msgSn;
		this.msgStatus = msgStatus;
		this.msgReport = msgReport;
		this.sendTime = sendTime;
		this.msgBody = msgBody;
		this.receiveMobile = receiveMobile;
		this.createTime = createTime;
		this.sendLevel = sendLevel;
		this.channel = channel;
		this.sponsor = sponsor;
		this.jobId = jobId;
	}

	public TbSendSmsQueue() {
		super();
	}

}