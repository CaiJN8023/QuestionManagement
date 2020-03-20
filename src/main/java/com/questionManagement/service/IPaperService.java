package com.questionManagement.service;

import java.util.List;
import java.util.Map;

import com.questionManagement.entity.Paper;
import com.questionManagement.entity.dto.PaperDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IPaperService 
 * Description: Paper实体类对应业务接口
 */
public interface IPaperService {

	Paper getById(Integer id);
	
	Paper save(Paper paper);

	PaperDTO getDTOById(Integer id);
	
	List<Paper> listAll();
	
	List<Paper> getListByCreatorId(Integer creatorId);
	
	List<PaperDTO> getDTOListByList(List<Paper> papers);
	
	void getKindsQuestionsById(Integer id, Map<Object, Object> map);
	
	boolean isTestedPaper(Integer answererId, Integer paperId, String unMark, String marked);
	
	boolean deleteById(Integer id);
	
	List<Paper> listByInviteCode(String inviteCode);
}
