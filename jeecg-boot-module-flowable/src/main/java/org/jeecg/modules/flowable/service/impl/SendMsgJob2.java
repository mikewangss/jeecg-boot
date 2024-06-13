package org.jeecg.modules.flowable.service.impl;
import com.alibaba.fastjson.JSON;
import org.flowable.task.api.Task;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.flowable.factory.FlowServiceFactory;
import org.jeecg.modules.flowable.service.impl.FlowTaskServiceImpl;
import org.jeecg.modules.message.entity.SysMessage;
import org.jeecg.modules.message.handle.enums.SendMsgStatusEnum;
import org.jeecg.modules.message.service.ISysMessageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送消息任务
 * @author: jeecg-boot
 */

@Slf4j
public class SendMsgJob2 extends FlowServiceFactory implements Job {
	@Autowired
	private ISysBaseAPI sysBaseApi;
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		log.info(String.format(" Jeecg-Boot 发送消息任务 SendMsgJob !  时间:" + DateUtils.getTimestamp()));

		List<Task> taskList =  taskService.createTaskQuery().active().list();
		for (Task task : taskList) {
			if (task.getTaskDefinitionKey()!="start") {
				try {
					MessageDTO md = new MessageDTO();
					md.setToAll(false);
					md.setTitle("任务到达通知");
					md.setTemplateCode("SYS001");
					md.setToUser(task.getAssignee());
					md.setType("dingtalk");
					String testData = "{userName:'" + task.getAssignee() + "',taskName:'" + task.getName() + "'}";
					if (oConvertUtils.isNotEmpty(testData)) {
						Map<String, Object> data = JSON.parseObject(testData, Map.class);
						md.setData(data);
					}
					sysBaseApi.sendTemplateMessage(md);
				} catch (Exception e) {
					log.error(String.format(" Jeecg-Boot 发送消息任务失败 SendMsgJob !  时间:" + DateUtils.getTimestamp()));
					e.printStackTrace();
				}
			}
		}

		}

	}
