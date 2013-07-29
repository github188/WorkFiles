package jeecg.kxcomm.controller.hrm;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import jeecg.system.pojo.base.TSDepart;
import jeecg.system.pojo.base.TSUser;
import jeecg.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import jeecg.kxcomm.entity.hrm.TbCheckingInEntity;
import jeecg.kxcomm.entity.hrm.TbCheckingInstanceEntity;
import jeecg.kxcomm.entity.hrm.TbEmployeeEntity;
import jeecg.kxcomm.service.hrm.TbCheckingInstanceServiceI;
import jeecg.kxcomm.service.hrm.TbEmployeeServiceI;

/**   
 * @Title: Controller
 * @Description: 考勤统计表
 * @author zhangdaihao
 * @date 2013-07-23 14:59:53
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tbCheckingInstanceController")
public class TbCheckingInstanceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TbCheckingInstanceController.class);

	@Autowired
	private TbCheckingInstanceServiceI tbCheckingInstanceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TbEmployeeServiceI tbEmployeeService;
	private String message;
	private String ids;		//事项类型：迟到，早退，旷工
	private String empids;	//员工ID集合

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getEmpids() {
		return this.empids;
	}

	public void setEmpids(String empids) {
		this.empids = empids;
	}

	/**
	 * 考勤统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tbCheckingInstance")
	public ModelAndView tbCheckingInstance(HttpServletRequest request) {
		return new ModelAndView("jeecg/kxcomm/hrm/tbCheckingInstanceList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TbCheckingInstanceEntity tbCheckingInstance,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//查询条件组装器
//		HqlQuery hqlQuery = new HqlQuery("tbCheckingInstanceController.do?datagrid");
//		hqlQuery.setCurPage(dataGrid.getPage());
//		hqlQuery.setPageSize(dataGrid.getRows());
//		hqlQuery.setDataGrid(dataGrid);
//		TSUser user= ResourceUtil.getSessionUserName();
//		user = systemService.getEntity(TSUser.class, user.getId());
//		
//		PageList pagelist = this.tbCheckingInstanceService.getPageList(hqlQuery, true,tbCheckingInstance,user.getId(),user);
//		dataGrid.setPage(pagelist.getCurPageNO());
//		dataGrid.setTotal(pagelist.getCount());
//		dataGrid.setReaults(pagelist.getResultList());
//		TagUtil.datagrid(response, dataGrid);
		CriteriaQuery cq = new CriteriaQuery(TbCheckingInstanceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbCheckingInstance);
		this.tbCheckingInstanceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除考勤统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TbCheckingInstanceEntity tbCheckingInstance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tbCheckingInstance = systemService.getEntity(TbCheckingInstanceEntity.class, tbCheckingInstance.getId());
		message = "删除成功";
		tbCheckingInstanceService.delete(tbCheckingInstance);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加考勤统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TbCheckingInstanceEntity tbCheckingInstance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		this.ids = request.getParameter("ids");
		String[] primarSouce = this.ids.split(",");
		if (StringUtil.isNotEmpty(tbCheckingInstance.getId())) {
			message = "更新成功";
			TbCheckingInstanceEntity t = tbCheckingInstanceService.get(TbCheckingInstanceEntity.class, tbCheckingInstance.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tbCheckingInstance, t);
				t.setEmpDue(Integer.parseInt(primarSouce[0]));
				t.setEmpActual(Integer.parseInt(primarSouce[1]));
				t.setNeglectWork(Integer.parseInt(primarSouce[2]));
				t.setEmpLate(Integer.parseInt(primarSouce[3]));
				t.setLeaveEarly(Integer.parseInt(primarSouce[4]));
				t.setOvertime(Integer.parseInt(primarSouce[5]));
				t.setEmpLeave(Integer.parseInt(primarSouce[6]));
				t.setEmpAway(Integer.parseInt(primarSouce[7]));
				t.setWeekendOvertime(Integer.parseInt(primarSouce[8]));
				t.setHappenday(tbCheckingInstance.getHappenday());
				System.out.println(t.toString());
				tbCheckingInstanceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.empids = tbCheckingInstance.getEmpId().getId();
			String[] emploSouce = this.empids.split(",");
			message = "添加成功";
			TbCheckingInstanceEntity entity = null;
			for(int k = 0; k < emploSouce.length; k++) {
				entity = new TbCheckingInstanceEntity();
				TbEmployeeEntity employeeEntity = new TbEmployeeEntity();
				employeeEntity.setId(emploSouce[k]);
				entity.setEmpId(employeeEntity);
				entity.setEmpDue(Integer.parseInt(primarSouce[0]));
				entity.setEmpActual(Integer.parseInt(primarSouce[1]));
				entity.setNeglectWork(Integer.parseInt(primarSouce[2]));
				entity.setEmpLate(Integer.parseInt(primarSouce[3]));
				entity.setLeaveEarly(Integer.parseInt(primarSouce[4]));
				entity.setOvertime(Integer.parseInt(primarSouce[5]));
				entity.setEmpLeave(Integer.parseInt(primarSouce[6]));
				entity.setEmpAway(Integer.parseInt(primarSouce[7]));
				entity.setWeekendOvertime(Integer.parseInt(primarSouce[8]));
				entity.setHappenday(tbCheckingInstance.getHappenday());
				System.out.println(entity.toString());
				tbCheckingInstanceService.save(entity);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * 考勤统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TbCheckingInstanceEntity tbCheckingInstance, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tbCheckingInstance.getId())) {
			tbCheckingInstance = tbCheckingInstanceService.getEntity(TbCheckingInstanceEntity.class, tbCheckingInstance.getId());
			req.setAttribute("tbCheckingInstancePage", tbCheckingInstance);
		}
		return new ModelAndView("jeecg/kxcomm/hrm/tbCheckingInstance");
	}
	
	/**
	 * 所有员工数据查询
	 * 
	 * @return
	 */
	@RequestMapping(params = "empdatagrid")
	public void datagrid(TbEmployeeEntity tbEmployee,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TbEmployeeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbEmployee);
		this.tbEmployeeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 员工考勤详情查看
	 * 
	 * @return
	 */
	@RequestMapping(params = "queryInfo")
	public void queryCheckInfoByTime(TbCheckingInstanceEntity tbCheckingInstance, HttpServletRequest req) {
		
	}
}