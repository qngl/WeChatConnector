package com.vwfsag.fso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * @author qngl
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.vwfsag.fso.service")
public class ServiceConfig {

	@Bean
	public HazelcastInstance hazelcastInstance() {
		Config cfg = new Config();
		return Hazelcast.newHazelcastInstance(cfg);
	}

}
