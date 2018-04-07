@Getter @Setter @ToString
public class AnnotationConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String value;
	
	@JsonIgnore
	private Annotation annotation;
	
	Map<String, Object> attributes;
	
}
--1)
public interface EntityConfig<T> {

	public String getId();
	
	@JsonIgnore
	public Class<T> getReferredClass();
	
	@JsonIgnore
	public EventHandlerConfig getEventHandlerConfig();
	
	public <K> ParamConfig<K> findParamByPath(String path);
	public <K> ParamConfig<K> findParamByPath(String[] pathArr);

	
	default public boolean hasRules() {
		return getRulesConfig()!=null;
	}
	
	@JsonIgnore
	public RulesConfig getRulesConfig();
	
	@JsonIgnore
	default boolean isMapped() {
		return false;
	}
	
	default public MappedConfig<T, ?> findIfMapped() {
		return null;
	}

	public interface MappedConfig<T, M> extends EntityConfig<T> {
		@Override
		default boolean isMapped() {
			return true;
		}
		
		@Override
		default public MappedConfig<T, ?> findIfMapped() {
			return this;
		}
		
		public EntityConfig<M> getMapsToConfig();
	}
}
--
@Getter @Setter
abstract public class AbstractEntityConfig<T> implements EntityConfig<T> {

	@JsonIgnore 
	final protected JustLogit logit = new JustLogit(getClass());

	private AnnotationConfig uiStyles;

	@JsonIgnore 
	private RulesConfig rulesConfig; 
	
	private static final AtomicInteger counter = new AtomicInteger();
	
	private final String id;

	@JsonIgnore
	private EventHandlerConfig eventHandlerConfig;

	public AbstractEntityConfig() {
		this(generateNextId());
	}
	
	public AbstractEntityConfig(String id) {
		this.id = id;
	}
	
	protected static String generateNextId() {
		return String.valueOf(counter.incrementAndGet());
	}
}
--
@Inherited
public @interface Model {
	
	/**
	 * alias
	 */
	String value() default "";
	
	String rules() default "drools";
	
	// list of listeners to exclude at a nested model level. Use only to exclude a particular listener defined at a root Domain level
	ListenerType[] excludeListeners() default { };
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public @interface Param {
		
		@Retention(RetentionPolicy.RUNTIME)
		@Target({ElementType.FIELD})
		public @interface Values {
			
			public static interface Source {
				public List<ParamValue> getValues(String paramCode);
			}
			
			public static class EMPTY implements Source {
				@Override
				public List<ParamValue> getValues(String paramPath) {
					return null;
				}
			}
			
			Class<? extends Source> value() default EMPTY.class;
			
			String url() default "staticCodeValue";
		}

	}
	
}
--
public interface ModelConfig<T> extends EntityConfig<T> {
	
	public String getAlias();
	
	public String getDomainLifecycle();
	
	public Repo getRepo();

	//@JsonIgnore
	public List<? extends ParamConfig<?>> getParamConfigs();
	
	public ParamConfig<?> getIdParamConfig();
	public ParamConfig<?> getVersionParamConfig();
	
	public CollectionsTemplate<List<ParamConfig<?>>, ParamConfig<?>> templateParamConfigs();
	
	public RulesConfig getRulesConfig();

	@Override
	default MappedModelConfig<T, ?> findIfMapped() {
		return null;
	}
	
	default boolean isRoot() {
		return false;
	}
	
	public interface MappedModelConfig<T, M> extends ModelConfig<T>, MappedConfig<T, M> {
		@Override
		default boolean isMapped() {
			return true;
		}
		
		@Override
		default MappedModelConfig<T, M> findIfMapped() {
			return this;
		}
		
		@Override
		public ModelConfig<M> getMapsToConfig();
	}
	
}
--
@Model
@Getter @Setter
public class ParamValue implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Object code;
	
	private String label;
	
	private String desc;
	
	//private boolean isActive = true;
	
	public ParamValue() {}
	
	public ParamValue(Object code) {
		this(code, null, null);
	}
	
	public ParamValue(Object code, String label) {
		this(code, label, null);
	}

	public ParamValue(Object code, String label, String desc) {
		this.code = code;
        setLabel(label);
        setDesc(desc);
	}
}

--
@Getter @Setter @ToString @RequiredArgsConstructor
public class ParamConfigType implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public enum CollectionType {
		array,
		list,
		page
	}
	
	
	final private boolean nested;
	
	final private String name;
	
	@JsonIgnore
	final private Class<?> referredClass;
	
	@JsonIgnore
	final private boolean array;
	
	public <T> Nested<T> findIfNested() {
		return null;
	}
	
	public <T> NestedCollection<T> findIfCollection() {
		return null;
	}
	
	public boolean isCollection() {
		return false;
	}
	
--
public interface ParamConfig<P> extends EntityConfig<P>, Findable<String> {

	public String getCode();
	public String getBeanName();
	
//	@JsonIgnore M7
	public ParamConfigType getType();
	
	public boolean isLeaf();
	
	@Getter @Setter @ToString
	public static class LabelConfig {
		private String locale; //default en-US
		private String text;
		private String helpText;
	}
	public List<LabelConfig> getLabelConfigs();
	
	
	public List<Execution.Config> getExecutionConfigs();
	
	public List<AnnotationConfig> getValidations();
	
	public List<AnnotationConfig> getUiNatures();
	
	public AnnotationConfig getUiStyles();
	
	// TODO Soham: change to list of notification handler annotations
	public List<AnnotationConfig> getRules();
	
	public Values getValues();
	
	public List<ParamConverter> getConverters();
	
	public List<AssociatedEntity> getAssociatedEntities();

	public void onCreateEvent();
	
	@JsonIgnore
	default boolean isTransient() {
		return false;
	}
	
	@JsonIgnore
	default MappedTransientParamConfig<P, ?> findIfTransient() {
		return null;
	}
	
	@JsonIgnore
	default MapsTo.Mode getMappingMode() {
		return MapsTo.Mode.UnMapped;
	}
	
	@Override
	default MappedParamConfig<P, ?> findIfMapped() {
		return null;
	}
	
	public interface MappedParamConfig<P, M> extends ParamConfig<P>, MappedConfig<P, M> {
		@Override
		default boolean isMapped() {
			return true;
		}
		
		@Override
		default MappedParamConfig<P, M> findIfMapped() {
			return this;
		}
		
		public Path getPath();

		@Override
		default Mode getMappingMode() {
			return MapsTo.getMode(getPath());
		}
		
		public boolean isDetachedWithAutoLoad(); // e.g. @Path(value="/a/b/c/action", linked=false)
		
		@Override
		public ParamConfig<M> getMapsToConfig();
		
		public ModelConfig<?> getMapsToEnclosingModel();
	}
	
	public interface MappedTransientParamConfig<P, M> extends MappedParamConfig<P, M> {
		@Override
		default boolean isTransient() {
			return true;
		}
		
		@Override @JsonIgnore
		default MappedTransientParamConfig<P, ?> findIfTransient() {
			return this;
		}
		
		MappedParamConfig<P, M> getSimulatedDetached();
	}
}
--
public interface RulesConfig {
	
	public String getPath();
	
	public <R> R unwrap(Class<R> clazz);
}
--
public interface EventHandlerConfig {

	public Set<Annotation> getOnParamCreateAnnotations();
	public Optional<OnParamCreateHandler<Annotation>> findOnParamCreateHandler(Annotation a);
	public OnParamCreateHandler<Annotation> getOnParamCreateHandler(Annotation a) throws InvalidConfigException;
	
	
	public Set<Annotation> getOnStateLoadAnnotations();
	public Optional<OnStateLoadHandler<Annotation>> findOnStateLoadHandler(Annotation a);
	public OnStateLoadHandler<Annotation> getOnStateLoadHandler(Annotation a) throws InvalidConfigException;
	
	
	public Set<Annotation> getOnStateChangeAnnotations();
	public Optional<OnStateChangeHandler<Annotation>> findOnStateChangeHandler(Annotation a);
	public OnStateChangeHandler<Annotation> getOnStateChangeHandler(Annotation a) throws InvalidConfigException;
	
 }
 --
 public interface ValidatorProvider {

	public Validator getValidator();
	
}
-- internal:
@Getter @Setter @ToString(callSuper=true, of={"alias", "referredClass"}) @RequiredArgsConstructor
public class DefaultModelConfig<T> extends AbstractEntityConfig<T> implements ModelConfig<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore private final Class<T> referredClass;
	
	@JsonIgnore private String alias;
	
	@JsonIgnore private Domain domain;
	@JsonIgnore private Model model;
	
	@JsonIgnore private Repo repo;
	
	private List<ParamConfig<?>> paramConfigs;
	
	@JsonIgnore private transient ParamConfig<?> idParamConfig;
	
	@JsonIgnore	private transient ParamConfig<?> versionParamConfig;
	
	@JsonIgnore
	private final transient CollectionsTemplate<List<ParamConfig<?>>, ParamConfig<?>> templateParamConfigs = new CollectionsTemplate<>(
			() -> getParamConfigs(), (p) -> setParamConfigs(p), () -> new LinkedList<>());

	@Override @JsonIgnore
	public CollectionsTemplate<List<ParamConfig<?>>, ParamConfig<?>> templateParamConfigs() {
		return templateParamConfigs;
	}

	@Override @JsonIgnore 
	public String getDomainLifecycle() {
		return Optional.ofNullable(getDomain()).map(Domain::lifecycle).orElse(null);
	}
	
	@Override
	public <K> ParamConfig<K> findParamByPath(String path) {
		// handle scenario if path = "/"
		String splits[] = StringUtils.equals(Constants.SEPARATOR_URI.code, StringUtils.trimToNull(path))
				? new String[] { path } : StringUtils.split(path, Constants.SEPARATOR_URI.code);

		return findParamByPath(splits);
	}
	
	@Override
	public <K> ParamConfig<K> findParamByPath(String[] pathArr) {
		if(ArrayUtils.isEmpty(pathArr)) return null;
		
		String nPath = pathArr[0];
		
		@SuppressWarnings("unchecked")
		ParamConfig<K> p = (ParamConfig<K>)templateParamConfigs().find(nPath);
		if(p == null) return null;
		
		if(pathArr.length == 1) { //last one
			return p;
		}
		
		return p.findParamByPath(ArrayUtils.remove(pathArr, 0));
	}
	
}
--
@Getter @Setter @ToString(callSuper=true, of={"code", "beanName", "type"})
public class DefaultParamConfig<P> extends AbstractEntityConfig<P> implements ParamConfig<P>, Serializable {

	private static final long serialVersionUID = 1L;

	final private String code;
	
	@JsonIgnore
	final private String beanName;

	private ParamConfigType type;	
	
	private List<LabelConfig> labelConfigs;

	private List<AnnotationConfig> validations;
	
	private List<AnnotationConfig> uiNatures;
	
	@JsonIgnore
	private List<AnnotationConfig> rules;
	
	
	@JsonIgnore
	private List<Execution.Config> executionConfigs;

	@JsonIgnore 
	private List<ParamConverter> converters;
	
	@JsonIgnore
	private Values values;
	
	@JsonIgnore @Setter 
	private List<AssociatedEntity> associatedEntities;

	protected DefaultParamConfig(String code) {
		this(code, code, generateNextId());
	}
	
	protected DefaultParamConfig(String code, String beanName) {
		super();
		
		Objects.requireNonNull(code, ()->"code in param config must not be null");
		Objects.requireNonNull(beanName, ()->"beanName in param config must not be null");
		
		this.code = code.intern();
		this.beanName = beanName.intern();
		
	}
	
	protected DefaultParamConfig(String code, String beanName, String id) {
		super(id);
		
		Objects.requireNonNull(code, ()->"code in param config must not be null");
		Objects.requireNonNull(beanName, ()->"beanName in param config must not be null");
		Objects.requireNonNull(id, ()->"id in param config must not be null");
		
		this.code = code.intern();
		this.beanName = beanName.intern();
	}


	final public static <T> DefaultParamConfig<T> instantiate(ModelConfig<?> mConfig, String code) {
		return instantiate(mConfig, code, code);
	}
	
	final public static <T> DefaultParamConfig<T> instantiate(ModelConfig<?> mConfig, String code, String beanName) {
		return new DefaultParamConfig<>(code, beanName);
	} 
	
	@JsonIgnore
	@SuppressWarnings("unchecked")
	@Override
	public Class<P> getReferredClass() {
		return (Class<P>)getType().getReferredClass();
	}
	
	@Override
	public boolean isFound(String by) {
		return StringUtils.equals(getCode(), by);
	}
	
	@JsonIgnore
	@Override
	public boolean isLeaf() {
		return !getType().isNested();
	}
	
	@Override
	public <K> ParamConfig<K> findParamByPath(String path) {
		String splits[] = StringUtils.split(path, Constants.SEPARATOR_URI.code);
		return findParamByPath(splits);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <K> ParamConfig<K> findParamByPath(String[] pathArr) {
		if(ArrayUtils.isEmpty(pathArr))
			return null;
		
		/* param is not leaf node: is nested */
		ParamConfigType.Nested<?> mp = getType().findIfNested();
		if(mp != null) {
			return mp.getModelConfig().findParamByPath(pathArr);
		}
		
		/* if param is a leaf node and requested path has more children, then return null */
		if(pathArr.length > 1) return null;
		
		/* param is leaf node */
		ParamConfig<K> p = isFound(pathArr[0]) ? (ParamConfig<K>) this : null;
		return p;
	}
	
	@Override
	public void onCreateEvent() {
		Optional.ofNullable(getEventHandlerConfig())
			.map(EventHandlerConfig::getOnParamCreateAnnotations)
			.ifPresent(
				set->set.stream()
					.forEach(ac-> {
						OnParamCreateHandler<Annotation> handler = getEventHandlerConfig().getOnParamCreateHandler(ac); 
						handler.handle(ac, this);
					})
			);
	}
	
}
--
bvn
-
@Getter @Setter
public class DefaultEventHandlerConfig implements EventHandlerConfig {

	// config handlers
	private _InternalConfig<OnParamCreateHandler<Annotation>> onParamCreateHandlers = new _InternalConfig<>();
	
	// state handlers
	private _InternalConfig<OnStateLoadHandler<Annotation>> onStateLoadHandlers = new _InternalConfig<>();
	private _InternalConfig<OnStateChangeHandler<Annotation>> onStateChangeHandlers = new _InternalConfig<>();
	
	
	private static class _InternalConfig<T> {
		private Map<Annotation, T> eventHandlers;
		
		public boolean isEmpty() {
			return MapUtils.isEmpty(eventHandlers);
		}
		
		public Set<Annotation> getAnnotations() {
			return Optional.ofNullable(eventHandlers).map(Map::keySet).orElse(null);	
		}
		
		public void add(Annotation a, T handler) {
			Optional.ofNullable(eventHandlers).orElseGet(()->{
				eventHandlers = new HashMap<>();
				return eventHandlers;
			}).put(a, handler);
		}
		
		public Optional<T> findHandler(Annotation a) {
			return Optional.ofNullable(eventHandlers).map(handlers->handlers.get(a));
		}
		
		public T getHandler(Annotation a) throws InvalidConfigException {
			return findHandler(a).orElseThrow(()->getEx(a));
		}
		
		public InvalidConfigException getEx(Annotation a) {
			return new InvalidConfigException("Expected event handler for annotation: "+a+ " not found");
		}
	}
	
	public boolean isEmpty() {
		return 
			onParamCreateHandlers.isEmpty() && 
			
			onStateLoadHandlers.isEmpty() &&
			onStateChangeHandlers.isEmpty()
		;
 	}
	
	/* onParamCreate */
	@Override
	public Set<Annotation> getOnParamCreateAnnotations() {
		return onParamCreateHandlers.getAnnotations();
	}
	
	@Override
	public Optional<OnParamCreateHandler<Annotation>> findOnParamCreateHandler(Annotation a) {
		return onParamCreateHandlers.findHandler(a);
	}
	
	public void add(Annotation a, OnParamCreateHandler<Annotation> handler) {
		onParamCreateHandlers.add(a, handler);
	}
	
	@Override
	public OnParamCreateHandler<Annotation> getOnParamCreateHandler(Annotation a) throws InvalidConfigException {
		return onParamCreateHandlers.getHandler(a);
	}
	
	/* onStateLoad */
	@Override
	public Set<Annotation> getOnStateLoadAnnotations() {
		return onStateLoadHandlers.getAnnotations();
	}
	
	public void add(Annotation a, OnStateLoadHandler<Annotation> handler) {
		onStateLoadHandlers.add(a, handler);
	}
	
	@Override
	public Optional<OnStateLoadHandler<Annotation>> findOnStateLoadHandler(Annotation a) {
		return onStateLoadHandlers.findHandler(a);
	}
	
	@Override
	public OnStateLoadHandler<Annotation> getOnStateLoadHandler(Annotation a) throws InvalidConfigException {
		return onStateLoadHandlers.getHandler(a);
	}
	
	/* onStateChange */
	@Override
	public Set<Annotation> getOnStateChangeAnnotations() {
		return onStateChangeHandlers.getAnnotations();
	}
	
	public void add(Annotation a, OnStateChangeHandler<Annotation> handler) {
		onStateChangeHandlers.add(a, handler);
	}
	
	@Override
	public Optional<OnStateChangeHandler<Annotation>> findOnStateChangeHandler(Annotation a) {
		return onStateChangeHandlers.findHandler(a);
	}
	
	@Override
	public OnStateChangeHandler<Annotation> getOnStateChangeHandler(Annotation a) throws InvalidConfigException {
		return onStateChangeHandlers.getHandler(a);
	}
}
--
@Getter @ToString(callSuper=true, of={"mapsToConfig"})
public class MappedDefaultModelConfig<T, M> extends DefaultModelConfig<T> implements MappedModelConfig<T, M>, Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore final private ModelConfig<M> mapsToConfig;
	
	public MappedDefaultModelConfig(ModelConfig<M> mapsToConfig, Class<T> referredClass) {
		super(referredClass);
		this.mapsToConfig = mapsToConfig;
	}
		
}
--
bvn-
@Getter @ToString(callSuper=true, of={"mapsToConfig", "path"})
public class MappedDefaultParamConfig<P, M> extends DefaultParamConfig<P> implements MappedParamConfig<P, M>, Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore final private ModelConfig<?> mapsToEnclosingModel;
	
	@JsonIgnore	final private ParamConfig<M> mapsToConfig;

	@JsonIgnore final private Path path;
	
	public static class NoConversion<P, M> extends MappedDefaultParamConfig<P, M> {
		private static final long serialVersionUID = 1L;
		
		public NoConversion(ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsTo) {
			super(mapsTo.getCode(), mapsTo.getBeanName(), mapsToEnclosingModel, mapsTo, createNewImplicitMapping("", true), mapsTo.getId());
			
			init(mapsTo);
		}
		
		private void init(ParamConfig<M> mapsTo) {
			setConverters(mapsTo.getConverters());
			setExecutionConfigs(mapsTo.getExecutionConfigs());
			setRules(mapsTo.getRules());
			setType(mapsTo.getType());
			setUiNatures(mapsTo.getUiNatures());
			setUiStyles(mapsTo.getUiStyles());
			setValidations(mapsTo.getValidations());
			setLabelConfigs(mapsTo.getLabelConfigs());
			setValues(mapsTo.getValues());
			setEventHandlerConfig(mapsTo.getEventHandlerConfig());
		}

	}
	
	public MappedDefaultParamConfig(String code, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsTo, Path path) {
		this(code, code, mapsToEnclosingModel, mapsTo, path);
	}
	
	public MappedDefaultParamConfig(String code, String beanName, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsToConfig, Path path) {
		super(code, beanName);
		this.mapsToEnclosingModel = mapsToEnclosingModel;
		this.mapsToConfig = mapsToConfig;
		this.path = path;
	}
	
	public MappedDefaultParamConfig(String code, String beanName, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsToConfig, Path path, String id) {
		super(code, beanName, id);
		this.mapsToEnclosingModel = mapsToEnclosingModel;
		this.mapsToConfig = mapsToConfig;
		this.path = path;
	}
	
	@Override
	public boolean isMapped() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isDetachedWithAutoLoad() {
		
		if(getMappingMode() == Mode.MappedDetached
				&& getPath().detachedState() != null
				&& getPath().detachedState().loadState() == LoadState.AUTO
				&& StringUtils.isNotBlank(getPath().value())) {
			
			return true;
		}
		return false;
	}
	
	
	public static MapsTo.Path createNewImplicitMapping(String mappedPath, boolean linked) {
		return createNewImplicitMapping("", true, new DetachedState() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return MapsTo.DetachedState.class;
			}
			
			@Override
			public boolean manageState() {
				return false;
			}
			
			@Override
			public LoadState loadState() {
				return LoadState.PROVIDED;
			}
			
			@Override
			public Cache cacheState() {
				return Cache.rep_none;
			}
		});
	}
	
	public static MapsTo.Path createNewImplicitMapping(String mappedPath, boolean linked, DetachedState detachedState) {
		return createNewImplicitMapping(mappedPath, linked, "", detachedState);
	}
	
	public static MapsTo.Path createNewImplicitMapping(String mappedPath, boolean linked, String colElemPath, DetachedState detachedState) {
		return new MapsTo.Path() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return MapsTo.Path.class;
			}
			
			@Override
			public String value() {
				return mappedPath;
			}
			
			@Override
			public boolean linked() {
				return linked;
			}
			
			@Override
			public String colElemPath() {
				return colElemPath;
			}
			
			@Override
			public DetachedState detachedState() {
				return detachedState;
			}
			
			@Override
			public Nature nature() {
				return Nature.Default;
			}
			
			@Override
			public String toString() {
				return new StringBuilder().append(MapsTo.Path.class)
							.append(" value: ").append(value())
							.append(" linked: ").append(linked())
							.toString();
			}
		};
	}
}
--
public class MappedDefaultTransientParamConfig<P, M> extends MappedDefaultParamConfig<P, M> implements MappedTransientParamConfig<P, M> {

	private static final long serialVersionUID = 1L;
	
	private final ParamConfig<P> simulatedMappedDetached;

	public MappedDefaultTransientParamConfig(ParamConfig<P> simulatedMappedDetached, String code, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsTo, Path path) {
		super(code, mapsToEnclosingModel, mapsTo, path);
		this.simulatedMappedDetached = simulatedMappedDetached;
	}
	
	public MappedDefaultTransientParamConfig(ParamConfig<P> simulatedMappedDetached, String code, String beanName, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsToConfig, Path path) {
		super(code, beanName, mapsToEnclosingModel, mapsToConfig, path);
		this.simulatedMappedDetached = simulatedMappedDetached;
	}
	
	public MappedDefaultTransientParamConfig(ParamConfig<P> simulatedMappedDetached, String code, String beanName, ModelConfig<?> mapsToEnclosingModel, ParamConfig<M> mapsToConfig, Path path, String id) {
		super(code, beanName, mapsToEnclosingModel, mapsToConfig, path, id);
		this.simulatedMappedDetached = simulatedMappedDetached;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MappedParamConfig<P, M> getSimulatedDetached() {
		return MappedParamConfig.class.cast(simulatedMappedDetached);
	}
}
--