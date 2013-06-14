package br.com.instaweb.jenkins.monitor.bean;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;


public class BeanFactory {

	private static final DefaultPicoContainer container = new DefaultPicoContainer(new Caching());
	
	public static <T> T getBean(final Class<T> beanType) {
		return container.getComponent(beanType);
	}
	public static void register(Object bean) {
		container.addComponent(bean);
	}

}
