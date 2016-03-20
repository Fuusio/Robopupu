package com.robopupu.api.feature;

import android.app.Activity;

import java.util.List;

/**
 * {@link FeatureContainerActivity} in an interface for an {@link Activity} that provides one
 * of more {@link FeatureContainer}s. Typically the {@link Activity} itself is a {@link FeatureContainer}.
 */
public interface FeatureContainerActivity {

    List<FeatureContainer> getFeatureContainers();
}
