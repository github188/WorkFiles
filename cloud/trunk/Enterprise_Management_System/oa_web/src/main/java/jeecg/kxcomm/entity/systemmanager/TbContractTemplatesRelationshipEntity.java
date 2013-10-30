package jeecg.kxcomm.entity.systemmanager;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
/**   
 * @Title: Entity
 * @Description: 合同模板与文件关系
 * @author zhangdaihao
 * @date 2013-10-14 16:31:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tb_contract_templates_relationship", schema = "")
@SuppressWarnings("serial")
public class TbContractTemplatesRelationshipEntity implements java.io.Serializable {
	/**templatesDocId*/
	private java.lang.String id;
	/**templatesdocId*/
	private TbContractTemplatesDocEntity templatesdocId = new TbContractTemplatesDocEntity();
	/**templatesId*/
	private TbContractTemplatesEntityEntity templatesId = new TbContractTemplatesEntityEntity();
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  templatesDocId
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=10,scale=0)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  templatesDocId
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  templatesdocId
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEMPLATESDOC_ID")
	public TbContractTemplatesDocEntity getTemplatesdocId(){
		return this.templatesdocId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  templatesdocId
	 */
	public void setTemplatesdocId(TbContractTemplatesDocEntity templatesdocId){
		this.templatesdocId = templatesdocId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  templatesId
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEMPLATES_ID")
	public TbContractTemplatesEntityEntity getTemplatesId(){
		return this.templatesId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  templatesId
	 */
	public void setTemplatesId(TbContractTemplatesEntityEntity templatesId){
		this.templatesId = templatesId;
	}
}