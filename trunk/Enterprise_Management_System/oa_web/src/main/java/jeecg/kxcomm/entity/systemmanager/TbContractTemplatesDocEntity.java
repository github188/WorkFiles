package jeecg.kxcomm.entity.systemmanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 合同模板文件
 * @author zhangdaihao
 * @date 2013-10-15 11:10:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tb_contract_templates_doc", schema = "")
@SuppressWarnings("serial")
public class TbContractTemplatesDocEntity implements java.io.Serializable {

	/**id*/
	private java.lang.String id;
	/**文件名称*/
	private java.lang.String docname;
	/**路径*/
	private java.lang.String path;
	/**创建时间*/
	private java.util.Date createtime;
	/**是否设置变量(0：无变量，1：有变量)*/
	private java.lang.Integer bvariable;
	/**合同文件类型*/
	private TbContractDocTypeEntity docType=new TbContractDocTypeEntity();
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件名称
	 */
	@Column(name ="DOCNAME",nullable=true,length=50)
	public java.lang.String getDocname(){
		return this.docname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件名称
	 */
	public void setDocname(java.lang.String docname){
		this.docname = docname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  路径
	 */
	@Column(name ="PATH",nullable=true,length=1024)
	public java.lang.String getPath(){
		return this.path;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  路径
	 */
	public void setPath(java.lang.String path){
		this.path = path;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATETIME",nullable=true)
	public java.util.Date getCreatetime(){
		return this.createtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreatetime(java.util.Date createtime){
		this.createtime = createtime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否设置变量(0：无变量，1：有变量)
	 */
	@Column(name ="BVARIABLE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getBvariable(){
		return this.bvariable;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否设置变量(0：无变量，1：有变量)
	 */
	public void setBvariable(java.lang.Integer bvariable){
		this.bvariable = bvariable;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String docType
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="docType")
	public TbContractDocTypeEntity getDocType() {
		return docType;
	}

	public void setDocType(TbContractDocTypeEntity docType) {
		this.docType = docType;
	}
}
