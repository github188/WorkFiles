package jeecg.kxcomm.service.impl.hrm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.kxcomm.service.hrm.TbPermanentAssetsServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tbPermanentAssetsService")
@Transactional
public class TbPermanentAssetsServiceImpl extends CommonServiceImpl implements TbPermanentAssetsServiceI {
	
}