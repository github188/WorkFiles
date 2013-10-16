<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>固定资产表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tbPermanentAssetsController.do?save">
			<input id="id" name="id" type="hidden" value="${tbPermanentAssetsPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							资产编号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="number" name="number" 
							   value="${tbPermanentAssetsPage.number}" datatype="s1-50">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							资产名称:
						</label>
					</td>
					<td class="value">
							<input class="inputxt" id="permName" name="permName" 
							   value="${tbPermanentAssetsPage.permName}" datatype="s1-100">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							资产类型:
						</label>
					</td>
					<td class="value">
							<select id="accType"  name="accType"  datatype="*">
					        <option value="1" >
					        计算机设备
					        </option>
					            <option value="2" >
					      办公用品
					        </option>
				      	</select>
						<span class="Validform_checktip">请选择员工</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							状态
						</label>
					</td>
					<td class="value">
					<select id="stauts"  name="stauts"  datatype="*">
				      	 <c:forEach items="${statusList}" var="stauts">
					        <option value="${stauts.id }" <%--  <c:if test="${depart.id==tbOrdersPage.channelId.id}">selected="selected"</c:if> --%>>
					         ${stauts.name}
					        </option>
					       </c:forEach>
				      	</select>
						<span class="Validform_checktip">请选择状态</span> 
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							购入时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="buyTime" name="buyTime" ignore="ignore"
							   value="<fmt:formatDate value='${tbPermanentAssetsPage.buyTime}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							资产价值:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="price" name="price" ignore="ignore"
							   value="${tbPermanentAssetsPage.price}" >
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属机构:
						</label>
					</td>
					<td class="value">
							<select id="orgen.id"  name="orgen.id"  datatype="*">
					       <c:forEach items="${orgenList}" var="orgen">
					        <option value="${orgen.id }"  <%-- <c:if test="${depart.id==tbOrdersPage.channelId.id}">selected="selected"</c:if> --%>>
					         ${orgen.permName}
					        </option>
					       </c:forEach>
				      	</select>
						<span class="Validform_checktip">请选择机构</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							使用人:
						</label>
					</td>
					<td class="value">
							<select id="empId.id"  name="empId.id"  datatype="*">
					       <c:forEach items="${employeeList}" var="employee">
					        <option value="${employee.id }"  <%-- <c:if test="${depart.id==tbOrdersPage.channelId.id}">selected="selected"</c:if> --%>>
					         ${employee.empName}
					        </option>
					       </c:forEach>
				      	</select>
						<%-- <input class="inputxt" id="channel" name="channel" 
							   value="${tbOrdersPage.channel}" datatype="*"> --%>
						<span class="Validform_checktip">请选择人员</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" datatype="s0-100"
							   value="${tbPermanentAssetsPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>