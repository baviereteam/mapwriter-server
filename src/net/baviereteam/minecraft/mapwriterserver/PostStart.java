package net.baviereteam.minecraft.mapwriterserver;

import java.util.UUID;
import java.util.logging.Logger;

import net.baviereteam.minecraft.mapwriterserver.service.MasterKeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PostStart implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void run(String... arg0) throws Exception {
		String masterKey = UUID.randomUUID().toString();
		MasterKeyService.getInstance().setMasterKey(masterKey);
		Logger.getLogger(this.getClass().getName())
			.info("\n\nMaster key is: " + masterKey + "\n");
	}

}
