package stanly.server.BackendTest;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("backendService")
public class BackendService {
	protected static final Logger logger = Logger.getLogger("service");

	@Async
	public Future<String> add(String uRL)
	{
		try{
			logger.info("Test");
			Thread.sleep(15000); 
		}catch(Exception e)
		{
			logger.error(e.getMessage());

		}
		return new AsyncResult<String>("완료 ㅎㅎ");
	}
}
