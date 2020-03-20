package com.questionManagement;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.questionManagement.entity.Subject;
import com.questionManagement.repository.ISubjectRepository;

/**
 * 
 * @author CJN
 * @date 2019年3月10日
 * Title: SubjectRepositoryTest 
 * Description: 测试 SubjectRepository 类方法
 */
public class SubjectRepositoryTest extends BaseTest {

	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Test
	public void testFindByName() {
		List<Subject> subjects = subjectRepository.findByNameLike("学", 1, 5);
		System.out.println(subjects.size());
	}
	
}
