package jp.co.ixui.tamura.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.ixui.tamura.domain.EmpMst;

/**
 * @author tamura
 * EmpMstへのCRUD操作を提供する
 */
@Mapper
public interface EmpMstMapper {
	/**
	 * @param empNo
	 * @return EmpMst
	 */
	EmpMst selectUser(int empNo);
	
	/**
	 * @return List<EmpMst>
	 */
	List<EmpMst> selectAll();
}
