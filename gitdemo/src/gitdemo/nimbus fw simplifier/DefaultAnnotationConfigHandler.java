NOTE: DefaultAnnotationConfigHandler is in down check it and learn

----
/**
 * <p>Base implementation for defining how annotation attributes and their values should be
 * parsed and mapped.</p>
 * 
 * <p>For example, to ensure that all values of a particular attribute in <tt>@Domain</tt> are
 * prefixed with some value (e.g. <i>prefix</i>), an implementation of 
 * <tt>AnnotationAttributeHandler</tt> (e.g. <tt>AnnotationAttributeHandlerImpl</tt>) could be 
 * defined to ensure the following scenario:</p>
 * 
 * <p>Executing AnnotationAttributeHandlerImpl.generateFrom(...) over an object annotated with <tt>@Domain</tt>:
 * <pre>
 * {@literal @}Domain(value = "home")
 * public class SomeElement {}
 * 
 * AnnotationAttributeHandlerImpl.generateFrom(...).get("value")
 *   <b>returns:</b> "prefix-home"
 * </pre>
 * 
 * <p>Implementing classes should ensure that the returned-value is non-null.</p>
 
 * @see com.antheminc.oss.nimbus.domain.config.builder.attributes.DefaultAnnotationAttributeHandler
 */
public interface AnnotationAttributeHandler {

	/**
	 * Generates a mapping of annotation attributes organized with the <tt>annotation</tt> 
	 * name as the map's <i>key</i> and the <tt>annotation</tt> attribute's values as the map's 
	 * <i>value</i>.
	 * 
	 * @param annotatedElement The element which is annotated with <tt>annotation</tt>
	 * @param annotation The <tt>Annotation</tt> from which to generate the key/value mappings for. 
	 * @return A mapping of <tt>annotation</t>>'s attribute names/values as the map's key/value, 
	 * respectively.
	 */
	Map<String, Object> generateFrom(AnnotatedElement annotatedElement, Annotation annotation);
}


---
/**
 * <p>Default Implementation of <tt>AnnotationAttributeHandler</tt> that simply returns a key/value 
 * map of the annotation attributes defined in the <tt>annotation</tt> element with no
 * additional changes made.
 * @see com.antheminc.oss.nimbus.domain.config.builder.AnnotationAttributeHandler
 *
 */
public class DefaultAnnotationAttributeHandler implements AnnotationAttributeHandler {

	/*
	 * (non-Javadoc)
	 * @see com.anthem.oss.nimbus.core.domain.config.builder.AnnotationAttributeHandler#generateFrom(java.lang.reflect.AnnotatedElement, java.lang.annotation.Annotation)
	 */
	@Override
	public Map<String, Object> generateFrom(AnnotatedElement annotatedElement, Annotation annotation) {
		final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotatedElement, annotation, false, true);
		final HashMap<String, Object> map = new HashMap<>();
		
		for(final Entry<String, Object> entry: annotationAttributes.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

}
--
/**
 * Implementation of <tt>AnnotationAttributeHandler</tt> that ensures all JSR <tt>Constraint</tt>
 * attributes are filtered as needed.
 */
public class ConstraintAnnotationAttributeHandler implements AnnotationAttributeHandler {

	public static final String ATTRIBUTE_MESSAGE_NAME = "message";
	public static final String ATTRIBUTE_MESSAGE_VALUE_DEFAULT = "";
	public static final String JSR_DEFAULT_MESSAGE_REGEX = "\\{javax.validation.constraints.(.*).message\\}";
	
	/*
	 * @see com.anthem.oss.nimbus.core.domain.config.builder.AnnotationAttributeHandler#generateFrom(java.lang.reflect.AnnotatedElement, java.lang.annotation.Annotation)
	 */
	@Override
	public Map<String, Object> generateFrom(AnnotatedElement annotatedElement, Annotation annotation) {
		final AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotatedElement, annotation, false, true);
		final HashMap<String, Object> map = new HashMap<>();
		
		for(final Entry<String, Object> entry: annotationAttributes.entrySet()) {
			// Prefer empty-string over JSR defaults.
			if (entry.getKey().equals(ATTRIBUTE_MESSAGE_NAME) && ((String) entry.getValue()).matches(JSR_DEFAULT_MESSAGE_REGEX)) {
				map.put(entry.getKey(), ATTRIBUTE_MESSAGE_VALUE_DEFAULT);
			} else {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

}
--



---
public interface AnnotationConfigHandler {
	
	AnnotationConfig handleSingle(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType);

	List<AnnotationConfig> handle(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType);
	
	List<Annotation> handleRepeatable(AnnotatedElement aElem, Class<? extends Annotation> repeatableMetaAnnotationType);

}
---





@RequiredArgsConstructor
public class DefaultAnnotationConfigHandler implements AnnotationConfigHandler {
	
	/**
	 * Default Handler for generating attribute values
	 */
	private final AnnotationAttributeHandler defaultAttributeHandler;
	
	private final Map<Class<? extends Annotation>, AnnotationAttributeHandler> attributeHandlers;
	
	private final PropertyResolver propertyResolver;
	
	@Override
	public AnnotationConfig handleSingle(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType) {
		AnnotationConfig ac = handleSingleInternal(aElem, metaAnnotationType);
		
		postProcess(ac);
		return ac;
	}
	
	protected AnnotationConfig handleSingleInternal(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType) {
		final List<AnnotationConfig> aConfigs = handle(aElem, metaAnnotationType);
		if(CollectionUtils.isEmpty(aConfigs)) {
			return null;
		}
		
		if(aConfigs.size() != 1) {
			throw new InvalidConfigException(String.format("Found more than one element of config: %s. Expecting only one config element", aConfigs));
		}
		
		return aConfigs.get(0);
	}

	@Override
	public List<AnnotationConfig> handle(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType) {
		List<AnnotationConfig> acList = handleInternal(aElem, metaAnnotationType);
		
		postProcess(acList);
		return acList;
	}
	
	protected List<AnnotationConfig> handleInternal(AnnotatedElement aElem, Class<? extends Annotation> metaAnnotationType) {
		final boolean hasIt = AnnotatedElementUtils.hasMetaAnnotationTypes(aElem, metaAnnotationType);
		if (!hasIt) {
			return null;
		}

		final List<AnnotationConfig> aConfigs = new ArrayList<>();
		
		final Annotation arr[] = aElem.getAnnotations();
		for(final Annotation a : arr) {
			final Set<String> metaTypes = AnnotatedElementUtils.getMetaAnnotationTypes(aElem, a.annotationType());
			
			if (metaTypes != null && metaTypes.contains(metaAnnotationType.getName())) {
				final AnnotationConfig ac = new AnnotationConfig();
				ac.setAnnotation(a);
				ac.setName(ClassUtils.getShortName(a.annotationType()));
				ac.setAttributes(getAttributesHandlerForType(metaAnnotationType).generateFrom(aElem, a));
				aConfigs.add(ac);
			}
		}
		
		// TODO null may be unreachable
		return CollectionUtils.isEmpty(aConfigs) ? null : aConfigs;
	}
	
	@Override
	public List<Annotation> handleRepeatable(AnnotatedElement aElem, Class<? extends Annotation> repeatableMetaAnnotationType) {
		final List<Annotation> annotations = new ArrayList<>();
		
		final Annotation arr[] = aElem.getAnnotations();

		for(final Annotation currDeclaredAnnotation : arr) {
			final Set<String> metaTypesOnCurrDeclaredAnnotation = AnnotatedElementUtils.getMetaAnnotationTypes(aElem, currDeclaredAnnotation.annotationType());
			
			// handle repeatable container
			if(metaTypesOnCurrDeclaredAnnotation!=null && metaTypesOnCurrDeclaredAnnotation.contains(RepeatContainer.class.getName())) {
				
				// get repeat container meta annotation and use declared repeatable annotaion
				RepeatContainer repeatContainerMetaAnnotation = AnnotationUtils.getAnnotation(currDeclaredAnnotation, RepeatContainer.class);
				Class<? extends Annotation> repeatableAnnotationDeclared = repeatContainerMetaAnnotation.value();
				
				// check that the declared annotation has passed in meta annotation type
				
				boolean hasRepeatable = AnnotatedElementUtils.hasAnnotation(repeatableAnnotationDeclared, repeatableMetaAnnotationType);
				if(hasRepeatable) {
					Object value = AnnotationUtils.getValue(currDeclaredAnnotation);
					if(value==null || !value.getClass().isArray())
						throw new InvalidConfigException("Repeatable container annotation is expected to follow convention: '{RepeableAnnotationType}[] value();' but not found in: "+currDeclaredAnnotation);
					
					Annotation[] annArr = (Annotation[])value;
					annotations.addAll(Arrays.asList(annArr));
				}
			}
			
			// handle non-repeating meta annotation
			if(metaTypesOnCurrDeclaredAnnotation!=null && metaTypesOnCurrDeclaredAnnotation.contains(repeatableMetaAnnotationType.getName())) {
				annotations.add(currDeclaredAnnotation);
			}
		}
		
		return annotations;
	}
	
	protected void postProcess(List<AnnotationConfig> acList) {
		Optional.ofNullable(acList)
			.ifPresent(list->{
				list.stream()
					.forEach(this::postProcess);
			});
		;
	}
	
	protected void postProcess(AnnotationConfig ac) {
		if(ac==null)
			return;
		
		resolvePropertyCb(ac::getValue, ac::setValue);
		
		Optional.ofNullable(ac.getAttributes())
			.map(Map::keySet)
			.ifPresent(set->{
				set.stream()
					.forEach(k->{
						
						Optional.ofNullable(ac.getAttributes().get(k))
							.filter(String.class::isInstance)
							.map(String.class::cast)
								.ifPresent(expr-> resolvePropertyCb(()->expr, v->ac.getAttributes().put(k, v)));
							;
					});
			});
	}
	
	private void resolvePropertyCb(Supplier<String> getter, Consumer<String> setter) {
		Optional.ofNullable(getter.get())
			.map(propertyResolver::resolveRequiredPlaceholders)
			.ifPresent(setter::accept);
	}

	/**
	 * If an <tt>AnnotationAttributeHandler</tt> is registered for the provided type of
	 * <tt>metaAnnotationType</tt> it will be returned.
	 * 
	 * Otherwise this instance's <tt>defaultAttributeHandler</tt> will be returned.
	 * 
	 * @param metaAnnotationType
	 * @see com.antheminc.oss.nimbus.domain.config.builder.attributes.DefaultAnnotationAttributeHandler
	 * @return
	 */
	private AnnotationAttributeHandler getAttributesHandlerForType(Class<? extends Annotation> metaAnnotationType) {
		return Optional.ofNullable(attributeHandlers.get(metaAnnotationType)).orElse(defaultAttributeHandler);
	}
}

