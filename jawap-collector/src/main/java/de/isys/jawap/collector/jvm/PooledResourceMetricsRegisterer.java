package de.isys.jawap.collector.jvm;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.List;

public class PooledResourceMetricsRegisterer {

	public static void registerPooledResources(List<? extends PooledResource> pooledResources, MetricRegistry registry) {
		for (PooledResource pooledResource : pooledResources) {
			registerPooledResource(pooledResource, registry);
		}
	}

	public static void registerPooledResource(final PooledResource pooledResource, MetricRegistry registry) {
		String name = pooledResource.getName();
		registry.register(name + ".active", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return pooledResource.getThreadPoolNumActiveThreads();
			}
		});
		registry.register(name + ".count", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return pooledResource.getThreadPoolSize();
			}
		});
		registry.register(name + ".max", new Gauge<Integer>() {
			@Override
			public Integer getValue() {
				return pooledResource.getMaxPoolSize();
			}
		});
		if (pooledResource.getThreadPoolNumTasksPending() != null) {
			registry.register(name + ".queued", new Gauge<Integer>() {
				@Override
				public Integer getValue() {
					return pooledResource.getThreadPoolNumTasksPending();
				}
			});
		}
	}
}