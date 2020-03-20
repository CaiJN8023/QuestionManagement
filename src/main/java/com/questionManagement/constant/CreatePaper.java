package com.questionManagement.constant;

/**
 * 
 * @author CJN
 * @date 2019年3月24日
 * Title: CreatePaper 
 * Description: 全局变量，组卷参数
 */
public class CreatePaper {

    /**
     * 章节权重
     */
    public static final double CHAPTER_WEIGHT = 0.20;
    /**
     * 难度权重
     */
    public static final double DIFFCULTY_WEIGHT = 0.80;
    
    /**
     * 变异概率
     */
    public static final double MUTATION_RATE = 0.085;
    /**
     * 精英主义
     */
    public static final boolean ELITISM = true;
    /**
     * 淘汰数组大小
     */
    public static final int TOURNAME_SIZE = 5;
    /**
     * 最大循环迭代次数
     */
    public static final int Run_COUNT = 100;
    /**
     * 适应度期望值
     */
    public static final double EXPAND = 0.98;
    /**
     * 初始化种群大小
     */
    public static final int INIT_SIZE = 10;
    /**
     * 初始化标志
     */
	public static final boolean INIT_FLAG = true;
}
