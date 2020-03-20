package com.questionManagement.util.GA;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.questionManagement.constant.CreatePaper;
import com.questionManagement.entity.Question;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.util.SpringContextHolder;

/**
 * 
 * @author CJN
 * @date 2019年3月24日
 * Title: GA 
 * Description: 遗传算法组卷
 */
@Component
@DependsOn("springContextHolder")
public class GA {
    
	private static IQuestionService questionService = SpringContextHolder.getBean(IQuestionService.class);
	
	/**
	 * 
	 * Title: geneticAlgorithm
	 * Description: 遗传算法
	 * @param rule
	 * @param diff
	 * @param req
	 * @return
	 * Paper
	 */
	public static Paper geneticAlgorithm(Rule rule, double diff, HttpServletRequest req) {
		
		Paper resultPaper = null;
		// 迭代计数器
		int count = 0;
		rule.setDifficulty(diff);
		
		int min = rule.getMinChapter();
		int max = rule.getMaxChapter();
		
		rule.setMinChapter(min < max ? min : max);
		rule.setMaxChapter(max > min ? max : min);
		
		// 可自己初始化组卷规则rule
		if (rule != null) {
			// 初始化种群
			Population population = new Population(CreatePaper.INIT_SIZE, CreatePaper.INIT_FLAG, rule, req);
			long startTime = System.currentTimeMillis();
			System.out.println("初次适应度  " + population.getFitness().getAdaptationDegree());
			// 种群不符合条件则进化
			while (count < CreatePaper.Run_COUNT
					&& population.getFitness().getAdaptationDegree() < CreatePaper.EXPAND) {
				population = GA.evolvePopulation(population, rule);
				count++;
				System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree());
			}
			long endTime = System.currentTimeMillis();
			// 组卷完成
			resultPaper = population.getFitness();
			System.out.println(resultPaper.getQuestionList().size() + "条: " + resultPaper.getQuestionList());
			System.out.println("进化次数： " + count);
			System.out.println("最终适应度为：" + resultPaper.getAdaptationDegree());
			System.out.println("所花时间为：" + (endTime - startTime) / 1000 + "秒");
		}
		
		return resultPaper;
	}
    
    /**
     * 
     * Title: evolvePopulation
     * Description: 种群进化
     * @param pop
     * @param rule
     * @return
     * Population
     */
    public static Population evolvePopulation(Population pop, Rule rule) {
        Population newPopulation = new Population(pop.getLength());
        int elitismOffset;
        // 精英主义
        if (CreatePaper.ELITISM) {
            elitismOffset = 1;
            // 保留上一代最优秀个体
            Paper fitness = pop.getFitness();
            fitness.setId(0);
            newPopulation.setPaper(0, fitness);
        }
        // 种群交叉操作，从当前的种群pop来创建下一代种群newPopulation
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            // 较优选择parent
            Paper parent1 = select(pop);
            Paper parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                parent2 = select(pop);
            }
            // 交叉
            Paper child = crossover(parent1, parent2, rule);
            child.setId(i);
            newPopulation.setPaper(i, child);
        }
        // 种群变异操作
        Paper tmpPaper;
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            tmpPaper = newPopulation.getPaper(i);
            mutate(tmpPaper);
            // 计算章节覆盖率与适应度
            tmpPaper.setChapterCoverage(rule);
            tmpPaper.setAdaptationDegree(rule, CreatePaper.CHAPTER_WEIGHT, CreatePaper.DIFFCULTY_WEIGHT);
        }
        return newPopulation;
    }

    /**
     * 
     * Title: crossover
     * Description: 交叉算子：将两个由选择算子选出的父母个体，交叉产生子个体
     * @param parent1
     * @param parent2
     * @param rule
     * @return
     * Paper
     */
    public static Paper crossover(Paper parent1, Paper parent2, Rule rule) {
        Paper child = new Paper(parent1.getQuestionSize());
        int s1 = (int) (Math.random() * parent1.getQuestionSize());
        int s2 = (int) (Math.random() * parent1.getQuestionSize());

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = s1 < s2 ? s1 : s2;
        int endPos = s1 > s2 ? s1 : s2;
        for (int i = startPos; i < endPos; i++) {
            child.saveQuestion(i, parent1.getQuestion(i));
        }

        // 继承parent2中未被child继承的question
        // 防止出现重复的元素
        for(int i = 0; i < startPos; i++) {
        	if(!child.containsQuestion(parent2.getQuestion(i))) {
        		child.saveQuestion(i, parent2.getQuestion(i));
        	}else{
        		Question q = parent2.getQuestion(i);
        		int typeId = q.getTypeId();
        		int typeScore = q.getScore();
        		int minChapter = rule.getMinChapter();
        		int maxChapter = rule.getMaxChapter();
        		// 从题库中取出一系列满足相同类型，分值，所属章节的question
        		List<Question> questions = questionService.getPaperQuestionList(typeId, typeScore, minChapter, maxChapter);
        		int size = questions.size();
        		for(int j = 0; j < size; j++) {
        			// 判断是否已经被包含
        			Question ques = questions.get((int)Math.random() * size);
        			if(!child.containsQuestion(ques)) {
        				// 不被包含则加入
        				child.saveQuestion(i, ques);
        				break;
        			}else {
        				if(j == size - 1) {
        					// 否则到最后随机加入一个（小概率重复）
        					child.saveQuestion(i, questions.get((int)Math.random() * size));
        				}
        			}
        		}
        	}
        }

        for (int i = endPos; i < parent2.getQuestionSize(); i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.saveQuestion(i, parent2.getQuestion(i));
            } else {
            	Question q = parent2.getQuestion(i);
        		int typeId = q.getTypeId();
        		int typeScore = q.getScore();
        		int minChapter = rule.getMinChapter();
        		int maxChapter = rule.getMaxChapter();
        		List<Question> questions = questionService.getPaperQuestionList(typeId, typeScore, minChapter, maxChapter);
        		int size = questions.size();
        		for(int j = 0; j < size; j++) {
        			// 判断是否已经被包含
        			Question ques = questions.get((int)Math.random() * size);
        			if(!child.containsQuestion(ques)) {
        				// 不被包含则加入
        				child.saveQuestion(i, ques);
        				break;
        			}else {
        				if(j == size - 1) {
        					// 否则到最后随机加入一个（小概率重复）
        					child.saveQuestion(i, questions.get((int)Math.random() * size));
        				}
        			}
        		}
            }
        }
        return child;
    }

    /**
     * 
     * Title: mutate
     * Description: 突变算子，满足突变率的个体会突变
     * @param paper
     * void
     */
    public static void mutate(Paper paper) {
        Question tmpQuestion;
        List<Question> questions;
        for (int i = 0; i < paper.getQuestionSize(); i++) {
            if (Math.random() < CreatePaper.MUTATION_RATE) {
                // 进行突变，第i道
                tmpQuestion = paper.getQuestion(i);
                // 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
                questions = questionService.getByTypeIdAndScore(tmpQuestion.getTypeId(), tmpQuestion.getScore());
                if (questions.size() > 0) {
                    // 随机获取一道
                    int size = questions.size();
            		for(int j = 0; j < size; j++) {
            			// 判断是否已经被包含
            			Question ques = questions.get((int)Math.random() * size);
            			if(!paper.containsQuestion(ques)) {
            				// 不被包含则加入
            				paper.saveQuestion(i, ques);
            				break;
            			}else {
            				if(j == size - 1) {
            					// 否则到最后随机加入一个（小概率重复）
            					paper.saveQuestion(i, questions.get((int)Math.random() * size));
            				}
            			}
            		}
                }
            }
        }
    }

    /**
    * 
    * Title: select
    * Description: 选择算子：从种群中随机选择指定个数个体并中选出最优个体
    * @param population
    * @return
    * Paper
    */
    private static Paper select(Population population) {
        Population pop = new Population(CreatePaper.TOURNAME_SIZE);
        for (int i = 0; i < CreatePaper.TOURNAME_SIZE; i++) {
            pop.setPaper(i, population.getPaper((int) (Math.random() * population.getLength())));
        }
        return pop.getFitness();
    }
}

