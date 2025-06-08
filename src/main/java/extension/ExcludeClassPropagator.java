package extension;

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.propagation.TextMapSetter;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ExcludeClassPropagator implements TextMapPropagator {

    private static final Logger logger = Logger.getLogger(ExcludeClassPropagator.class.getName());
    private static final TextMapPropagator basePropagator = W3CBaggagePropagator.getInstance();
    private static final ExcludeClassPropagator INSTANCE = new ExcludeClassPropagator();
    private static final HashSet<String> toExcludeInjection = new HashSet<String>();
    private static final String excludeInjectionConfigKey =
            "otel.instrumentation.propagators.tracecontext-exclude.injection";

    private ExcludeClassPropagator() {}

    /** Singleton instance of the {@link ExcludeClassPropagator}. */
    public static ExcludeClassPropagator getInstance(ConfigProperties configProperties) {
        logger.info("Parsing values from: " + excludeInjectionConfigKey);
        List<String> classNames = configProperties.getList(excludeInjectionConfigKey);
        if (toExcludeInjection.isEmpty() && !classNames.isEmpty()) {
            toExcludeInjection.addAll(classNames);
            logger.info("\tParsed to be excluded from context injection: " + classNames);
        }
        return INSTANCE;
    }

    @Override
    public Collection<String> fields() {
        return basePropagator.fields();
    }

    @Override
    public <C> void inject(Context context, C carrier, TextMapSetter<C> setter) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            String elementClassName = element.getClassName();
            if (toExcludeInjection.contains(elementClassName)) {
                logger.fine("Detected: [ " + elementClassName + " ]. Skipping context injection.");
                return;
            }
        }
        StackTraceElement callerElement = stackTraceElements[stackTraceElements.length - 1];
        logger.fine(
                "Injecting context for: "
                        + callerElement.getClassName()
                        + "."
                        + callerElement.getMethodName());
        basePropagator.inject(context, carrier, setter);
    }

    @Override
    public <C> Context extract(Context context, C carrier, TextMapGetter<C> getter) {
        return basePropagator.extract(context, carrier, getter);
    }
}
