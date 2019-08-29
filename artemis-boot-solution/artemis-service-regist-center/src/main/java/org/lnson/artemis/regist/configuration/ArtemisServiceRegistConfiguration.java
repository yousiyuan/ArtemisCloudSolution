package org.lnson.artemis.regist.configuration;

import org.lnson.artemis.regist.listener.ArtemisServiceRegistListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ArtemisServiceRegistListener.class})
public class ArtemisServiceRegistConfiguration {
}
