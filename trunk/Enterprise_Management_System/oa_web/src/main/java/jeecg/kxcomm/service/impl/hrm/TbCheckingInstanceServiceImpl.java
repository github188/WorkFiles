package jeecg.kxcomm.service.impl.hrm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.kxcomm.entity.hrm.TbCheckingInstanceEntity;
import jeecg.kxcomm.service.hrm.TbCheckingInstanceServiceI;
import jeecg.system.pojo.base.TSUser;

import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tbCheckingInstanceService")
@Transactional
public class TbCheckingInstanceServiceImpl extends CommonServiceImpl implements TbCheckingInstanceServiceI {

	/**
	 * 根据查询条件查询考勤情况.
	 *
	 * @param hqlQuery
	 * @param b
	 * @param tbCheckingInstance
	 * @param id
	 * @param user
	 * @return
	 */
	@Override
	public PageList getPageList(HqlQuery hqlQuery, boolean b,TbCheckingInstanceEntity tbCheckingInstance, String id, TSUser user) {
		
		return null;
	}
}