package stanly.server.PollutionView.DAO;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PollutionViewDAO {
	protected static final Logger logger = Logger.getLogger("PollutionViewDAO");
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
}
