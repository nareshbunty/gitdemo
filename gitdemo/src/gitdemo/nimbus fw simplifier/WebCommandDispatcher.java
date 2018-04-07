
public class WebCommandDispatcher {

	private final WebCommandBuilder builder;

	private final CommandExecutorGateway gateway;

	public WebCommandDispatcher(BeanResolverStrategy beanResolver) {
		this.builder = beanResolver.get(WebCommandBuilder.class);
		this.gateway = beanResolver.get(CommandExecutorGateway.class);
	}
	
	public Object handle(HttpServletRequest httpReq, RequestMethod httpMethod, ModelEvent<String> event) {
		Command cmd = builder.build(httpReq, event);
		return handle(cmd, event.getPayload());
	}

	public Object handle(HttpServletRequest httpReq, RequestMethod httpMethod, String v, String json) {
		Command cmd = builder.build(httpReq);
		return handle(cmd, json);
	}

	public MultiOutput handle(Command cmd, String payload) {
		return gateway.execute(cmd, payload);
	}

}
--
public class WebCommandBuilder {

	private JustLogit logit = new JustLogit(this.getClass());
	
	public Command build(HttpServletRequest request) {
		logit.trace(()->"Received http request. "+request.getMethod()+" URI: "+request.getRequestURI());
		
		return handleInternal(request.getRequestURI(), request.getParameterMap());
	}
	
	public Command build(HttpServletRequest request, ModelEvent<String> event) {
		String uri = request.getRequestURI();
		logit.info(()->"Received http request. "+request.getMethod()+" URI: "+uri+" with event: "+event);
		
		final String constructedUri;
		if(event==null) {
			constructedUri = request.getRequestURI();
		} else {
			String clientUri = StringUtils.substringBefore(uri, Constants.SEPARATOR_URI_PLATFORM.code+Constants.SEPARATOR_URI.code); //separator = /p/
			constructedUri = clientUri + Constants.SEPARATOR_URI_PLATFORM.code + event.getPath() + Constants.SEPARATOR_URI.code +event.getType();
			
			logit.info(()->"Constructed URI: "+constructedUri);
		}
		
		
		Command cmd = handleInternal(constructedUri, request.getParameterMap());
		return cmd;
	}
	
	public Command handleInternal(String uri, Map<String, String[]> rParams) {
		Command cmd = CommandBuilder.withUri(uri).addParams(rParams).getCommand();
		return cmd;
	} 
	--
	/TODO: Hack solution, to be revisited. Objective is to set the threadContextInheritable property of DispatcherServlet
@Configuration
public class WebBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		 if (bean instanceof DispatcherServlet) {
             ((DispatcherServlet) bean).setThreadContextInheritable(true);
         }		
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
--
@RestController
//@EnableResourceServer
public class WebActionController {
	
	public static final String URI_PATTERN_P = "/{clientCode}/**/p";
	public static final String URI_PATTERN_P_OPEN = URI_PATTERN_P + "/**";

	@Autowired WebCommandDispatcher dispatcher;
	
	@Autowired ExecutionContextLoader ctxLoader;
	
	@RequestMapping(value=URI_PATTERN_P+"/clear", produces="application/json", method=RequestMethod.GET)
	public void clear() {
		ctxLoader.clear();
	}
	
	@RequestMapping(value=URI_PATTERN_P+"/gc", produces="application/json", method=RequestMethod.GET)
	public void gc() {
		System.gc();
		System.runFinalization();
	}
	
	
	@RequestMapping(value=URI_PATTERN_P_OPEN, produces="application/json", method=RequestMethod.GET)
	public Object handleGet(HttpServletRequest req, @RequestParam(required=false) String a) {
		return handleInternal(req, RequestMethod.GET, null, a);
	}
	
	@RequestMapping(value=URI_PATTERN_P_OPEN, produces="application/json", method=RequestMethod.DELETE)
	public Object handleDelete(HttpServletRequest req, @RequestParam String v) {
		return handleInternal(req, RequestMethod.DELETE, v, null);
	}
	
	@RequestMapping(value=URI_PATTERN_P_OPEN, produces="application/json", method=RequestMethod.POST)
	public Object handlePost(HttpServletRequest req, @RequestBody String json) { 
		return handleInternal(req, RequestMethod.POST, null, json);
	}
	
	@RequestMapping(value=URI_PATTERN_P_OPEN, produces="application/json", method=RequestMethod.PUT)
	public Object handlePut(HttpServletRequest req, @RequestParam String v, @RequestBody String json) {  
		return handleInternal(req, RequestMethod.PUT, v, json);
	}
	
	@RequestMapping(value=URI_PATTERN_P_OPEN, produces="application/json", method=RequestMethod.PATCH)
	public Object handlePatch(HttpServletRequest req, @RequestParam String v, @RequestBody String json) {  
		return handleInternal(req, RequestMethod.PATCH, v, json);
	}
	
	@RequestMapping(value=URI_PATTERN_P+"/event/notify", produces="application/json", method=RequestMethod.POST)
	public Object handleEventNotify(HttpServletRequest req, @RequestBody ModelEvent<String> event) {
		Object obj = dispatcher.handle(req, RequestMethod.POST, event);
		
		Holder<Object> output = new Holder<>(obj);
		return output;
	}
	
	@RequestMapping({"/login/*"})
	public ResponseEntity<?> login(){
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	
	protected Object handleInternal(HttpServletRequest req, RequestMethod httpMethod, String v, String json) {
		Object obj = dispatcher.handle(req, httpMethod, v, json);
		Holder<Object> output = new Holder<>(obj);
		return output;
	}
}

--
@ControllerAdvice(assignableTypes=WebActionController.class)
public class WebActionControllerAdvice implements ResponseBodyAdvice<Object> {
	
	private JustLogit logit = new JustLogit(this.getClass());
	
	@Autowired CommandTransactionInterceptor interceptor;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, 
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		
		logit.debug(()->"Processed response from "+WebActionController.class+": "
					+ "\n"+ body);
		
		MultiExecuteOutput multiOutput = interceptor.handleResponse(body);
		return multiOutput;
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(FrameworkRuntimeException.class)
	@ResponseBody
	public MultiExecuteOutput exception(FrameworkRuntimeException pEx){
		logit.error(()->"Logging backing execute exception...",pEx);
		
		ExecuteOutput<?> resp = new ExecuteOutput<>();
		resp.setExecuteException(pEx.getExecuteError());
		return interceptor.handleResponse(resp);		
	}
	
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public MultiExecuteOutput exception(ValidationException vEx){	
		logit.error(()->"Logging backing validation exception...",vEx);
		
		ExecuteOutput<?> resp = new ExecuteOutput<>();
		resp.setValidationResult(vEx.getValidationResult());
		return interceptor.handleResponse(resp);
	}
	
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public MultiExecuteOutput exception(MethodArgumentNotValidException vEx){	
		logit.error(()->"Logging backing validation exception...",vEx);
		
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if(vEx.getBindingResult()!=null && vEx.getBindingResult().getAllErrors()!=null){
			
			for(ObjectError objErr : vEx.getBindingResult().getAllErrors()){
				ValidationError err = new ValidationError(){};
				err.setCode(objErr.getCode());
				err.setMsg(objErr.getDefaultMessage());
				err.setModelAlias(objErr.getObjectName());
				errors.add(err);
			}
		}			
		
		ExecuteOutput<?> resp = new ExecuteOutput<>();		
		resp.setValidationResult(new ValidationResult());
		resp.getValidationResult().setErrors(errors);	
		
		return interceptor.handleResponse(resp);
	}
	
}
