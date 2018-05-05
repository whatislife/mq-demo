package com.zhht.aliyun.mq.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component
public class LogJobTask {

	@Autowired
	//CopOperationService copOperationService;

	ExecutorService threadPoolOffer = Executors.newSingleThreadExecutor();

//	public static Logger logger = LogManager.getLogger(LogJobTask.class);

	public void runJob() throws Exception {

		threadPoolOffer.execute(new Runnable() {

			public void run() {
				try {
					while (true) {
						//数据监听

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
