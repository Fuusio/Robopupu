/*
 * Copyright (C) 2016 Marko Salmela, http://robopupu.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robopupu.component;

import com.robopupu.api.component.Manager;
import com.robopupu.api.plugin.PlugInterface;

@PlugInterface
public interface PlatformManager extends Manager {

    /**
     * Gets the specified string resource formatted with the given optional arguments.
     * @param stringResId The resource id of the string.
     * @param formatArgs Optional formatting arguments.
     * @return A {@link String}.
     */
    String getString(int stringResId, final Object... formatArgs);

    /**
     * Opens the web page specified by the given url. The web page is opened on a web browser app.
     *
     * @param url A @link Context}.
     */
    void openWebPage(String url);

    /**
     * Sends the specified email.
     * @param address The address of the receiver.
     * @param subject The subject of the email.
     * @param body The body of the email.
     * @param chooserTitle The action parameter for the chooser showed selecting an email client.
     * @return A {@code boolean} value indicating if an email client was found for sending the email.
     */
    boolean sendEmail(String address, String subject, String body, String chooserTitle);
}
