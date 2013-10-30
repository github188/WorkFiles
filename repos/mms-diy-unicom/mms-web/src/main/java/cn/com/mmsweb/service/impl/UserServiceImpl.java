package cn.com.mmsweb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.common.MD5;
import cn.com.mmsweb.dao.CommonDAO;
import cn.com.mmsweb.dao.UserDAO;
import cn.com.mmsweb.service.UserService;

import com.unicom.mms.entity.TSignOrder;
import com.unicom.mms.entity.TbUsers;

/**
 * 
* 功能描述:系统用户管理业务逻辑处理类
* @author chenliang 新增日期：2013-1-14
* @since mms-cms-unicom
 */
@Service("userService")
public class UserServiceImpl extends CommonServiceImpl<TbUsers> implements UserService {
	public static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired(required = true)
	public UserDAO userDAO;

	@Override
	public boolean checkPasswd(Long userID, String oldPasswd) {
		TbUsers users=userDAO.findById(userID);
		String pwd=users.getPwd();
	//	String passwd = MD5.toMD5(oldPasswd);
		if(pwd.equals(oldPasswd))
		{
			return true;
		}else{
	     	return false;
		}
		}

	@Override
	public void updatePasswd(Long userId, String newPasswd) {
		
	}

	@Override
	public void initPasswd(Long userID) {
		
	}

	/**
	 * 登陆，根据用户名查询
	 * @throws Exception 
	 */
	@Override
	public TbUsers checkLogin(String account) throws Exception {
		TbUsers tbUsers = null;
		try {
			tbUsers = userDAO.findByField("mdn", account);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return tbUsers;
	}
	/**
	 * 根据用户号码查询
	 * @throws Exception 
	 */
	@Override
	public TbUsers findByMsn(String msn)
	{
		
		String hql=" from TbUsers u where u.mdn=?";
		TbUsers users=(TbUsers)userDAO.findUnique(hql.toString(), msn);
		return users;
	}
	@Override
	public CommonDAO<TbUsers> getBindDao() {
		return userDAO;
	}
	@Override
	public void updatePWD(TbUsers users)
	{
		userDAO.merge(users);
	}

	@Override
	public TSignOrder findProductByMdn(String mdn) {
		String hql = "select s from TbUsers u,TSignOrder s where u.id = s.userId and u.mdn = ?";
		List list = userDAO.findProductByMdn(hql, mdn);
		if(null!=list && list.size()>0){
			TSignOrder signOrder = (TSignOrder) list.get(0);
			return signOrder;
		}
		return new TSignOrder();
	}
	
}