package desafio.urban_potato.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TraceFilter  implements Filter {
	
	private static final String TRACE_ID_HEADER_NAME = "traceparent";
	private static final String DEFAULT = "00";
    private final Tracer tracer;

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!httpResponse.getHeaderNames().contains(TRACE_ID_HEADER_NAME)) {
            if(Optional.of(tracer).map(Tracer::currentTraceContext).map(CurrentTraceContext::context).isEmpty()) {
                chain.doFilter(request, response);
                return;
            }
            var context = tracer.currentTraceContext().context();
            var traceId = context.traceId();
            var parentId = context.spanId();
            var traceFlags = "00";
            
            if(Boolean.TRUE.equals(context.sampled())) {
            	traceFlags = "01";
            }
            
            var traceparent = DEFAULT + "-" + traceId + "-" + parentId + "-" + traceFlags;
            httpResponse.setHeader(TRACE_ID_HEADER_NAME, traceparent);
        }
        chain.doFilter(request, response);
	}

}
