package jeecg.kxcomm.entity.contactm;

import java.math.BigDecimal;
import java.util.Date;

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
 * @Description: 合同报价表关系管理
 * @author zhangdaihao
 * @date 2013-10-25 10:45:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tb_contract_quotations", schema = "")
@SuppressWarnings("serial")
public class TbContractQuotationsEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**quotationsId*/
	private TbQuotationsEntity quotationsId=new TbQuotationsEntity();
	/**contractId*/
	private TbContractEntity contractId = new TbContractEntity();
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=255)
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
	 *@return: java.lang.String  quotationsId
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="QUOTATIONS_ID",nullable=true)
	public TbQuotationsEntity getQuotationsId() {
		return quotationsId;
	}

	public void setQuotationsId(TbQuotationsEntity quotationsId) {
		this.quotationsId = quotationsId;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  contractId
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="CONTRACT_ID",nullable=true)
	public TbContractEntity getContractId() {
		return contractId;
	}

	public void setContractId(TbContractEntity contractId) {
		this.contractId = contractId;
	}
}