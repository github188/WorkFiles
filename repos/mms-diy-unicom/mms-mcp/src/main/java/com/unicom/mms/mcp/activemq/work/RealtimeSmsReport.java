package com.unicom.mms.mcp.activemq.work;

import oracle.net.aso.r;

import org.apache.log4j.Logger;

import com.unicom.mms.constants.SharePublicContants;
import com.unicom.mms.gateway.MsgReport;
import com.unicom.mms.mcp.activemq.InitSpringBean;
import com.unicom.mms.mcp.service.SendSmsRecsService;

/**
 * 
 * 实时发送短信的报告
 * 
 * @author zhangjh 新增日期：2013-9-27
 * @since mms-mcp
 */
public class RealtimeSmsReport implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(RealtimeSmsReport.class);
	
	private MsgReport report;
	
	public MsgReport getReport() {
		return report;
	}
	public void setReport(MsgReport report) {
		this.report = report;
	}
	public RealtimeSmsReport(MsgReport report ){
		this.report = report;
	}
	
	@Override
	public void run() {
		LOGGER.info("Save SendSmsRecs begin.");
		SendSmsRecsService sendsmsRecsService = InitSpringBean.sendSmsRecsService;
		try {
			if(SharePublicContants.MSGREPORT_USER == report.getReportType()){
				LOGGER.info("用户收到消息报告.");
				sendsmsRecsService.updateSmsStates(report);
			}else if(SharePublicContants.MSGREPORT_GATEWAY == report.getReportType()){
				LOGGER.info("网关收到消息报告.");
				sendsmsRecsService.saveWork(report);
			}else {
				LOGGER.info("没有收到消息报告.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("实施收到短信息报告异常.",e);
		}
	}

}