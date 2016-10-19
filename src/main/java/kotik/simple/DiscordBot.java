/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotik.simple;

import javafx.application.Application;
import kotik.simple.service.DiscordClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DiscordBot extends SpringBootServletInitializer {

	@Autowired
	private DiscordClient discord;

	@Autowired
	private InterfaceListener interfaceListener;

	@Autowired
	private ChatListener chatListener;


	public static void main(String[] args) throws Exception {
		SpringApplication.run(DiscordBot.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		discord.dispather.registerListener(interfaceListener);
		discord.dispather.registerListener(chatListener);
		return application.sources(applicationClass);
	}

	private static Class<Application> applicationClass = Application.class;

}
