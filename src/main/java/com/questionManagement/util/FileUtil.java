package com.questionManagement.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.constant.fieldEnum.QuestionTypeEnum;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.User;
import com.questionManagement.service.IQuestionTypeService;
import com.questionManagement.service.IRoleService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 
 * @author CJN
 * @date 2019年4月18日
 * Title: FileUtil 
 * Description: 操作文件工具类
 */
@Component
@DependsOn("springContextHolder")
public class FileUtil {

	private static IQuestionTypeService questionTypeService = SpringContextHolder.getBean(IQuestionTypeService.class);
	
	private static IRoleService roleService = SpringContextHolder.getBean(IRoleService.class);
	
	@javax.annotation.Resource
	private ResourceLoader resourceLoader;
	
	public static FileUtil fileUtil;
	
	@PostConstruct
	public void init() {
		fileUtil = this;
		fileUtil.resourceLoader = this.resourceLoader;
	}
	
    /**
     * 将 content 写到file中
     * @param file
     * @param content
     * @throws Exception 
     */
    public static void writeFile(File file, byte[] content) throws Exception {
    	FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        fos.close();
    }
    
    /**
     * 获取文件名的后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if(fileName.indexOf(".") == -1) {
            return "";
        }
        int dotIndex = fileName.indexOf(".");
        String result = fileName.substring(dotIndex, fileName.length());
        return result;
    }
    
    /**
     * 
     * Title: downloadFile
     * Description: 下载对应文件
     * @param fileName
     * @param res
     * void
     */
    public static void downloadFile(String fileName, HttpServletResponse res) {
    	Resource resource = fileUtil.resourceLoader.getResource("classpath:/excelTemplates/" + fileName);
		res.setCharacterEncoding("utf-8");
		res.setContentType("multipart/form-data");
		// 处理弹出框名字的编码问题
		try {
			res.setHeader("Content-Disposition", "attachment;fileName=" + 
								new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			InputStream is = resource.getInputStream();
			OutputStream os = res.getOutputStream();
			
			byte[] b = new byte[1024];
			int length = 0;
			while((length = is.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * Title: getQuestionsFromFile
     * Description: 读取用户上传Excel 组装试题List
     * @param fileDir
     * @param creatorId
     * @param subjectId
     * @return
     * List<Question>
     */
    public static List<Question> getQuestionsFromFile(String fileDir, Integer creatorId, Integer subjectId){
    	File questionFile = new File(fileDir);
		// 创建工作蒲
		Workbook workbook;
		List<Question> questions = new ArrayList<Question>();
		
		try {
			workbook = Workbook.getWorkbook(questionFile);
		
			// 获取Excel 的第一个表
			Sheet sheet = workbook.getSheet(0);
			// 获取行数
			int rows = sheet.getRows();
			
			String typeName = sheet.getCell(0, 0).getContents();
			Integer typeId = questionTypeService.getIdByName(typeName);
			Date date = new Date(new java.util.Date().getTime());
			
			if(typeName != null) {
				// 是选择题
				if(QuestionTypeEnum.SINGLE.getValue().equals(typeName) 
										|| QuestionTypeEnum.MULTIPLE.getValue().equals(typeName)) {
					for(int i = 2; i < rows; i ++) {
						try {
							// 组装试题
							Question q = new Question();
							q.setCreatorId(creatorId);
							q.setSubjectId(subjectId);
							q.setCreateTime(date);
							q.setUpdateTime(date);
							q.setStatus(BaseStatusEnum.NORMAL.getValue());
							q.setContent(sheet.getCell(0, i).getContents());
							q.setOptionA(sheet.getCell(1, i).getContents());
							q.setOptionB(sheet.getCell(2, i).getContents());
							q.setOptionC(sheet.getCell(3, i).getContents());
							q.setOptionD(sheet.getCell(4, i).getContents());
							q.setAnswer(sheet.getCell(5, i).getContents());
							q.setScore(Integer.parseInt(sheet.getCell(6, i).getContents()));
							q.setDifficulty(Integer.parseInt(sheet.getCell(7, i).getContents()));
							q.setChapter(Integer.parseInt(sheet.getCell(8, i).getContents()));
							q.setText(sheet.getCell(9, i).getContents());
							q.setTypeId(typeId);
							questions.add(q);
						} catch (Exception e) {
							break;
						}
					}
					
				}else {
					// 非选择题
					for(int i = 2; i < rows; i++) {
						try {
							// 组成试题
							Question q = new Question();
							q.setCreatorId(creatorId);
							q.setSubjectId(subjectId);
							q.setCreateTime(date);
							q.setUpdateTime(date);
							q.setStatus(BaseStatusEnum.NORMAL.getValue());
							q.setContent(sheet.getCell(0, i).getContents());
							q.setAnswer(sheet.getCell(1, i).getContents());
							q.setScore(Integer.parseInt(sheet.getCell(2, i).getContents()));
							q.setDifficulty(Integer.parseInt(sheet.getCell(3, i).getContents()));
							q.setChapter(Integer.parseInt(sheet.getCell(4, i).getContents()));
							q.setText(sheet.getCell(5, i).getContents());
							q.setTypeId(typeId);
							questions.add(q);
						} catch (Exception e) {
							break;
						}
					}
				}
			}
			
			// 关闭工作蒲
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return questions;
    }
    
    /**
     * 
     * Title: getUsersFromFile
     * Description: 根据上传的用户信息组装User List
     * @param fileDir
     * @return
     * List<User>
     */
    public static List<User> getUsersFromFile(String fileDir){
    	List<User> users = new ArrayList<User>();
    	File userFile = new File(fileDir);
    	// 创建工作簿
    	Workbook workbook = null;
    	
    	try {
    		// 获取工作簿
			workbook = Workbook.getWorkbook(userFile);
			// 获取工作簿中的第一个表
			Sheet sheet = workbook.getSheet(0);
			Date date = new Date(new java.util.Date().getTime());
			int rows = sheet.getRows();
			
			// 组装User 实体
			for(int i = 2; i < rows; i++) {
				User user = new User();
				
				try {
					user.setAccount(sheet.getCell(0, i).getContents());
					user.setName(sheet.getCell(1, i).getContents());
					user.setPassword("123456");
					Integer roleId = roleService.getIdByName(sheet.getCell(2, i).getContents());
					user.setRoleId(roleId);
					user.setAge(Integer.parseInt(sheet.getCell(3, i).getContents()));
					user.setSex(sheet.getCell(4, i).getContents());
					user.setPhone(sheet.getCell(5, i).getContents());
					user.setText(sheet.getCell(6, i).getContents());
					user.setCreateTime(date);
					user.setUpdateTime(date);
					user.setStatus(BaseStatusEnum.NORMAL.getValue());
					
					users.add(user);
				} catch (Exception e) {
					break;
				}
			}
			
			// 关闭工作蒲
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return users;
    }
    
}
