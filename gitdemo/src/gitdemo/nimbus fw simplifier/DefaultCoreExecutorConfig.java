plz see in this pkg and complete it:

package com.antheminc.oss.nimbus.app.extension.config;


@Configuration
public class DefaultCoreExecutorConfig {
	
	@Bean
	public ExpressionEvaluator expressionEvaluator() {
		return new SpelExpressionEvaluator();
	}
	
	@Bean
	public CommandMessageConverter commandMessageConverter(BeanResolverStrategy beanResolver) {
		return new CommandMessageConverter(beanResolver);
	}
	
	@Bean
	public CommandTransactionInterceptor commandTransactionInterceptor(){
		return new CommandTransactionInterceptor();
	}
	
	@Bean
	public CommandPathVariableResolver defaultCommandPathVariableResolver(BeanResolverStrategy beanResolver, PropertyResolver propertyResolver) {
		return new DefaultCommandPathVariableResolver(beanResolver, propertyResolver);
	}
	
	@Bean
	public ExecutionContextPathVariableResolver defaultExecutionContextPathVariableResolver() {
		return new DefaultExecutionContextPathVariableResolver();
	}
	
	@Bean(name="default.ExecutionContextLoader", destroyMethod="clear") 
	//@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, scopeName="session")
	public ExecutionContextLoader defaultExecutionContextLoader(BeanResolverStrategy beanResolver) {
		return new DefaultExecutionContextLoader(beanResolver);
	}
	
	@Bean(name="default._new$execute")
	public CommandExecutor<?> defaultActionExecutorNew(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorNew(beanResolver);
	}
	
	@Bean(name="default._get$execute")
	public CommandExecutor<?> defaultActionExecutorGet(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorGet(beanResolver);
	}
	
	@Bean(name="default._nav$execute")
	public CommandExecutor<?> defaultActionExecutorNav(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorNav<>(beanResolver);
	}
	
	@Bean(name="default._process$execute")
	public CommandExecutor<?> defaultActionExecutorProcess(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorProcess<>(beanResolver);
	}
	
	@Bean(name="default._search$execute")
	public CommandExecutor<?> defaultActionExecutorSearch(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorSearch<>(beanResolver);
	}
	
	@Bean(name="default._update$execute")
	public CommandExecutor<?> defaultActionExecutorUpdate(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorUpdate(beanResolver);
	}
	
	@Bean(name="default._delete$execute")
	public CommandExecutor<?> defaultActionProcessExecutorDelete(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorDelete(beanResolver);
	}
	
	@Bean(name="default._replace$execute")
	public CommandExecutor<?> defaultActionProcessExecutorReplace(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorReplace(beanResolver);
	}
	
	@Bean(name="default._config$execute")
	public CommandExecutor<?> defaultBehaviorExecutorConfig(BeanResolverStrategy beanResolver){
		return new DefaultActionExecutorConfig(beanResolver);
	}
	
	@Bean(name="default._get$state")
	public CommandExecutor<?> defaultActionBehaviorExecutorGetState(BeanResolverStrategy beanResolver){
		return new DefaultActionBehaviorExecutorGetState(beanResolver);
	}
	
	@Bean
	public HierarchyMatchBasedBeanFinder hierarchyMatchBasedBeanFinder(){
		return new HierarchyMatchBasedBeanFinder();
	}
	
	
	@Bean(name="default.processGateway")
	public DefaultCommandExecutorGateway defaultProcessGateway(BeanResolverStrategy beanResolver){
		return new DefaultCommandExecutorGateway(beanResolver);
	}
	
	@Bean(name="searchByExample")
	public DBSearch searchByExample(BeanResolverStrategy beanResolver) {
		return new MongoSearchByExample(beanResolver);
	}
	
	@Bean(name="searchByQuery")
	public DBSearch searchByQuery(BeanResolverStrategy beanResolver) {
		return new MongoSearchByQuery(beanResolver);
	}
	
}
