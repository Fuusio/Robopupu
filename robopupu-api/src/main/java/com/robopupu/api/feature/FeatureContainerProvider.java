package com.robopupu.api.feature;

import java.util.List;

/**
 * {@link FeatureContainerProvider} in an interface for an {@code Activity} that provides one
 * of more {@link FeatureContainer}s. Typically the {@code Activity} itself is a {@link FeatureContainer}.
 */
public interface FeatureContainerProvider {

    List<FeatureContainer> getFeatureContainers();
}
