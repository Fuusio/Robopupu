package org.fuusio.api.feature;

import android.app.Activity;

import java.util.List;

/**
 * {@link FeatureContainerActivity} in an interface for an {@Link Activity} that provides one
 * of more {@link FeatureContainer}s. Typically the {@link Activity} itself is a {@link FeatureContainer}.
 */
public interface FeatureContainerActivity {

    List<FeatureContainer> getFeatureContainers();
}
