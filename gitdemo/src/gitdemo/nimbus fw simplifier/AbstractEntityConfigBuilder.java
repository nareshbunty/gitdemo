
--1
public interface EntityConfigBuilder {

	<T> ModelConfig<T> load(Class<T> clazz, EntityConfigVisitor visitedModels);

	<T> ModelConfig<T> buildModel(Class<T> clazz, EntityConfigVisitor visitedModels);

	<T> ParamConfig<?> buildParam(ModelConfig<T> mConfig, Field f, EntityConfigVisitor visitedModels);

}

--6
public final class ConfigEventHandlers {

	@EventHandler(OnParamCreate.class)
	public interface OnParamCreateHandler<A extends Annotation> {
		
		public void handle(A configuredAnnotation, ParamConfig<?> param);
	}
}
--7
public abstract class AbstractConfigEventHandler<A extends Annotation> {

	protected <T> T castOrEx(Class<T> type, ParamConfig<?> param) {
		if(!type.isInstance(param))
			throw new InvalidConfigException("Handler supports ParamConfig of type: "+type+" but found of type: "+param.getClass());
		
		return type.cast(param);
	}
}
---2
@Getter @Setter
abstract public class AbstractEntityConfigBuilder {

	protected JustLogit logit = new JustLogit(getClass());
	
	private final BeanResolverStrategy beanResolver;
	private final RulesEngineFactoryProducer rulesEngineFactoryProducer;
	private final EventHandlerConfigFactory eventHandlerConfigFactory;
	private final AnnotationConfigHandler annotationConfigHandler;
	
	public AbstractEntityConfigBuilder(BeanResolverStrategy beanResolver) {
		this.beanResolver = beanResolver;
		this.rulesEngineFactoryProducer = beanResolver.get(RulesEngineFactoryProducer.class);
		this.eventHandlerConfigFactory = beanResolver.get(EventHandlerConfigFactory.class);
		this.annotationConfigHandler = beanResolver.get(AnnotationConfigHandler.class);
	}
	
	abstract public <T> ModelConfig<T> buildModel(Class<T> clazz, EntityConfigVisitor visitedModels);
	
	abstract public <T> ParamConfig<?> buildParam(ModelConfig<T> mConfig, Field f, EntityConfigVisitor visitedModels);
	
	abstract protected <T, P> ParamConfigType buildParamType(ModelConfig<T> mConfig, ParamConfig<P> pConfig, Field f, EntityConfigVisitor visitedModels);
	abstract protected <T, P> ParamConfigType buildParamType(ModelConfig<T> mConfig, ParamConfig<P> pConfig, ParamConfigType.CollectionType colType, Class<?> pDirectOrColElemType, EntityConfigVisitor visitedModels); 
	
	public boolean isPrimitive(Class<?> determinedType) {
		return ClassUtils.isPrimitiveOrWrapper(determinedType) || String.class==determinedType;
	}
	
	public <T> DefaultModelConfig<T> createModel(Class<T> referredClass, EntityConfigVisitor visitedModels) {
		MapsTo.Type mapsToType = AnnotationUtils.findAnnotation(referredClass, MapsTo.Type.class);
		
		final DefaultModelConfig<T> created;
		if(mapsToType!=null) {
			
			ModelConfig<?> mapsTo = visitedModels.get(mapsToType.value());
			if(mapsTo==null)
				throw new InvalidConfigException(MapsTo.Type.class.getSimpleName()+" not found: "+mapsToType + " in mapped: " + referredClass);
			
			created = new MappedDefaultModelConfig<>(mapsTo, referredClass);
			
		} else {
			created = new DefaultModelConfig<>(referredClass);
		}
		
		// annotation config: viewStyle
		created.setUiStyles(annotationConfigHandler.handleSingle(referredClass, ViewStyle.class));
		
		// handle repo
		Repo rep = AnnotationUtils.findAnnotation(referredClass, Repo.class);
		created.setRepo(rep);
		
		// set alias from domain or model
		assignDomainAndModel(created, created::setAlias);
				
		// rules
		Optional.ofNullable(created.getAlias())
			.map(d->rulesEngineFactoryProducer.getFactory(referredClass))
			.map(f->f.createConfig(created.getAlias()))
				.ifPresent(c->created.setRulesConfig(c));
		return created; 
	}
	
	protected void assignDomainAndModel(DefaultModelConfig<?> created, Consumer<String> cb) {
		// prefer @Domain or @Model declared on current class
		Domain domain = AnnotationUtils.findAnnotation(created.getReferredClass(), Domain.class);
		created.setDomain(domain);
		
		// set model if domain is absent
		Model model = AnnotationUtils.findAnnotation(created.getReferredClass(), Model.class);
		created.setModel(model);

		
		if(domain==null && model!=null)
			cb.accept(model.value());
		else
			if(domain!=null && model==null)
				cb.accept(domain.value());
		else
			if(domain!=null && model!=null // both present with different alias entries
					&& StringUtils.trimToNull(domain.value())!=null && StringUtils.trimToNull(model.value())!=null 
					&& !StringUtils.equals(domain.value(), model.value())) {
				
				// prefer annotation declared directly on class
				if(AnnotationUtils.isAnnotationInherited(Domain.class, created.getReferredClass()) 
						&& !AnnotationUtils.isAnnotationInherited(Model.class, created.getReferredClass()))
					cb.accept(model.value());
				else 
					if(!AnnotationUtils.isAnnotationInherited(Domain.class, created.getReferredClass()) 
							&& AnnotationUtils.isAnnotationInherited(Model.class, created.getReferredClass()))	
						cb.accept(domain.value());
				else
					throw new InvalidConfigException("A model can have alias defined in either @Domain or @Model. "
							+ "Found in both with different values for class: "+created.getReferredClass()
							+" with @Domain: "+domain+" and @Model: "+model);
			}
		else 
			cb.accept(domain.value());
		
	}
	
	public <T> DefaultModelConfig<List<T>> createCollectionModel(Class<List<T>> referredClass, ParamConfig<?> associatedParamConfig) {
		//mapsTo is null when the model itself is a java List implementation (ArrayList, etc)
		
		DefaultModelConfig<List<T>> coreConfig = new DefaultModelConfig<>(referredClass);
		
		if(associatedParamConfig.isMapped()) {
			return new MappedDefaultModelConfig<>(coreConfig, referredClass);
		} else {
			return coreConfig;
		}
	}
	
	public DefaultParamConfig<?> createParam(ModelConfig<?> mConfig, Field f, EntityConfigVisitor visitedModels) {
		MapsTo.Path mapsToPath = AnnotationUtils.findAnnotation(f, MapsTo.Path.class);
		MapsTo.Mode mapsToMode = MapsTo.getMode(mapsToPath);
		
		// no path specified
		if(mapsToMode==MapsTo.Mode.UnMapped)
			return decorateParam(mConfig, f, DefaultParamConfig.instantiate(mConfig, f.getName()), visitedModels); 
			
		// check if field is mapped with linked=true: which would require parent model to also be mapped
		if(mapsToMode==MapsTo.Mode.MappedAttached)
			return createMappedParamAttached(mConfig, f, visitedModels, mapsToPath);
		else 
			return createMappedParamDetached(mConfig, f, visitedModels, mapsToPath);
	}
	
	private DefaultParamConfig<?> createMappedParamAttached(ModelConfig<?> mConfig, Field f, EntityConfigVisitor visitedModels, MapsTo.Path mapsToPath) {
		// param field is linked: enclosing model must be mapped
		if(!mConfig.isMapped())
			throw new InvalidConfigException("Mapped param field: "+f.getName()+" is mapped with linked=true. Enclosing model: "+mConfig.getReferredClass()+" must be mapped, but was not.");

		// param field is linked: enclosing mapped model's config must have been loaded
		Class<?> mapsToModelClass = mConfig.findIfMapped().getMapsToConfig().getReferredClass();
		ModelConfig<?> mapsToModel = visitedModels.get(mapsToModelClass);
		if(mapsToModel==null)
			throw new ConfigLoadException("Mapped param field: "+f.getName()+" is mapped with linked=true. Enclosing model: "+mConfig.getReferredClass()
				+" mapsToModelClass: "+mapsToModelClass+" which must have been loaded prior, but wasn't.");
		
		// find mapsTo param from mapsTo model
		ParamConfig<?> mapsToParam = findMappedParam(mapsToModel, f.getName(), mapsToPath);
		if(mapsToParam==null)
			throw new InvalidConfigException("No mapsTo param found for mapped param field: "+f.getName()+" in enclosing model:"+mConfig.getReferredClass()+" with mapsToPath: "+mapsToPath);
		
		
		final DefaultParamConfig<?> created;
		
		// handle transient
		if(mapsToPath.nature().isTransient()) {
			MapsTo.Path simulatedMapsToPath = MappedDefaultParamConfig.createNewImplicitMapping("", false);
			DefaultParamConfig<?> simulatedMappedParamDetached = createMappedParamDetached(mConfig, f, visitedModels, simulatedMapsToPath);
			
			created = new MappedDefaultTransientParamConfig<>(simulatedMappedParamDetached, f.getName(), mapsToModel, mapsToParam, mapsToPath);
			
		} else {
			created = new MappedDefaultParamConfig<>(f.getName(), mapsToModel, mapsToParam, mapsToPath);
		}
		
		return decorateParam(mConfig, f, created, visitedModels);
	}
	
	private DefaultParamConfig<?> createMappedParamDetached(ModelConfig<?> mConfig, Field mappedField, EntityConfigVisitor visitedModels, MapsTo.Path mapsToPath) {
		if(!isCollection(mappedField.getType())) {
			return createMappedParamDetachedNested(mConfig, mappedField, visitedModels, mapsToPath);
		} 
		
		// detached collection
		return createMappedParamDetachedCollection(mConfig, mappedField, visitedModels, mapsToPath);
	}
	
	/**
	 * For detached mode, simulate mapsToParam. 
	 * This would require creating an enclosing ModelConfig which encloses the mapsTo Param.
	 * 
	 * Enclosing ModelConfig's type (referredClass) is top level holder class.
	 * 
	 * MapsTo ParaConfig's type is same as MapsTo.Type on mappedParam's referredClass. 
	 * If mappedParam's referredClass doesn't have MapsTo.Type defined, then mappedParam's referredClass would be used for self-detached mode simulation.
	 */
	private DefaultParamConfig<?> createMappedParamDetachedNested(ModelConfig<?> mConfig, Field mappedField, EntityConfigVisitor visitedModels, MapsTo.Path mapsToPath) {
		// create mapsToParam's enclosing ModelConfig
		DefaultModelConfig<?> simulatedEnclosingModel = new DefaultModelConfig<>(SimulatedNestedParamEnclosingEntity.class);
		
		// create mapsToParam and attach to enclosing model
		DefaultParamConfig<?> simulatedMapsToParam = decorateParam(simulatedEnclosingModel, DefaultParamConfig.instantiate(simulatedEnclosingModel, MapsTo.DETACHED_SIMULATED_FIELD_NAME), visitedModels);
		simulatedEnclosingModel.templateParamConfigs().add(simulatedMapsToParam);
		
		// build mapsToParam's type
		MapsTo.Type mappedParamRefClassMapsToType = AnnotationUtils.findAnnotation(mappedField.getType(), MapsTo.Type.class);
		Class<?> mappedParamRefClassMapsToEntityClass = mappedParamRefClassMapsToType!=null ? mappedParamRefClassMapsToType.value() : mappedField.getType();
		
		ParamConfigType pType = buildParamType(simulatedEnclosingModel, simulatedMapsToParam, null, mappedParamRefClassMapsToEntityClass, visitedModels);
		simulatedMapsToParam.setType(pType);
		
		DefaultParamConfig<?> mappedParam = decorateParam(mConfig, mappedField, new MappedDefaultParamConfig<>(mappedField.getName(), simulatedEnclosingModel, simulatedMapsToParam, mapsToPath), visitedModels);
		return mappedParam;
	}
	
	public DefaultParamConfig<?> createMappedParamDetachedCollection(ModelConfig<?> mConfigOfMappedParam, Field mappedField, EntityConfigVisitor visitedModels, MapsTo.Path mapsToPath) {
		// create mapsToParam's enclosing ModelConfig
		DefaultModelConfig<?> simulatedEnclosingModel = new DefaultModelConfig<>(SimulatedCollectionParamEnclosingEntity.class);
		
		// create mapsToParam and attach to enclosing model
		DefaultParamConfig<?> simulatedMapsToParam = decorateParam(simulatedEnclosingModel, DefaultParamConfig.instantiate(simulatedEnclosingModel, MapsTo.DETACHED_SIMULATED_FIELD_NAME), visitedModels);
		simulatedEnclosingModel.templateParamConfigs().add(simulatedMapsToParam);
		
		// determine collection param generic element type
		final ParamConfigType.CollectionType colType = determineCollectionType(mappedField.getType());
		final Class<?> determinedMappedColElemType = GenericUtils.resolveGeneric(mConfigOfMappedParam.getReferredClass(), mappedField);
		
		MapsTo.Type mappedParamRefClassMapsToType = AnnotationUtils.findAnnotation(determinedMappedColElemType, MapsTo.Type.class);
		Class<?> mappedParamRefClassMapsToEntityClass = mappedParamRefClassMapsToType!=null ? mappedParamRefClassMapsToType.value() : determinedMappedColElemType;
		
		ParamConfigType pType = buildParamType(simulatedEnclosingModel, simulatedMapsToParam, colType, mappedParamRefClassMapsToEntityClass, visitedModels);
		simulatedMapsToParam.setType(pType);
		
		DefaultParamConfig<?> mappedParam = decorateParam(mConfigOfMappedParam, mappedField, new MappedDefaultParamConfig<>(mappedField.getName(), simulatedEnclosingModel, simulatedMapsToParam, mapsToPath), visitedModels);
		return mappedParam;
	}

	@Getter @Setter @Domain("_simulatedDetachedNested")
	public static class SimulatedNestedParamEnclosingEntity<E> {
		private E detachedParam; 
	}
	
	
	@Getter @Setter @Domain("_simulatedDetachedCollection")
	public static class SimulatedCollectionParamEnclosingEntity<E> {
		private List<E> detachedParam;
	}

	private <T, P> ParamConfig<P> createParamCollectionElemMappedAttached(ModelConfig<T> mConfig, MappedParamConfig<P, ?> pConfig, ModelConfig<List<P>> colModelConfig, EntityConfigVisitor visitedModels, Class<?> colElemClass, MapsTo.Path mapsToColParamPath) {
		// colParam is mapped as Attached, but parent enclosing Model is un-mapped :- throw Ex
		if(!mConfig.isMapped()) { 	
			throw new InvalidConfigException("Param: "+pConfig.getCode()+" has @MapsTo.Path "+mapsToColParamPath+" with resolved mode: "+MapsTo.Mode.MappedAttached
						+" Attached Mapped Param must have Model that is mapped, but found with no @MapsTo.Model mappings for: "+mConfig.getReferredClass());
		}
		
		//ModelConfig<?> mapsToEnclosingModel = visitedModels.get(mConfig.findIfMapped().getMapsTo().getReferredClass());
		//logit.debug(()->"[create.pColElem] [colParam is mapped] [elemClass same] [Attached] Found parent mapsToEnclosingModel: "+mapsToEnclosingModel+" from visitedMmodels using: "+mConfig.findIfMapped().getMapsTo().getReferredClass());
		
		return createParamCollectionElemMapped(/*mapsToEnclosingModel, */pConfig, colModelConfig, visitedModels, colElemClass, mapsToColParamPath);
	}
	
	private <T, P> ParamConfig<P> createParamCollectionElemMappedDetached(ModelConfig<T> mConfig, MappedParamConfig<P, ?> pConfig, ModelConfig<List<P>> colModelConfig, EntityConfigVisitor visitedModels, Class<?> colElemClass, MapsTo.Path mapsToColParamPath) {
		return createParamCollectionElemMapped(pConfig, colModelConfig, visitedModels, colElemClass, mapsToColParamPath);
	}
	
	
	private <T, P> ParamConfig<P> createParamCollectionElemMapped(MappedParamConfig<P, ?> pConfig, ModelConfig<List<P>> colModelConfig, EntityConfigVisitor visitedModels, Class<?> colElemClass, MapsTo.Path mapsToColParamPath) {
		//ParamConfig<?> mapsToColParamConfig = findMappedParam(mapsToEnclosingModel, pConfig.getCode(), mapsToColParamPath);
		ParamConfig<?> mapsToColParamConfig = pConfig.getMapsToConfig();
		logit.debug(()->"[create.pColElem] [colParam is mapped] [elemClass same] [Attached] Found mapsToColParamConfig for "+pConfig.getCode()+" with mapsToPath of colParam: "+mapsToColParamPath+" -> "+mapsToColParamConfig);
		
		@SuppressWarnings("unchecked")
		ParamConfig<P> mapsToColElemParamConfig = (ParamConfig<P>)mapsToColParamConfig.getType().findIfCollection().getElementConfig();

		
		// colParam is mapped: colElemModel is NOT explicitly mapped BUT colElemClass is NOT SAME as mappedElemClass :- throw Ex
		if(colElemClass!=mapsToColElemParamConfig.getReferredClass()) {
			
			// handle {index} scenario in MapsTo.Path
			//if(StringUtils.contains(mapsToColParamPath.value(), Constants.MARKER_COLLECTION_ELEM_INDEX.code)) {
			if(MapsTo.hasCollectionPath(mapsToColParamPath)) {
				String colElemPathAfterIndexMarker = mapsToColParamPath.colElemPath();//StringUtils.substringAfter(mapsToColParamPath.value(), Constants.MARKER_COLLECTION_ELEM_INDEX.code);
				ParamConfig<P> mapsToNestedColElemParamConfig = mapsToColElemParamConfig.findParamByPath(colElemPathAfterIndexMarker);
				
				return createParamCollectionElementInternal(colModelConfig, mapsToNestedColElemParamConfig, mapsToColParamPath, visitedModels, pConfig.getCode());
				
			} else {
			
				MapsTo.Type mapsToElemModel = AnnotationUtils.findAnnotation(colElemClass, MapsTo.Type.class);
				
				if(mapsToElemModel==null)
					throw new InvalidConfigException("Mapped Elem Class is not same as MapsTo Elem Class. Must be same or an explicit MapsTo.Model mapping is required. "
							//+ " For EnclosingModel: "+mapsToEnclosingModel.getReferredClass()
							+" param: "+pConfig.getCode()
							+ " Expected elemClass: "+colElemClass+" but found mapsToElemClass: "+mapsToColElemParamConfig.getReferredClass());
			}
		}
		
		return createParamCollectionElementInternal(colModelConfig, mapsToColElemParamConfig, mapsToColParamPath, visitedModels, pConfig.getCode());
	}
	
	public <T, P> ParamConfig<P> createParamCollectionElement(ModelConfig<T> mConfig, ParamConfig<P> pConfig, ModelConfig<List<P>> colModelConfig, EntityConfigVisitor visitedModels, Class<?> colElemClass) {
		logit.trace(()->"[create.pColElem] starting to process colElemClass: "+colElemClass+" with pConfig :"+pConfig.getCode());
		
		MapsTo.Path mapsToColParamPath = pConfig.isMapped() ? pConfig.findIfMapped().getPath() : null;
		MapsTo.Mode mapsToColParamMode = MapsTo.getMode(mapsToColParamPath);
		
		logit.debug(()->"[create.pColElem] mapsToColParam: "+mapsToColParamPath);
		logit.debug(()->"[create.pColElem] mapsToModeColParam: "+mapsToColParamMode);
		logit.debug(()->"[create.pColElem] colParamCode: "+pConfig.getCode());
		
		if(mapsToColParamMode==MapsTo.Mode.UnMapped) { 
			ParamConfig<P> pCoreElemConfig = createParamCollectionElementInternal(colModelConfig, null, null, visitedModels, pConfig.getCode());
		
			logit.trace(()->"[create.pColElem] [colParam is UnMapped] returning core pColElem Config as colElem is UnMapped.");
			return pCoreElemConfig;
			
		} else if(mapsToColParamMode==MapsTo.Mode.MappedAttached) {
			ParamConfig<P> pMappedAttachedElemConfig = createParamCollectionElemMappedAttached(mConfig, pConfig.findIfMapped(), colModelConfig, visitedModels, colElemClass, mapsToColParamPath);
			return pMappedAttachedElemConfig;
			
		} else if(mapsToColParamMode==MapsTo.Mode.MappedDetached) {
			ParamConfig<P> pMappedDetachedElemConfig = createParamCollectionElemMappedDetached(mConfig, pConfig.findIfMapped(), colModelConfig, visitedModels, colElemClass, mapsToColParamPath);
			return pMappedDetachedElemConfig;
			
		} else {
			throw new UnsupportedScenarioException("Param: "+pConfig.getCode()+" has @MapsTo.Path "+mapsToColParamPath+" with unknown mode: "+mapsToColParamMode
					+" in enclosing model: "+mConfig.getReferredClass());
		}
	}
	
	private <P> ParamConfig<P> createParamCollectionElementInternal(ModelConfig<List<P>> colModelConfig, ParamConfig<P> mapsToColElemParamConfig, MapsTo.Path mapsToColParamPath, EntityConfigVisitor visitedModels, String colParamCode) {
		final String collectionElemPath = createCollectionElementPath(colParamCode);
		
		final ParamConfig<P> created;
		if(colModelConfig.isMapped()) {
			final MapsTo.Path mapsToColElemParamPathAnnotation = 
					(mapsToColParamPath==null) 
						? null : MappedDefaultParamConfig.createNewImplicitMapping(collectionElemPath, mapsToColParamPath.linked(), mapsToColParamPath.colElemPath(), mapsToColParamPath.detachedState());
			
			created = new MappedDefaultParamConfig<>(collectionElemPath, colModelConfig, mapsToColElemParamConfig, mapsToColElemParamPathAnnotation);

		} else if(mapsToColElemParamConfig==null) {
			created = decorateParam(colModelConfig, DefaultParamConfig.instantiate(colModelConfig, collectionElemPath), visitedModels);
			
		} else {
			created = mapsToColElemParamConfig;
			
		}
		return created;
	}
	
	public static String createCollectionElementPath(String collectionPath) {
		return new StringBuilder()
				//.append(collectionPath)
				//.append(Constants.SEPARATOR_URI.code)
				.append(Constants.MARKER_COLLECTION_ELEM_INDEX.code)
				.toString();
	}
	
	private <P> DefaultParamConfig<P> decorateParam(ModelConfig<?> mConfig, Field f, DefaultParamConfig<P> created, EntityConfigVisitor visitedModels) {
		created.setUiNatures(annotationConfigHandler.handle(f, ViewParamBehavior.class));
		created.setUiStyles(annotationConfigHandler.handleSingle(f, ViewStyle.class));
		created.setExecutionConfigs(new ArrayList<>(AnnotatedElementUtils.findMergedRepeatableAnnotations(f, Execution.Config.class)));
		
		
		if(AnnotatedElementUtils.isAnnotated(f, Converters.class)) {
			Converters convertersAnnotation = AnnotationUtils.getAnnotation(f, Converters.class);
			
			List<ParamConverter> converters = new ArrayList<>();
			
			Arrays.asList(convertersAnnotation.converters())
						.forEach((converterClass)->converters.add(beanResolver.get(converterClass)));
			
			created.setConverters(converters);
		}

		if(AnnotatedElementUtils.isAnnotated(f, Model.Param.Values.class)) {
			Model.Param.Values aVal = AnnotationUtils.getAnnotation(f, Model.Param.Values.class);
			
			//Model.Param.Values.Source srcValues = ClassLoadUtils.newInstance(aVal.value());
			//List<ParamValue> values = srcValues.getValues(created.getCode());
			created.setValues(aVal);
			//created.setValues(values);
		}
		
		if(AnnotatedElementUtils.isAnnotated(f, AssociatedEntity.class)) {
			AssociatedEntity[] associatedEntityAnnotationArr = f.getAnnotationsByType(AssociatedEntity.class);
			created.setAssociatedEntities(Arrays.asList(associatedEntityAnnotationArr));
		}
		
		List<AnnotationConfig> vConfig = annotationConfigHandler.handle(f, Constraint.class);
		created.setValidations(vConfig);

		EventHandlerConfig eventConfig = eventHandlerConfigFactory.build(f);
		created.setEventHandlerConfig(eventConfig);
		
		return decorateParam(mConfig, created, visitedModels);
	}
	
	protected <P> DefaultParamConfig<P> decorateParam(ModelConfig<?> mConfig, DefaultParamConfig<P> created, EntityConfigVisitor visitedModels) {
		return created;
	}
	
	//TODO Implement mapped param lookup given via explicit path reference
	private <T> ParamConfig<?> findMappedParam(ModelConfig<T> mapsToModel, String fieldNm, MapsTo.Path mapsTo) {
		if(mapsTo!=null && !StringUtils.isEmpty(mapsTo.value())) { //: if @Path has an explicit value
			String path = mapsTo.value();
			ParamConfig<?> mapsToParam = mapsToModel.findParamByPath(path);
			return mapsToParam;
			//throw new UnsupportedOperationException("TODO: Explicit Mapped Path lookup is not yet implemented. Found: "+ mapsTo.value());
		}
		
		ParamConfig<?> mappedToParam = mapsToModel.templateParamConfigs().find(fieldNm);
		return mappedToParam;
	}
	
	protected <P> ParamConfigType.NestedCollection<P> createNestedCollectionType(ParamConfigType.CollectionType colType) {
		Class<?> referredClass = ArrayList.class;
		String name = ClassUtils.getShortName(referredClass);
		
		ParamConfigType.NestedCollection<P> nestedColType = new ParamConfigType.NestedCollection<>(name, referredClass, colType);
		return nestedColType;
	}
	
	protected <T, P> ParamConfigType createParamType(boolean isArray, Class<P> determinedType, ModelConfig<?> mConfig, EntityConfigVisitor visitedModels) {
		final ParamConfigType pType;
		if(isPrimitive(determinedType)) {	//Primitives bare or with wrapper & String
			String name = ClassUtils.getShortNameAsProperty(ClassUtils.resolvePrimitiveIfNecessary(determinedType));
			pType = new ParamConfigType.Field(isArray, name, determinedType);

		} else if(lookUpTypeClassMapping(determinedType)!=null) { //custom mapping overrides
			String name = lookUpTypeClassMapping(determinedType);
			pType = new ParamConfigType.Field(isArray, name, determinedType);
			
		} else if(AnnotationUtils.findAnnotation(determinedType, Model.class)!=null) { 
			String name = ClassUtils.getShortName(determinedType);
			pType = createParamTypeNested(name, determinedType, mConfig, visitedModels);
			
		} else { //All others: Treat as field type instead of complex object that requires config traversal
			pType = new ParamConfigType.Field(isArray, ClassUtils.getShortName(determinedType), determinedType);
			
		}
		return pType;
	}
	
	protected <P> ParamConfigType.Nested<P> createParamTypeNested(String typeName, Class<P> determinedType, ModelConfig<?> mConfig, EntityConfigVisitor visitedModels) {
		
		final ParamConfigType.Nested<P> nestedParamType = new ParamConfigType.Nested<>(typeName, determinedType);
		
		final ModelConfig<P> nmConfig; 
		if(mConfig.getReferredClass()==determinedType) { //nested reference to itself
			nmConfig = (ModelConfig<P>)mConfig;
			
		} else if(visitedModels.contains(determinedType)) { //any nested model in hierarchy pointing back to any of its parents
			nmConfig = (ModelConfig<P>)visitedModels.get(determinedType);
			
		} else {
			nmConfig = buildModel(determinedType, visitedModels);
		}
		
		nestedParamType.setModelConfig(nmConfig);
		return nestedParamType;
	}
	
	protected boolean isCollection(Class<?> clazz) {
		return determineCollectionType(clazz) != null;
	}
	
	protected ParamConfigType.CollectionType determineCollectionType(Class<?> clazz) {
		if(clazz.isArray())	//Array
			return ParamConfigType.CollectionType.array;
		
		else if(Collection.class.isAssignableFrom(clazz))  //Collection
			return ParamConfigType.CollectionType.list;
			
		else if(Page.class.isAssignableFrom(clazz))	//Page
			return ParamConfigType.CollectionType.page;
		
		return null;
	}
	
	abstract protected String lookUpTypeClassMapping(Class<?> clazz);
}
. <,,,,,,,,,,,,,,,,,,,,
----

@Getter
public class DefaultEntityConfigBuilder extends AbstractEntityConfigBuilder implements EntityConfigBuilder {

	private final Map<String, String> typeClassMappings;
	
	public DefaultEntityConfigBuilder(BeanResolverStrategy beanResolver, Map<String, String> typeClassMappings) {
		super(beanResolver);
		
		this.typeClassMappings = typeClassMappings;
	}
	

	/* (non-Javadoc)
	 * @see com.antheminc.oss.nimbus.domain.model.config.builder.IEntityConfigBuilder#load(java.lang.Class, com.antheminc.oss.nimbus.domain.model.config.builder.EntityConfigVisitor)
	 */
	@Override
	public <T> ModelConfig<T> load(Class<T> clazz, EntityConfigVisitor visitedModels) {
		ModelConfig<T> mConfig = buildModel(clazz, visitedModels);
		return mConfig;
	}
	
	/* (non-Javadoc)
	 * @see com.antheminc.oss.nimbus.domain.model.config.builder.IEntityConfigBuilder#buildModel(java.lang.Class, com.antheminc.oss.nimbus.domain.model.config.builder.EntityConfigVisitor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> ModelConfig<T> buildModel(Class<T> clazz, EntityConfigVisitor visitedModels) {
		logit.trace(()->"building model for class: "+clazz);
		
		// skip if already built
		if(visitedModels.contains(clazz)) 
			return (ModelConfig<T>)visitedModels.get(clazz);
		
		
		DefaultModelConfig<T> mConfig = createModel(clazz, visitedModels);
		visitedModels.set(clazz, mConfig);

		
		//look if the model is marked with MapsTo
		if(mConfig.isMapped()) {
			
			//ensure mapped class config is already loaded
			buildModel(mConfig.findIfMapped().getMapsToConfig().getReferredClass(), visitedModels);
		}
		
		List<Field> fields = FieldUtils.getAllFieldsList(clazz);
		if(fields==null) return mConfig;
		
		fields.stream()
			.filter((f)-> !f.isSynthetic())
			.forEach((f)->{
				ParamConfig<?> p = buildParam(mConfig, f, visitedModels);
				mConfig.templateParamConfigs().add(p);
				
				if(AnnotatedElementUtils.isAnnotated(f, Id.class)) {
					// default id
					mConfig.setIdParamConfig(p);
					
				} else if(AnnotatedElementUtils.isAnnotated(f, Version.class)) {
					// default version
					mConfig.setVersionParamConfig(p);
				}				
			});
		
		if(Repo.Database.isPersistable(mConfig.getRepo()) && mConfig.getIdParamConfig()==null) {
			throw new InvalidConfigException("Persistable Entity: "+mConfig.getReferredClass()+" must be configured with @Id param which has Repo: "+mConfig.getRepo());
		}
		
		return mConfig;
	}

	/* (non-Javadoc)
	 * @see com.antheminc.oss.nimbus.domain.model.config.builder.IEntityConfigBuilder#buildParam(com.antheminc.oss.nimbus.domain.model.config.ModelConfig, java.lang.reflect.Field, com.antheminc.oss.nimbus.domain.model.config.builder.EntityConfigVisitor)
	 */
	@Override
	public <T> ParamConfig<?> buildParam(ModelConfig<T> mConfig, Field f, EntityConfigVisitor visitedModels) {
		
		logit.trace(()->"Building Param for config class: "+mConfig.getReferredClass()+ " field : "+f.getName());
		
		/* handle ignore field */
		if(AnnotatedElementUtils.isAnnotated(f, ConfigNature.Ignore.class)) return null;
		if("serialVersionUID".equals(f.getName())) return null;

		final DefaultParamConfig<?> pConfig = createParam(mConfig, f, visitedModels);
		
		// handle type
		ParamConfigType type = buildParamType(mConfig, pConfig, f, visitedModels);
		pConfig.setType(type);
		
		// trigger event
		pConfig.onCreateEvent();
		
		return pConfig;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T, P> ParamConfigType buildParamType(ModelConfig<T> mConfig, ParamConfig<P> pConfig, Field f, EntityConfigVisitor visitedModels) {
		Class<P> determinedType = (Class<P>)GenericUtils.resolveGeneric(mConfig.getReferredClass(), f);
		
		ParamConfigType.CollectionType colType = determineCollectionType(f.getType());	
		
		return buildParamType(mConfig, pConfig, colType, determinedType, visitedModels);
	}
	
	@Override
	protected <T, P> ParamConfigType buildParamType(ModelConfig<T> mConfig, ParamConfig<P> pConfig, ParamConfigType.CollectionType colType, Class<?> pDirectOrColElemType, /*MapsTo.Path mapsToPath, */EntityConfigVisitor visitedModels) {
		if(ParamConfigType.CollectionType.array==colType && isPrimitive(pDirectOrColElemType)) { // handle primitive array first
			ParamConfigType type = createParamType(true, pDirectOrColElemType, mConfig, visitedModels);
			return type;
			
		} else if(colType!=null) { //handle collections second
			//create nested collection type
			ParamConfigType.NestedCollection<P> colModelType = createNestedCollectionType(colType);
			
			//create collection config model
			DefaultModelConfig<List<P>> colModelConfig = createCollectionModel(colModelType.getReferredClass(), pConfig);
			colModelType.setModelConfig(colModelConfig);
			 
			//create collection element param config
			DefaultParamConfig<P> colElemParamConfig = (DefaultParamConfig<P>)createParamCollectionElement(mConfig, /*mapsToPath, */pConfig, colModelConfig, visitedModels, pDirectOrColElemType);
			colModelType.setElementConfig(colElemParamConfig);

			//create collection element type (and element model config)
			ParamConfigType colElemType = createParamType(false, pDirectOrColElemType, colModelConfig, visitedModels);
			colElemParamConfig.setType(colElemType);
			
			return colModelType;
			
		} else {
			ParamConfigType type = createParamType(false, pDirectOrColElemType, mConfig, visitedModels);
			return type;
		}
	}
	
	@Override
	public String lookUpTypeClassMapping(Class<?> clazz) {
		if(getTypeClassMappings()==null) return null;
		
		String mapping = getTypeClassMappings().get(clazz.getName());
		return mapping;
	}
	

}
---
@Getter
public class EventHandlerConfigFactory {

	private final BeanResolverStrategy beanResolver;
	
	private AnnotationConfigHandler annotationConfigHandler;
	
	public EventHandlerConfigFactory(BeanResolverStrategy beanResolver) {
		this.beanResolver = beanResolver;
		this.annotationConfigHandler = beanResolver.find(AnnotationConfigHandler.class);
	}
	
	@SuppressWarnings("unchecked")
	public EventHandlerConfig build(AnnotatedElement aElem) {
		final DefaultEventHandlerConfig eventConfig = new DefaultEventHandlerConfig();
		
		// onParamCreate
		buildInternal(aElem, OnParamCreate.class, OnParamCreateHandler.class, (a,h)->eventConfig.add(a, h));
		
		// onStateLoad
		buildInternal(aElem, OnStateLoad.class, OnStateLoadHandler.class, (a,h)->eventConfig.add(a, h));
		
		// onStateChange
		buildInternal(aElem, OnStateChange.class, OnStateChangeHandler.class, (a,h)->eventConfig.add(a, h));

		
		return eventConfig.isEmpty() ? null : eventConfig;
	}

	
	protected <T> void buildInternal(AnnotatedElement aElem, Class<? extends Annotation> configuredAnnotationType, Class<T> handlerType, BiConsumer<Annotation, T> addHandlerCb) {
		List<Annotation> annotations = annotationConfigHandler.handleRepeatable(aElem, configuredAnnotationType);
		if(!CollectionUtils.isEmpty(annotations)) { 
			
			annotations.stream()
				.forEach(a->{
					T handler = getBeanResolver().get(handlerType, a.annotationType());
					addHandlerCb.accept(a, handler);
				});
		}
	}
	
}
--

@RequiredArgsConstructor
public class EntityConfigVisitor {

	@Getter(AccessLevel.PRIVATE)
	private final Map<Class<?>, ModelConfig<?>> visitedModels = new HashMap<>();
	
	@Getter(AccessLevel.PRIVATE)
	private final Map<String, ModelConfig<?>> visitedModelsByAlias = new HashMap<>();
	
	public boolean contains(Class<?> clazz) {
		if(getVisitedModels()==null) return false;
		
		return getVisitedModels().containsKey(clazz);
	}
	
	public boolean contains(String alias) {
		if(getVisitedModelsByAlias()==null) return false;
		
		return getVisitedModelsByAlias().containsKey(alias);
	}
	
	public void set(Class<?> clazz, ModelConfig<?> mConfig) {
		getVisitedModels().put(clazz, mConfig);
		
		if(mConfig.getAlias()!=null)
			getVisitedModelsByAlias().put(mConfig.getAlias(), mConfig);
	}
	
	
	public ModelConfig<?> get(Class<?> clazz) {
		return getVisitedModels().get(clazz);
	}
	
	public ModelConfig<?> get(String alias) {
		return getVisitedModelsByAlias().get(alias);
	}
	
	public static List<String> determineRootPackages(List<String> basePackages) {
		if(CollectionUtils.isEmpty(basePackages)) return Collections.emptyList();
		
		ArrayList<String> rootPackages = new ArrayList<>(basePackages);
		for(String check : basePackages) {
			
			String remove = null;
			for(String existing : rootPackages) {
				if(!StringUtils.equals(existing, check) && StringUtils.startsWith(existing, check)) {
					remove = existing;
					break;
				} 
			}
			
			if(remove!=null) {
				rootPackages.remove(remove);
			}
		}
		
		return rootPackages;
	}

}
--
public class LabelConfigEventHandler extends AbstractConfigEventHandler<Label> implements OnParamCreateHandler<Label> {

	@Override
	public void handle(Label configuredAnnotation, ParamConfig<?> param) {
		if(configuredAnnotation==null)
			return;
		
		DefaultParamConfig<?> paramConfig = castOrEx(DefaultParamConfig.class, param);
		
		Optional.ofNullable(paramConfig.getLabelConfigs()).orElseGet(()->{
			paramConfig.setLabelConfigs(new ArrayList<>());
			return paramConfig.getLabelConfigs();
		});
		
		validateAndAdd(paramConfig, convert(configuredAnnotation));
	}
	
	protected void validateAndAdd(ParamConfig<?> paramConfig, LabelConfig toAdd) {
		// duplicate check
		paramConfig.getLabelConfigs().stream()
			.filter(lc->lc.getLocale().equals(toAdd.getLocale()))
			.forEach(lc->{
				throw new InvalidConfigException("Label must have unique entries by locale,"
						+ " found multiple entries in ParamConfig: "+paramConfig
						+ " with repeating locale for LabelConfig: "+ toAdd);	
			});
		
		// at-least one of Label text or helpText must be present
		if(StringUtils.isEmpty(toAdd.getText()) && StringUtils.isEmpty(toAdd.getHelpText()))
			throw new InvalidConfigException("Label must have non empty values for at least label text value or help text,"
					+ " found none for \"" + paramConfig.getCode() + "\" in ParamConfig: " + paramConfig
					+ " with LabelConfig: " + toAdd);
		
		paramConfig.getLabelConfigs().add(toAdd);
	}
	
	protected LabelConfig convert(Label label) {
		LabelConfig config = new LabelConfig();
		
		Locale locale = Content.getLocale(label);
		
		config.setLocale(StringUtils.trimToNull(locale.toLanguageTag()));
		config.setText(StringUtils.isWhitespace(label.value()) ? label.value() : StringUtils.trimToNull(label.value()));	
		config.setHelpText(StringUtils.trimToNull(label.helpText()));
		
		return config;
	}
}
