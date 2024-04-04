package org.blaisesolutions.services;

import org.blaisesolutions.entity.Log;
import java.util.List;

public interface MyService {

	Log addLog(Log log);

	List<Log> getLogs();

	void deleteLog(Log log);
}
