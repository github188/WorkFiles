<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="contractDetailList" actionUrl="tbContractController.do?contractDetailList&contractId=${id}" idField="id" fit="true">
  		<t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
  		<t:dgCol title="康讯订单号" field="tbOrder_kxOrderNo" width="70"></t:dgCol>
  		<t:dgCol title="项目名称" field="tbOrder_projectName" width="70" hidden="false"></t:dgCol>
  		<t:dgCol title="客户名称" field="tbOrder_client" width="70"></t:dgCol>
  		<t:dgCol title="最终客户" field="tbOrder_finalClient" width="70"></t:dgCol>
   		<t:dgCol title="产品名称" field="name" width="70"></t:dgCol>
   		<t:dgCol title="产品型号" field="type" width="100"></t:dgCol>
   		<t:dgCol title="产品单价" field="price" width="100"></t:dgCol>
   		<t:dgCol title="产品数量" field="number" width="100"></t:dgCol>
   		<t:dgCol title="销售金额" field="totalprice" width="100"></t:dgCol>
   		<t:dgCol title="下单时间" field="tbOrder_createTime" width="70" formatter="yyyy-MM-dd"></t:dgCol>
   		<t:dgCol title="状态" field="status" width="50"></t:dgCol>
   		<t:dgCol title="付款方式" field="tbOrder_payment" width="70"></t:dgCol>
   		<t:dgCol title="负责人" field="tbOrder_principal" width="70"></t:dgCol>
  	</t:datagrid>
  </div>
 </div>
 