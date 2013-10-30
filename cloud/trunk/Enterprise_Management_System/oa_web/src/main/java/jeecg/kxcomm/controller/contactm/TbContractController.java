package jeecg.kxcomm.controller.contactm;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.swftools.SwfToolsUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.PinyinUtil;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import jeecg.system.pojo.base.TSUser;
import jeecg.system.service.SystemService;



import jeecg.kxcomm.entity.contactm.TbConfigModelsEntity;
import jeecg.kxcomm.entity.contactm.TbContractEntity;
import jeecg.kxcomm.entity.contactm.TbContractQuotationsEntity;
import jeecg.kxcomm.entity.contactm.TbOrderDetailEntity;
import jeecg.kxcomm.entity.contactm.TbProjectEntityEntity;
import jeecg.kxcomm.entity.contactm.TbQuotationsDataEntity;
import jeecg.kxcomm.entity.contactm.TbQuotationsEntity;
import jeecg.kxcomm.page.contactm.TbContractPage;
import jeecg.kxcomm.service.contactm.TbContractQuotationsServiceI;
import jeecg.kxcomm.service.contactm.TbContractServiceI;
import jeecg.kxcomm.service.contactm.TbQuotationsDataServiceI;
import jeecg.kxcomm.service.systemmanager.TbContractDocServiceI;
import jeecg.kxcomm.service.systemmanager.TbContractDocVariableServiceI;
import jeecg.kxcomm.service.systemmanager.TbContractTemplatesDocServiceI;
import jeecg.kxcomm.service.systemmanager.TbContractTemplatesDocVariableServiceI;
import jeecg.kxcomm.service.systemmanager.TbContractTemplatesEntityServiceI;
import jeecg.kxcomm.vo.contactm.ContractDocVariableVo;
import jeecg.kxcomm.vo.contactm.QuotationsDataVo;
import jeecg.kxcomm.vo.contactm.TbContractVo;
import jeecg.kxcomm.vo.contactm.TbQuotationsVo;
import jeecg.kxcomm.entity.contactm.TbOrderEntity;
import jeecg.kxcomm.entity.systemmanager.TbContractDocEntity;
import jeecg.kxcomm.entity.systemmanager.TbContractDocVariableEntity;
import jeecg.kxcomm.entity.systemmanager.TbContractTemplatesDocEntity;
import jeecg.kxcomm.entity.systemmanager.TbContractTemplatesEntityEntity;
/**   
 * @Title: Controller
 * @Description: 销售合同
 * @author zhangdaihao
 * @date 2013-09-27 17:00:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tbContractController")
public class TbContractController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TbContractController.class);

	@Autowired
	private TbContractServiceI tbContractService;
	@Autowired 
	private TbContractTemplatesEntityServiceI tbContractTemplatesEntityService;
	@Autowired
	private TbContractTemplatesDocServiceI tbContractTemplatesDocService;
	@Autowired
	private TbContractTemplatesDocVariableServiceI tbContractTemplatesDocVariableService;
	@Autowired
	private TbQuotationsDataServiceI tbQuotationsDataService;
	@Autowired
	private TbContractQuotationsServiceI tbContractQuotationsService;
	@Autowired
	private TbContractDocServiceI tbContractDocService;
	@Autowired
	private TbContractDocVariableServiceI tbContractDocVariableService;
 	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 销售合同列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tbContract")
	public ModelAndView tbContract(HttpServletRequest request) {
		return new ModelAndView("jeecg/kxcomm/contactm/tbContractList");
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
	public void datagrid(TbContractVo tbContract,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TbContractEntity.class, dataGrid);

		
		
		String contractNo = request.getParameter("contractNo");
		
		HqlQuery hqlQuery = new HqlQuery("TbContractEntity.do?datagrid");
		hqlQuery.setCurPage(dataGrid.getPage());
		hqlQuery.setPageSize(dataGrid.getRows());
		hqlQuery.setDataGrid(dataGrid);
		PageList pagelist = this.tbContractService.getPageList(hqlQuery, true,tbContract,contractNo);

		dataGrid.setPage(pagelist.getCurPageNO());
		dataGrid.setTotal(pagelist.getCount());
		dataGrid.setReaults(pagelist.getResultList());
		TagUtil.datagrid(response, dataGrid);
		
	}

	/**
	 * 删除销售合同
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TbContractEntity tbContract, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
	/*	tbContract = systemService.getEntity(TbContractEntity.class, tbContract.getId());
		message = "删除成功";
		tbContractService.delMain(tbContract);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		*/
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加销售合同
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TbContractPage tbContractPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TbContractEntity tbContract=new TbContractEntity();
		Map<String, Object> attr = new HashMap<String, Object>();
	//报价表IDS
		String ids=request.getParameter("ids");
			attr.put("quoIds", ids);
		try {
	//保存合同
		String contractName=request.getParameter("contractName");  //合同名称
		String contractNo=request.getParameter("contractNo");   //外部合同号
		String contractNumber=request.getParameter("contractNumber"); //内部合同号
		String contactType=request.getParameter("contactType");  //合同类型
		 attr.put("contactType", contactType);
		String ratio=request.getParameter("ratio");            //比例
		String invoiceType=request.getParameter("invoiceType");   //发票类型
		String contractDate=request.getParameter("contractDate");  //签订时间
		String remark=request.getParameter("remark");        //备注
		String projectId=request.getParameter("projectId");      //项目
		String contractTem=request.getParameter("contractTem");  //合同模板
	   attr.put("contractTemplatesId", contractTem);
	   tbContract.setContractName(contractName);
	   tbContract.setContractNo(contractNo);
       tbContract.setContractNumber(contractNumber);
       tbContract.setRatio(ratio);
       tbContract.setInvoiceType(invoiceType);
       try {
    	   if(contractDate!=null && contractDate!="")
    	   {
		tbContract.setContractDate(new SimpleDateFormat("yyyy-MM-dd").parse(contractDate));
    	   }
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       tbContract.setRemark(remark);
       TbProjectEntityEntity projectEntity=new TbProjectEntityEntity();
        projectEntity.setId(projectId);
        tbContract.setProjectId(projectEntity);
		tbContract.setCreateTime(new Date());
		 TSUser u = ResourceUtil.getSessionUserName();
		 tbContract.setCreatorId(u);
		 tbContract.setContactType(contactType);
		 tbContract.setStatus("1");
		if(contactType.equals("1"))
			{
			TbContractTemplatesEntityEntity tbContractTemplatesEntity=new TbContractTemplatesEntityEntity();
			tbContractTemplatesEntity.setId(contractTem);
			tbContract.setContractTemplatesId(tbContractTemplatesEntity);
		}
		 tbContractService.save(tbContract);
		  String id=tbContract.getId();
		 attr.put("contractId", id);
		 HttpSession session =  request.getSession();
	     session.setAttribute("attr", attr);
		//保存合同文件
	//		HqlQuery hqlQuery = new HqlQuery("");
			List<TbContractTemplatesDocEntity> contractTemplatesDocList=this.tbContractTemplatesDocService.getConTempList(contractTem);
			for(TbContractTemplatesDocEntity templatesDoc:contractTemplatesDocList)
	   {
		   TbContractDocEntity tbContractDocEntity=new TbContractDocEntity();
		   tbContractDocEntity.setTemplatesdocId(templatesDoc);
		   tbContractDocEntity.setCreatetime(new Date());
		   tbContractDocEntity.setContractId(tbContract);
		   tbContractDocEntity.setFilename(templatesDoc.getDocname()+".docx");
		   tbContractDocService.save(tbContractDocEntity);
	   }
	   //保存合同文件变量
	   this.tbContractDocService.saveContractDocVaialbe(tbContract);
	   //保存TbContractQuotationsEntity
	   String[] quoId = ids.split(",");
		for (String strId : quoId) {
			TbContractQuotationsEntity tbContractQuotationsEntity=new TbContractQuotationsEntity();
			TbQuotationsEntity quotationsEntity=new TbQuotationsEntity();
			tbContractQuotationsEntity.setContractId(tbContract);
			quotationsEntity.setId(strId);
			tbContractQuotationsEntity.setQuotationsId(quotationsEntity);
			tbContractQuotationsService.save(tbContractQuotationsEntity);
		}
		message="保存成功";
		} catch (Exception e) {
			message="失败"; 
		}
		j.setMsg(message);
			return j;
	}
	
	/**
	 * 销售合同列表 下一步页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tbContractQuoDoc")
	public ModelAndView tbContractQuoDoc(HttpServletRequest request) {
		List<TbConfigModelsEntity> tbConfigModelsEntities=new ArrayList<TbConfigModelsEntity>();
		 HttpSession session =  request.getSession();
		 Map<String, Object> ff =(Map<String, Object>) session.getAttribute("attr");
		 String quoIds=(String) ff.get("quoIds");
		 String contactType=(String) ff.get("contactType");
		 request.setAttribute("contactType", contactType);
		 String[] ids = quoIds.split(",");
		for (String strId : ids) {
		if(StringUtil.isNotEmpty(strId))
		{
			tbConfigModelsEntities=tbContractService.getConfigByQuoList(strId);
		    request.setAttribute("tbConfigModelsEntities", tbConfigModelsEntities);
		}
		}
		return new ModelAndView("jeecg/kxcomm/contactm/tbContractQuoDoc");
	}

	/**
	 * 销售合同列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TbContractEntity tbContract, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tbContract.getId())) {
			tbContract = tbContractService.getEntity(TbContractEntity.class, tbContract.getId());
			req.setAttribute("tbContractPage", tbContract);
		}
		List<TbProjectEntityEntity> projectList=systemService.getList(TbProjectEntityEntity.class);
		req.setAttribute("projectList", projectList);
		List<TbContractTemplatesEntityEntity> contractTemplateList=tbContractTemplatesEntityService.getContractTemplatesList();
		req.setAttribute("contractTemplateList", contractTemplateList);
		return new ModelAndView("jeecg/kxcomm/contactm/tbContract");
	}
	
	/**
	 * easyui AJAX请求数据
	 * 查询可以使用的报价表
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "quotations")
	public void getQuotations(TbQuotationsVo quotationsVo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		HqlQuery hqlQuery = new HqlQuery("quotationsVo.do?datagrid");
		hqlQuery.setCurPage(dataGrid.getPage());
		hqlQuery.setPageSize(dataGrid.getRows());
		hqlQuery.setDataGrid(dataGrid);
		PageList pagelist = this.tbContractService.getQuotationsList(hqlQuery, true,quotationsVo);
		dataGrid.setPage(pagelist.getCurPageNO());
		dataGrid.setTotal(pagelist.getCount());
		dataGrid.setReaults(pagelist.getResultList());
		TagUtil.datagrid(response, dataGrid);
		
	}
	/**
	 * easyui AJAX请求数据
	 * 根据合同模板id查询模板文件
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "getConDocList")
	public void getConDocList(TbContractDocEntity contractDocEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		/* HttpSession session =  request.getSession();
		 Map<String, Object> ff =(Map<String, Object>) session.getAttribute("attr");
		 String contractTemplatesId=(String) ff.get("contractTemplatesId");
				HqlQuery hqlQuery = new HqlQuery("tbContractTemplatesDocEntity.do?quotations");
				hqlQuery.setCurPage(dataGrid.getPage());
				hqlQuery.setPageSize(dataGrid.getRows());
				hqlQuery.setDataGrid(dataGrid);
				PageList pagelist = this.tbContractTemplatesDocService.getConTempDocList(hqlQuery, true, tbContractTemplatesDocEntity, contractTemplatesId);
				dataGrid.setPage(pagelist.getCurPageNO());
				dataGrid.setTotal(pagelist.getCount());
				dataGrid.setReaults(pagelist.getResultList());
				TagUtil.datagrid(response, dataGrid);*/
		CriteriaQuery cq = new CriteriaQuery(TbContractDocEntity.class, dataGrid);
		//查询条件组装器
		HttpSession session =  request.getSession();
		 Map<String, Object> ff =(Map<String, Object>) session.getAttribute("attr");
		String contractId=(String) ff.get("contractId");
		cq.createAlias("contractId", "contractId");	
		cq.eq("contractId.id", contractId);
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, contractDocEntity);
		this.tbContractDocService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * 合同模板文件变量列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tbSContractDocVariable")
	public ModelAndView tbSContractDocVariable(HttpServletRequest request) {
		String docId=request.getParameter("id");
		request.setAttribute("docId", docId);
		List<TbContractDocVariableEntity> tbContractDocVariableList=new ArrayList<TbContractDocVariableEntity>();
		if(StringUtil.isNotEmpty(docId))
		{
			tbContractDocVariableList=tbContractDocVariableService.getContractDocVariableEntity(docId);
		    request.setAttribute("tbContractDocVariableList", tbContractDocVariableList);
		}
		return new ModelAndView("jeecg/kxcomm/contactm/tbSContractDocVariable");
	}

	/**
	 * 保存合同文件变量
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveContractDocVariable")
	@ResponseBody
	public AjaxJson saveContractDocVariable(TbContractDocVariableEntity TbContractDocVariableEntity,HttpServletRequest request,ContractDocVariableVo vo) {
		AjaxJson j = new AjaxJson();
		try {
		List<TbContractDocVariableEntity> tbContractDocVariableList=vo.getTbContractDocVariableList();
		for(TbContractDocVariableEntity contractDocVariable:tbContractDocVariableList){
			tbContractDocVariableService.saveOrUpdate(contractDocVariable);
		}
		message="保存成功";
		} catch (Exception e) {
			message="保存失败";
		}
		 j.setMsg(message);
		 return j;
	}
	
	/**
	 * 保存合同
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveContract")
	@ResponseBody
	public AjaxJson saveContract(HttpServletRequest request,QuotationsDataVo vo, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		 HttpSession session =  request.getSession();
		 Map<String, Object> ff =(Map<String, Object>) session.getAttribute("attr");
		String contractId=(String) ff.get("contractId");
		try {
	message="保存成功";
		 //保存TbQuotationsDataEntity
		List<TbQuotationsDataEntity> quotationsDataList=vo.getQuotationsDataList();
		Double t=0.0;
		for(TbQuotationsDataEntity quotationsDataEntity:quotationsDataList)
		{
			TbQuotationsDataEntity quotationsData = new TbQuotationsDataEntity();
			quotationsData.setProjectName(quotationsDataEntity.getProjectName());
			quotationsData.setName(quotationsDataEntity.getName());
			quotationsData.setQuantity(quotationsDataEntity.getQuantity());
			TbConfigModelsEntity configModelsEntity=new TbConfigModelsEntity();
			configModelsEntity.setId(quotationsDataEntity.getTbConfigModels().getId());
			quotationsData.setTbConfigModels(configModelsEntity);
			TbQuotationsEntity quotationsEntity=new TbQuotationsEntity();
			quotationsEntity.setId(quotationsDataEntity.getTbQuotations().getId());
			quotationsData.setTbQuotations(quotationsEntity);
	       tbQuotationsDataService.save(quotationsData);
	       Integer shuliang=quotationsDataEntity.getQuantity();
	       String price=quotationsDataEntity.getTbConfigModels().getTotalPrice();
	       t+=shuliang*Double.parseDouble(price);
		}
		//保存合同
		String totalPrice=t.toString();
	   String contactType= (String)ff.get("contactType");  //合同类型
	   TbContractEntity contractEntity=systemService.get(TbContractEntity.class, contractId);
	   if(contactType.equals("1"))
	   {
		   contractEntity.setContractTotalPrice(totalPrice); //保存合同的总价
	   }else {
		 String fileName=(String) session.getAttribute("temporaryFileName");  //临时合同上传文件名
		 contractEntity.setFileName(fileName);
	      }
	   tbContractService.saveOrUpdate(contractEntity);
		
		} catch (Exception e) {
			message="保存失败";
		}
		j.setMsg(message);
		 return j;
	}
	/**
	 * 加载明细列表[销售订单]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tbOrderList")
	public ModelAndView tbOrderList(TbContractEntity tbContract, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tbContract.getId();
		//===================================================================================
		//删除-销售订单
	    String hql0 = "from TbOrderEntity where 1 = 1 AND tbContract = ? ";
	    List<TbOrderEntity> tbOrderEntityList = systemService.findHql(hql0,tbContract);
		req.setAttribute("tbOrderList", tbOrderEntityList);
		return new ModelAndView("jeecg/kxcomm/contactm/tbOrderList1");
	}
	
	/**
	 * 明细列表页面跳转
	 * 
	 * @return
	 */
/*	@RequestMapping(params = "contractDetail")
	public ModelAndView contractDetail(HttpServletRequest req,String id) {	    
	    String hql1 = "from TbOrderDetailEntity where 1 = 1 AND tbOrder.tbContract.id = ? ";
		List<TbOrderDetailEntity> tbOrderDetailList = systemService.findHql(hql1,id);
	    List t = new ArrayList();
		for(TbOrderDetailEntity tbOrderDetail:tbOrderDetailList){
    		t.add(tbOrderDetail);
    	}
		req.setAttribute("tbOrderDetailList", t);
		return new ModelAndView("jeecg/kxcomm/contactm/tbContractDetail");
	}*/
	
	/**
	 * 销售订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "contractDetail")
	public ModelAndView orderDetailDetail(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id",id);
		return new ModelAndView("jeecg/kxcomm/contactm/tbContractDetail");
	}
	
	/**
	 * 明细列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "contractDetailList")
	public void contractDetailList(TbOrderEntity tbOrderEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TbOrderDetailEntity tbOrderDetail= new TbOrderDetailEntity();
		CriteriaQuery cq = new CriteriaQuery(TbOrderDetailEntity.class, dataGrid);
		String id = request.getParameter("contractId");
		cq.createAlias("tbOrder", "tbOrder");	
		cq.createAlias("tbOrder.tbContract", "tbContract");
		if(id!=null && !"".equals(id)){
			cq.eq("tbOrder.tbContract.id", id);
			cq.add();
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbOrderDetail);
		this.tbContractService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "getContractByContractNo")
	@ResponseBody
	public TbContractEntity getContractByContractNo(HttpServletRequest request) {
		
		String contractNo = request.getParameter("contractNo");
		String hql = "from TbContractEntity where 1 = 1 AND contractNo = ? ";
		List<TbContractEntity> list = systemService.findHql(hql,contractNo);
		
		if(list.size()==0){
			return null;
		}else{		
			return list.get(0);
		}
	}
	
	
	/**
	 * 上传临时合同文件
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(params = "uploadTemporaryDoc", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadTemporaryDoc( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TbContractEntity tbContractEntity=new TbContractEntity();
		String fileName="";
		String fileNewName="";
		String savePath="";
		String swfName = "";
		UploadFile uploadFile = new UploadFile(request,tbContractEntity);
		uploadFile.setSwfpath("swfpath");
		ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
		 String ctxPath = request.getSession().getServletContext().getRealPath("upload");
         File file = new File(ctxPath);
         if (!file.exists()) {
                 file.mkdir();// 创建文件根目录
         }
		 MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
         Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
         for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                 MultipartFile mf = entity.getValue();// 获取上传文件对象
                 fileName = mf.getOriginalFilename();// 获取文件名
                 String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                 swfName = PinyinUtil.getPinYinHeadChar(oConvertUtils.replaceBlank(FileUtils.getFilePrefix(fileName)));// 取文件名首字母作为SWF文件名
                 fileNewName = System.currentTimeMillis()+mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."), mf.getOriginalFilename().length());// 获取文件名
                 savePath = file.getPath() + "\\" + fileNewName;// 上传后的文件绝对路径
                System.out.println("路径长度："+savePath.length());
                 System.out.println("上传后路径："+savePath);
                 File savefile = new File(savePath);
                 try {
                         FileCopyUtils.copy(mf.getBytes(), savefile);
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
             	File savef = new File(savePath);
             	try {
					FileCopyUtils.copy(mf.getBytes(), savef);
					// 转SWF
					reflectHelper.setMethodValue(uploadFile.getSwfpath(), file.getPath() + "/"+ swfName + ".swf");
					if (uploadFile.getSwfpath() != null) {
						// 转SWF
						//----------------------------------------------------------------
						//update-end--Author:liutao  Date:20130506 for：修改swf文件的存储路径
						//----------------------------------------------------------------
						reflectHelper.setMethodValue(uploadFile.getSwfpath(), file.getPath() + "/" + FileUtils.getFilePrefix(fileNewName) + ".swf");
						//----------------------------------------------------------------
						//update-end--Author:liutao  Date:20130506 for：修改swf文件的存储路径
						//----------------------------------------------------------------
						SwfToolsUtil.convert2SWF(savePath);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
         }
         HttpSession session =  request.getSession();
         session.setAttribute("temporaryFileName", fileNewName);
       System.out.println("savePath@@@"+savePath);
         j.setMsg(message);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
	return j;
	}
	
}